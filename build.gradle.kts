import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

group = "com.gateweb"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  google()
}

dependencies {
  // Note, if you develop a library, you should use compose.desktop.common.
  // compose.desktop.currentOs should be used in launcher-sourceSet
  // (in a separate module for demo project and in testMain).
  // With compose.desktop.common you will also lose @Preview functionality
  implementation(compose.desktop.currentOs)
//    exclude(compose.material)
  implementation(compose.materialIconsExtended)
  implementation(compose.material3)

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
//  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
//  implementation("org.slf4j:slf4j-api:2.0.9")
//  implementation("org.apache.logging.log4j:log4j-api:2.20.0")
//  implementation("org.apache.logging.log4j:log4j-core:2.20.0")
//  implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
  implementation("org.slf4j:slf4j-api:1.7.32")
  implementation("ch.qos.logback:logback-classic:1.2.9")


  implementation("commons-codec:commons-codec:1.15")

// Please do remember to add compose.foundation and compose.animation
  api(compose.foundation)
  api(compose.animation)

  implementation("io.insert-koin:koin-compose:1.1.0")

}
//val commonMain by getting {
//    dependencies {
//        implementation("io.insert-koin:koin-core:3.3.3")
//        api("dev.icerock.moko:mvvm-core:0.13.1")
//    }
//}
//
//val androidMain by getting {
//            dependencies {
//                implementation("io.insert-koin:koin-androidx-compose:3.4.2")
//            }
//        }
//tasks.withType<JavaCompile> {
//  sourceCompatibility = JavaVersion.VERSION_17.toString()
//  targetCompatibility = JavaVersion.VERSION_17.toString()
//}
tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "17"
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      modules("java.naming")
//      modules("java.instrument")
//      modules("java.management")
//      modules("java.sql")
//      modules("jdk.unsupported")

      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "Gateweb-tool"
      packageVersion = "1.0.0"
//      macOS{
//        iconFile.set(project.file("gateweb_logo.png"))
//      }
      windows {
        iconFile.set(project.file("launcher/gateweb_logo.ico"))
      }
      linux {
        iconFile.set(project.file("launcher/gateweb_logo.png"))
      }
    }
  }
}

