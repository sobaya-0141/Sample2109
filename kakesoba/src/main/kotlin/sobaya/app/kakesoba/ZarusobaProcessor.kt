package sobaya.app.kakesoba

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class ZarusobaProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }

        val viewModelSymbol = resolver.getSymbolsWithAnnotation("sobaya.app.zarusoba.ViewModel")
        val stateSymbol = resolver.getSymbolsWithAnnotation("sobaya.app.zarusoba.State")

        viewModelSymbol.filterIsInstance<KSClassDeclaration>().forEach { vm ->
            val code = """
                package ${vm.packageName.asString()}
                class ZarusobaFuncs {
                    fun ${vm.packageName.asString()}.${vm.simpleName}.hello() {
                        println("Hello World")
                    }
                }
            """.trimIndent()

            val file = codeGenerator.createNewFile(
                Dependencies(false),
                vm.packageName.asString(),
                "ZarusobaFuncs"
            )

            file.write(code.toByteArray())
            file.close()
        }

        invoked = true
        return emptyList()
    }

    override fun onError() {
        super.onError()
        logger.error("Error")
    }
}
