//inherit from other file
apply {
    from("$rootDir/base-module.gradle")
}

/**
 * Dependencies used only for the core module
 */
dependencies {

    "implementation"(project(Modules.core))
    //implement coroutines that hace flow inside
    "implementation"(Coroutines.coroutines)



}