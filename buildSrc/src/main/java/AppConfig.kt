import org.gradle.api.JavaVersion

object AppConfig {

    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"

    object DefaultConfig{
       const val applicationId = "com.niyazi.news"
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object BuildTypes {
        const val release = "release"
        const val debug = "debug"
        const val releaseMinifyEnabled = true
        const val debugMinifyEnabled = false
        const val defaultProguardFile = "proguard-android-optimize.txt"
        const val proguardRules = "proguard-rules.pro"
    }

    object CompileOptions {
        val sourceCompatibility = JavaVersion.VERSION_1_8
        val targetCompatibility = JavaVersion.VERSION_1_8
    }

    object KotlinOptions {
        const val jvmTarget = "1.8"
    }

    object ApiKeyProperties{
        const val PATH = "apikey.properties"
        const val TYPE = "String"
        const val PROPERTY_NAME = "API_KEY"
    }


}