//inherit from other file
apply {
    from("$rootDir/base-module.gradle")
}

/**
 * Dependencies used only for the core module
 */
dependencies {

    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
    "implementation"(Retrofit.moshiConverter)

    "kapt"(Room.roomCompiler)
    //api menas that we add dependency only from room bit if we use implementation we also acces all the libraries that this room library implements
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)



}