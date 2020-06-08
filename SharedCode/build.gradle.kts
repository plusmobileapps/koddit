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
}
android {
    compileSdkVersion(Android.compileSdkVersion)
    buildToolsVersion(Android.androidToolsVersion)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = 1
        versionName = "1.0"

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


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(Ktor.core)
                implementation(Ktor.serialization)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization")
                implementation("com.squareup.sqldelight:runtime:$sqlDelight")
                api("org.kodein.di:kodein-di:${Versions.kodein}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }


//        js().compilations["main"].defaultSourceSet  { /* ... */ }
//        js().compilations["test"].defaultSourceSet { /* ... */ }

        // Default source set for JVM-specific sources and dependencies:
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}")
                implementation("io.ktor:ktor-client-apache:${Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization-jvm:${Versions.ktor}")
                implementation("com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}")
            }
        }
        // JVM-specific tests and their dependencies:
        jvm().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }


    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation(Ktor.androidClient)
        implementation(Ktor.androidSerialization)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization")
        implementation("com.squareup.sqldelight:android-driver:$sqlDelight")
    }

    sourceSets["iosMain"].dependencies {
        implementation("io.ktor:ktor-client-ios:$ktor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization")
        implementation("io.ktor:ktor-client-serialization-native:$ktor")
        implementation ("com.squareup.sqldelight:native-driver:$sqlDelight")

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