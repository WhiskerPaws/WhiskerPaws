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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

// 👈 THIS FORCEFULLY STOPS THE WORK-RUNTIME / ROOM ROADBLOCKS IN LINES 90-93
configurations.all {
    resolutionStrategy {
        force("androidx.work:work-runtime:2.9.0")
        force("androidx.work:work-runtime-ktx:2.9.0")
        force("androidx.room:room-runtime:2.6.1")
        force("androidx.sqlite:sqlite-framework:2.4.0")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    
    // Modern Compose UI Components
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    
    // Linker Engine Browser Tabs
    implementation("androidx.browser:browser:1.6.0")
    
    // Monetization Engine
    implementation("com.google.android.gms:play-services-ads:23.0.0")
}
