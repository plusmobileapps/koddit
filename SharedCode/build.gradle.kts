import Versions.coroutines
import Versions.ktor_version
import Versions.serialization
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}
android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
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

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation(Ktor.core)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization")
        implementation("io.ktor:ktor-client-serialization:$ktor_version")
        implementation("com.squareup.sqldelight:runtime:1.3.0")
        api(Koin.core)
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("io.ktor:ktor-client-android:${ktor_version}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization")
        implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
        implementation("com.squareup.sqldelight:android-driver:1.3.0")
    }

    sourceSets["iosMain"].dependencies {
        implementation("io.ktor:ktor-client-ios:$ktor_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization")
        implementation("io.ktor:ktor-client-serialization-native:$ktor_version")
        implementation ("com.squareup.sqldelight:native-driver:1.3.0")

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