plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.expensestracker.core.common"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // Compose runtime is needed only for the @Composable UiText.asString() helper.
    // Pulled in via the platform BOM declared by consumers; we depend on the API surface here.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)

    testImplementation(libs.junit)
}
