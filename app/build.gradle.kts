plugins {
    id("com.android.application")
}

android {
    namespace = "ir.yeksaco.jahesh"
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.yeksaco.jahesh"
        minSdk = 22
        targetSdk = 34
        versionCode = 3
        versionName = "1.3.0"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Fix Duplicate class
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("com.google.code.gson:gson:2.8.0")
    implementation("com.squareup.retrofit:retrofit:1.9.0")
    implementation("com.squareup.okhttp:okhttp:2.7.0")
    implementation("com.squareup.retrofit:converter-gson:2.0.0-beta2")
    implementation("com.alimuzaffar.lib:pinentryedittext:2.0.6")
    implementation("com.github.taimoorsultani:android-sweetalert2:2.0.2")
    implementation("com.nex3z:notification-badge:1.0.4")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation(files("E:\\Projects\\Android\\Jahesh\\libs\\autoimageslider.aar"))


}