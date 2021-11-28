package sobaya.lib.wasabi

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class WasabiViewModel(val dataClass: KClass<*>)
