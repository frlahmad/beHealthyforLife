plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.healthylife"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.healthylife"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    // 🔥 Kotlin DSL version (WAJIB create())
    signingConfigs {
        create("release") {
            storeFile = file("senopati-release.jks")
            storePassword = "123456"
            keyAlias = "senopati"
            keyPassword = "123456"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            // 🔥 Kotlin DSL (WAJIB pakai "=")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("com.google.code.gson:gson:2.10.1")
}
