import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.app1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app1"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding =true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.compose.material.material.icons.extended)
    implementation(files("libs/SparkChain.aar"))
    implementation("com.google.android.exoplayer:exoplayer:2.18.2")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.29.0-alpha")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")
    implementation("androidx.media3:media3-common:1.4.1")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jsoup:jsoup:1.18.1")
    implementation("com.github.ZEGOCLOUD:zego_inapp_chat_uikit_android:+"){
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.saved.instance.state)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.auth)
    val room_version = "2.6.1"
    implementation("androidx.biometric:biometric:1.1.0")
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt (libs.androidx.room.compiler)
    implementation(libs.map2d)
    implementation(libs.accompanist.permissions)
    implementation(libs.location)
    implementation("androidx.room:room-ktx:$room_version")
    implementation(libs.generativeai)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.x024.x2.x1)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.accompanist.swiperefresh)





}