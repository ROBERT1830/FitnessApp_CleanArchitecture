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
    //al the things that we need in the domain module will be needed in the presentation
    //for example the model clases wee need this to show in the ui
    /**
     * The good thing here is that we can decide if we want to include
     * a specific module and make sure that we follow separation of concerns.
     * So will be not way that for example the presentation on boardin modle acces the data module.
     */
    "implementation"(project(Modules.onboardingDomain)) //project ---> Creates a dependency on a project and as a parameter needs the route module
    //path - the path of the project to be added as a dependency



}