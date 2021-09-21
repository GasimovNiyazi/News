object Dependencies {

    object Kotlin {
        private const val version = "1.5.10"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    object Appcompat {
        private const val version = "1.3.0"
        const val appcompat = "androidx.appcompat:appcompat:$version"
    }

    object CoreKTX {
        private const val version = "1.5.0"
        const val coreKTX = "androidx.core:core-ktx:$version"
    }

    object Paging3 {
        private const val version = "3.0.1"
        const val paging3 = "androidx.paging:paging-runtime:$version"
    }

    object Hilt {
        private const val version = "2.38.1"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val kapt = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Network {
        private const val retrofitVersion = "2.9.0"
        private const val okhttpVersion = "4.9.0"
        private const val loggingInterceptorVersion = "4.9.0"
        private const val gsonConverterVersion = "2.9.0"
        private const val gsonVersion = "2.8.7"

        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion"

        const val okHttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"

        const val retrofitConverter =
            "com.squareup.retrofit2:converter-gson:$gsonConverterVersion"

        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object View {
        private const val materialVersion = "1.3.0"
        private const val constraintVersion = "2.0.4"

        const val materialDesign = "com.google.android.material:material:$materialVersion"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintVersion"
    }

    object Lifecycle {
        private const val lifecycleVersion = "2.4.0-alpha02"
        private const val fragmentKtxVersion = "1.3.5"

        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentKtxVersion"
    }

    object Room {
        private const val roomVersion = "2.3.0"

        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomKapt = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }

    object Helpers {
        private const val timberVersion = "4.7.1"
        private const val coilVersion = "1.3.2"

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val coil = "io.coil-kt:coil:$coilVersion"
    }

    object Testing {
        private const val junitVersion = "4.12"
        private const val androidJunitVersion = "1.1.2"
        private const val espressoVersion = "3.3.0"

        const val junit = "junit:junit:$junitVersion"
        const val androidJunit = "androidx.test.ext:junit:$androidJunitVersion"
        const val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"
    }
}