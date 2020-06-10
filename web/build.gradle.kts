plugins {
    kotlin("js")
}


repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
}

dependencies {
    implementation(kotlin("stdlib-js"))
    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.105-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.105-kotlin-1.3.72")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

}

kotlin.target.browser {
}
