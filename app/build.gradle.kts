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
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("com.google.code.gson:gson:2.8.0")
    implementation("com.squareup.retrofit:retrofit:1.9.0")
    implementation("com.squareup.okhttp:okhttp:2.7.0")
    implementation("com.squareup.retrofit:converter-gson:2.0.0-beta2")
    implementation("com.alimuzaffar.lib:pinentryedittext:2.0.6")
   //implementation ("com.github.traex.rippleeffect:library:1.3")
   //implementation ("com.github.smarteist:autoimageslider:1.4.0")
}