plugins {
    id("kotlin")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.8"

}

javafx {
    version = "11.0.2"
    modules("javafx.controls", "javafx.graphics")
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

application {
    mainClassName = "com.plusmobileapps.desktop.MyApp"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("no.tornado:tornadofx:1.7.20")
}

//jar {
//    manifest {
//        attributes(
//            "Class-Path": configurations.compile.collect { it.getName() }.join(" "),
//        "Main-Class": mainClassName
//        )
//    }
//    from(configurations.compile.collect { entry -> zipTree(entry) }) {
//        exclude "META-INF/MANIFEST.MF"
//        exclude "META-INF/*.SF"
//        exclude "META-INF/*.DSA"
//        exclude "META-INF/*.RSA"
//    }
//}
