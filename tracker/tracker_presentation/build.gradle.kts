//inherit from other file
apply {
    from("$rootDir/compose-module.gradle")
}

/**
 * Dependencies used only for the core module
 */
dependencies {

    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.trackerDomain))
    "implementation"(Coil.coilCompose)



}