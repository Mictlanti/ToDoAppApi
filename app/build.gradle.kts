plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serilization)
    alias(libs.plugins.dagger)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.todoapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
//    room {
//        schemaDirectory("$projectDir/schema")
//    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.work:work-runtime-ktx:2.11.0")
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Navi
    implementation(libs.androidx.navigation.compose)

    //Fonts
    implementation(libs.androidx.compose.ui.text.google.fonts)

    //Icons extended
    implementation(libs.androidx.compose.material.icons.extended)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt(libs.hilt.compiler)
    kapt("androidx.hilt:hilt-compiler:1.3.0")

    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //FusedLocationProvider
    implementation(libs.play.services.location)

    //logging interceptor
    implementation(libs.logging.interceptor)

}
