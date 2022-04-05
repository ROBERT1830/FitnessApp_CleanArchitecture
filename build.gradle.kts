// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    //we dont need to define it because we didi it in the objects java
//    ext {
//        compose_version = "1.0.1"
//    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}