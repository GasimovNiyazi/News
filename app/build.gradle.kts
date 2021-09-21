import java.io.FileInputStream
import java.util.*

plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
}

val apikeyPropertiesFile = rootProject.file(AppConfig.ApiKeyProperties.PATH)
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    compileSdkVersion(AppConfig.compileSdkVersion)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId(AppConfig.DefaultConfig.applicationId)
        minSdkVersion(AppConfig.DefaultConfig.minSdkVersion)
        targetSdkVersion(AppConfig.DefaultConfig.targetSdkVersion)
        versionCode = AppConfig.DefaultConfig.versionCode
        versionName = AppConfig.DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            AppConfig.ApiKeyProperties.TYPE,
            AppConfig.ApiKeyProperties.PROPERTY_NAME,
            apikeyProperties.getProperty(AppConfig.ApiKeyProperties.PROPERTY_NAME)
        )
    }

    buildTypes {
        getByName(AppConfig.BuildTypes.release) {
            isMinifyEnabled = AppConfig.BuildTypes.releaseMinifyEnabled
            proguardFiles(
                getDefaultProguardFile(AppConfig.BuildTypes.defaultProguardFile),
                AppConfig.BuildTypes.proguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.CompileOptions.sourceCompatibility
        targetCompatibility = AppConfig.CompileOptions.targetCompatibility
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = AppConfig.KotlinOptions.jvmTarget
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.Kotlin.kotlin)
    implementation(Dependencies.CoreKTX.coreKTX)
    implementation(Dependencies.Appcompat.appcompat)
    implementation(Dependencies.View.materialDesign)
    implementation(Dependencies.View.constraintLayout)

    implementation(Dependencies.Paging3.paging3)

    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.retrofitConverter)
    implementation(Dependencies.Network.gson)
    implementation(Dependencies.Network.okHttp)
    implementation(Dependencies.Network.loggingInterceptor)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.kapt)

    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Lifecycle.liveData)
    implementation(Dependencies.Lifecycle.lifecycle)
    implementation(Dependencies.Lifecycle.fragmentKtx)

    implementation(Dependencies.Room.roomRuntime)
    kapt(Dependencies.Room.roomKapt)
    implementation(Dependencies.Room.roomKtx)

    implementation(Dependencies.Helpers.timber)

    implementation(Dependencies.Helpers.coil)

    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.androidJunit)
    androidTestImplementation(Dependencies.Testing.espresso)
}