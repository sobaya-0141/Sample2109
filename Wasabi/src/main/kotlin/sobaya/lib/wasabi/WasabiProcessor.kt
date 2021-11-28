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
    private val debugMode = false
    private val debug = ArrayList<String>()

    private fun getViewModels(resolver: Resolver): List<Pair<String, String>> {
        val viewModels = ArrayList<Pair<String, String>>()
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

        debug.addAll(stateMap.keys.map { it })

        viewModelSymbol.filterIsInstance<KSClassDeclaration>().forEach { classDeclare ->
            val key = stateMap.keys.find { it.contains(classDeclare.packageName.asString() + "." + classDeclare.simpleName.getShortName()) }
            if (key?.isNotEmpty() == true) {
                val state = stateMap[key] ?: ""
                viewModels.add(("${classDeclare.packageName.asString()}.${classDeclare.simpleName.getShortName()}" to state))
            }
        }

        return viewModels
    }

    private fun getStates(resolver: Resolver): List<Pair<String, String>> {
        val states = ArrayList<Pair<String, String>>()
        val stateSymbol = resolver.getSymbolsWithAnnotation("sobaya.lib.wasabi.WasabiState")

        stateSymbol.filterIsInstance<KSPropertyDeclaration>().forEach { it ->
            it.annotations.forEach {
                val filePath = (it.parent?.location as FileLocation).filePath
                val file = File(filePath)
                val fieldName = it.parent.toString()
                states.add(file.path.replace("/", ".") to fieldName)
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

        val functions = ArrayList<String>()
        val viewModels = getViewModels(resolver)
        val states = getStates(resolver)

        if (viewModels.isEmpty()) {
            invoked = true
            return emptyList()
        }

        functions.add("package sobaya.wasabi.${viewModels[0].first}")
        states.forEach { (viewModelPath, fieldName) ->
            viewModels.forEach {
                if (viewModelPath.contains(it.first)) {
                    functions.add(makeFunction(it.first, fieldName, it.second))
                }
            }
        }

        val file = codeGenerator.createNewFile(
            Dependencies(false),
            "sobaya.wasabi.${viewModels[0].first}",
            "StateExtensions"
        )

        if (!debugMode) {
            file.write(functions.joinToString("\n").toByteArray())
        } else {
            file.write(debug.joinToString("\n").toByteArray())
        }
        file.close()

        invoked = true
        return emptyList()
    }

    override fun onError() {
        super.onError()
        logger.warn("ERROR")
    }
}
