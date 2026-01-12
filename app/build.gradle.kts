plugins {
    id("com.android.application") // or "com.android.library"
    id("org.jetbrains.kotlin.android")
    kotlin("kapt") // This is the correct way to apply kapt
}

android {
    namespace = "com.example.jazzlibrary2025v2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jazzlibrary2025v2"
        minSdk = 24     //21   to play to all smartphone
        targetSdk = 34    //33    to play to all smartphone
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Local JAR files
    implementation(files("libs/mssql-jdbc-9.4.0.jre11.jar"))
    implementation(files("libs/javax.ws.rs-api-2.1.1.jar"))






    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Flow Layout
    implementation("com.nex3z:flow-layout:1.3.3")

    // JTDS
    implementation("net.sourceforge.jtds:jtds:1.3.1")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.30")

    //implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5")

    // Glide (Image loading)
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    //NOT SUPPORTED OVER JAVA 9 .. USE ViewBinding INSTEED
    // ButterKnife (View binding)
//    implementation("com.jakewharton:butterknife:10.2.3")
//    annotationProcessor("com.jakewharton:butterknife-compiler:10.2.3")

    // Extra dependencies (JARs in 'libs' folder)
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    // Volley (HTTP traffic)
    implementation("com.android.volley:volley:1.2.1")

    // GIF Drawable
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")

    // Core Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")


    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")





    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // WorkManager
    implementation("androidx.work:work-runtime:2.8.1")

    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("androidx.navigation:navigation-fragment:2.7.7")


    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.android.material:material:1.6.0")
}