plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.navigation.safeargs)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.android.hilt)
}

android {
    namespace = "com.example.reusablecomponents"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.reusablecomponents"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "ReusableComponents"
            keyPassword = "password"
            storeFile = file("keystore.jks")
            storePassword = "password"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    flavorDimensions += listOf("build")

    productFlavors{
        create("dev"){
            dimension = "build"
            applicationIdSuffix = ".dev"
        }
        create("stage"){
            dimension = "build"
            applicationIdSuffix = ".stage"
        }
        create("prod"){
            dimension = "build"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    buildFeatures{
        viewBinding=true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":adapter"))
    implementation(project(":viewpageradapter"))
    implementation(project(":permissions"))
    implementation(project(":reusableapi"))
    implementation(project(":media-picker"))
    implementation(project(":payment-app"))
    implementation(project(":ml-kit"))
    implementation(project(":loaders"))

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)
    implementation(libs.swipe.refresh)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}