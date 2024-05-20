// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    val navVersion by extra("2.5.3")
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false


}