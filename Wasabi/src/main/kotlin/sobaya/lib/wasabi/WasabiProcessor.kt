package sobaya.lib.wasabi

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import java.io.File

class WasabiProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var invoked = false

    private fun getViewModels(resolver: Resolver): List<WasabiViewModelInfo> {
        val viewModels = ArrayList<WasabiViewModelInfo>()
        val viewModelSymbol = resolver.getSymbolsWithAnnotation("sobaya.lib.wasabi.WasabiViewModel")
        val stateMap = HashMap<String, String>()
        viewModelSymbol.iterator().forEach {
            it.annotations.forEach {
                if (it.annotationType.toString() == "WasabiViewModel") {
                    it.arguments.forEach {
                        val ksValue = it.value as KSType
                        val fileLocation = (it.parent?.location as FileLocation).filePath.replace("/", ".")
                        stateMap[fileLocation] = "${ksValue.declaration.packageName.asString()}.${ksValue.declaration.simpleName.asString()}"
                    }
                }
            }
        }

        viewModelSymbol.filterIsInstance<KSClassDeclaration>().forEach { classDeclare ->
            val key = stateMap.keys.find { it.contains(classDeclare.packageName.asString() + "." + classDeclare.simpleName.getShortName()) }
            if (key?.isNotEmpty() == true) {
                val state = stateMap[key] ?: ""
                viewModels.add(
                    WasabiViewModelInfo(
                        "${classDeclare.packageName.asString()}.${classDeclare.simpleName.getShortName()}",
                        state
                    )
                )
            }
        }

        return viewModels
    }

    private fun getStates(resolver: Resolver): List<WasabiStateInfo> {
        val states = ArrayList<WasabiStateInfo>()
        val stateSymbol = resolver.getSymbolsWithAnnotation("sobaya.lib.wasabi.WasabiState")

        stateSymbol.filterIsInstance<KSPropertyDeclaration>().forEach { it ->
            it.annotations.forEach {
                val filePath = (it.parent?.location as FileLocation).filePath
                val file = File(filePath)
                val fieldName = it.parent.toString()
                states.add(
                    WasabiStateInfo(
                        file.path.replace("/", "."), fieldName
                    )
                )
            }
        }

        return states
    }

    private fun makeFunction(viewModel: String, fieldName: String, stateType: String): String =
        """
            fun ${viewModel}.setState(proc: (state: ${stateType}) -> ${stateType}) {
                ${fieldName}.value = proc(${fieldName}.value)
            }
        """.trimIndent()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }

        logger.warn("START")
        val viewModels = getViewModels(resolver)
        val states = getStates(resolver)

        if (viewModels.isEmpty()) {
            invoked = true
            return emptyList()
        }

        states.forEach { (viewModelPath, fieldName) ->
            viewModels.forEach {
                if (viewModelPath.contains(it.classPath)) {
                    writeFile(makeFunction(it.classPath, fieldName, it.state), it.classPath)
                }
            }
        }

        logger.warn("FINISH")

        invoked = true
        return emptyList()
    }

    private fun writeFile(functionString: String, viewModelPath: String) {
        val functions = ArrayList<String>()

        functions.add("package sobaya.wasabi.${viewModelPath}")
        functions.add(functionString)

        val file = codeGenerator.createNewFile(
            Dependencies(false),
            "sobaya.wasabi.${viewModelPath}",
            "StateExtensions"
        )

        file.write(functions.joinToString("\n").toByteArray())
        file.close()
    }

    override fun onError() {
        super.onError()
        logger.warn("ERROR")
    }
}

data class WasabiViewModelInfo(
    val classPath: String,
    val state: String
)

data class WasabiStateInfo(
    val path: String,
    val fieldName: String
)
