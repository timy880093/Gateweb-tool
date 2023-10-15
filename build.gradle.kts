import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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
//  val voyagerVersion = "1.0.0-rc05"
  // Note, if you develop a library, you should use compose.desktop.common.
  // compose.desktop.currentOs should be used in launcher-sourceSet
  // (in a separate module for demo project and in testMain).
  // With compose.desktop.common you will also lose @Preview functionality
  implementation(compose.desktop.currentOs)

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

  implementation("com.arkivanov.decompose:decompose:2.1.2")
  implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.2")
// Please do remember to add compose.foundation and compose.animation
  api(compose.foundation)
  api(compose.animation)
//...
  api("moe.tlaster:precompose:1.5.4")
//  implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
//  implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
//  implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
//  implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "Gateweb-tool"
      packageVersion = "1.0.0"
    }
  }
}

