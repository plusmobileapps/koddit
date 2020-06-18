plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-js"))
    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.105-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.105-kotlin-1.3.72")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation(npm("text-encoding"))
    implementation(npm("abort-controller"))
    implementation(npm("bufferutil"))
    implementation(npm("utf-8-validate"))
    implementation(npm("fs"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.coroutines}")

    // ktor
    implementation("io.ktor:ktor-client-js:${Versions.ktor}") //include http&websockets
    implementation("io.ktor:ktor-client-json-js:${Versions.ktor}")
    implementation("io.ktor:ktor-client-logging-js:${Versions.ktor}")
    implementation("io.ktor:ktor-client-serialization-js:${Versions.ktor}")

    // Serialize
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.serialization}")

    // SQL Delight
    //implementation("com.squareup.sqldelight:sqljs-driver:${Versions.sqlDelight}")
    implementation("com.squareup.sqldelight:runtime-js:${Versions.sqlDelight}")


}

kotlin {
    target {
        useCommonJs()
        browser {
            // https://kotlinlang.org/docs/reference/javascript-dce.html#known-issue-dce-and-ktor
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }
}
