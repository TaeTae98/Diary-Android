plugins {
    id("com.android.library")
}


dependencies {

    implementation("androidx.security:security-crypto:1.0.0")

    // For Identity Credential APIs
//    implementation("androidx.security:security-identity-credential:1.0.0-alpha03")

    // For App Authentication APIs
//    implementation("androidx.security:security-app-authenticator:1.0.0-alpha02")

    // For App Authentication API testing
//    androidTestImplementation("androidx.security:security-app-authenticator:1.0.0-alpha02")
}