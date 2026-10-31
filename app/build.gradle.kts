plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.whiskerpaws.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.whiskerpaws.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // 1. Core UI Components
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")
    
    // 2. Linker Engine Browser Tabs
    implementation("androidx.browser:browser:1.6.0")
    
    // 3. Monetization Engine
    implementation("com.google.android.gms:play-services-ads:23.0.0")

    // 4. 🛠️ THE CRITICAL FIX: Force-inject modern libraries to override AdMob background conflicts
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.sqlite:sqlite-framework:2.4.0")
    implementation("androidx.room:room-runtime:2.6.1")
}
