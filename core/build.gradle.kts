//inherit from other file
apply {
    from("$rootDir/base-module.gradle")
}

/**
 * Dependencies used only for the core module
 *
 * here ass a shared resources we will have al the string that are in the app module
 */
dependencies {

}