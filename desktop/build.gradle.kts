plugins {
    kotlin("jvm")
    id("application")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":SharedCode"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation(files("/usr/lib/jvm/openjfx/rt/lib/ext/jfxrt.jar"))

    testCompile("junit", "junit", "4.12")
}

application {
    mainClassName = "MyApp"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}