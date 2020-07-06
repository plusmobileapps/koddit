import Versions.coroutines
import Versions.ktor
import Versions.serialization
import Versions.sqlDelight
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("maven-publish")
}

group = "com.plusmobileapps.redditclient"
version = "0.0.1"

android {
    compileSdkVersion(Android.compileSdkVersion)
    buildToolsVersion(Android.androidToolsVersion)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }
    targets {
        android()
    }
    js {
        browser{
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation(Ktor.core)
        implementation(Ktor.serialization)
        implementation("com.github.aakira:napier:${Versions.napierVersion}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization")
        implementation("com.squareup.sqldelight:runtime:$sqlDelight")
        api("org.kodein.di:kodein-di:${Versions.kodein}")
        api("org.reduxkotlin:redux-kotlin-threadsafe:0.5.3")
//        api("org.reduxkotlin:redux-kotlin-thunk:0.4.0")

//        api("org.reduxkotlin:presenter-middleware:0.2.10")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation(Ktor.androidClient)
        implementation(Ktor.androidSerialization)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization")
        implementation("com.squareup.sqldelight:android-driver:$sqlDelight")
        implementation("org.reduxkotlin:redux-kotlin-threadsafe-jvm:0.5.1")
        implementation("androidx.appcompat:appcompat:1.1.0")
        implementation("com.github.aakira:napier-android:${Versions.napierVersion}")
    }

    sourceSets["iosMain"].dependencies {
        implementation(     "io.ktor:ktor-client-ios:$ktor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization")
        implementation("io.ktor:ktor-client-serialization-native:$ktor")
        implementation ("com.squareup.sqldelight:native-driver:$sqlDelight")
        implementation("com.github.aakira:napier-ios:${Versions.napierVersion}")


    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.coroutines}")
        implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
        implementation(npm("text-encoding"))
        implementation(npm("abort-controller"))
        implementation(npm("bufferutil"))
        implementation(npm("utf-8-validate"))
        implementation(npm("fs"))
        implementation("com.github.aakira:napier-js:${Versions.napierVersion}")

        // ktor
        implementation("io.ktor:ktor-client-js:${Versions.ktor}") //include http&websockets
        implementation("io.ktor:ktor-client-json-js:${Versions.ktor}")
        implementation("io.ktor:ktor-client-logging-js:${Versions.ktor}")
        implementation("io.ktor:ktor-client-serialization-js:${Versions.ktor}")

        // Serialize
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.serialization}")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText(
            "#!/bin/bash\n"
                    + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                    + "cd '${rootProject.rootDir}'\n"
                    + "./gradlew \$@\n"
        )
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)

sqldelight {
    database("MyDatabase") {
        packageName = "com.plusmobileapps.sharedcode.db"
        sourceFolders = listOf("sqldelight")
    }
}