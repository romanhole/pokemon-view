// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val hiltVersion = "2.48.1"
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}