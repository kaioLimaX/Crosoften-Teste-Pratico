plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.skymob.crosoftenteste"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.skymob.crosoftenteste"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //koin
    implementation ("io.insert-koin:koin-android:3.4.0")         // Koin para Android
    implementation ("io.insert-koin:koin-androidx-navigation:3.4.0") // Inclui suporte  Navigation


    //interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //activity and fragment Ktx for viewModels(
    implementation ("androidx.activity:activity-ktx:1.9.3")
    implementation ("androidx.fragment:fragment-ktx:1.8.5")

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //permissionX
    implementation ("com.guolindev.permissionx:permissionx:1.8.1")
    implementation(libs.core.ktx)

    //roboelectric
    testImplementation ("org.robolectric:robolectric:4.9")

    // test koin
    testImplementation ("io.insert-koin:koin-test-junit4:3.4.0")
    //mockk
    testImplementation ("io.mockk:mockk:1.13.2") // MockK para mocking

    //biblioteca do AndroidX usado em testes unitários para garantir que o código que manipula o LiveData seja executado de forma síncrona.
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    //
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")

    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}