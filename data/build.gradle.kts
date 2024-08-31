plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.example.reposlist"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // Make sure this matches your project's JVM target
    }
}


dependencies {
    implementation(libs.hilt.android)
    implementation(libs.room.android)
    implementation(libs.room.android.ktx)
    implementation(libs.room.android.paging)
    implementation(libs.hilt.android)
    implementation(project(":network"))
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.paging.runtime)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler.android)

    testImplementation (libs.mockito.core)
    androidTestImplementation (libs.mockito.core)

    // Testing dependencies
    testImplementation(libs.junit)
    testImplementation(libs.ktx.coroutines.test) // Replace with your Coroutines version

    // Required by the Hilt Testing library
    androidTestImplementation(libs.androidx.arch.core.test)
    androidTestImplementation(libs.androidx.test.core.test)
    androidTestImplementation(libs.androidx.junit)
}