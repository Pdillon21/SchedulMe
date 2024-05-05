import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    `maven-publish`
}

android {
    namespace = "com.example.schedulemebase"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val mLibraryName = "Schedule Me Base"
val mFilePath = "$buildDir/outputs/aar/"
val mLibraryFileName = "${mLibraryName.replace(" ","")}-release.aar"
val mGroupId = "com.schedule.me"
val mArtifactID = "base"
val mVersionNumber = "0.0.2-alpha"
val mLibraryDescription = "A simple library to test CI path"
val githubProperties = Properties().apply {
    file("../github.properties").inputStream().use {
        load(it)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = mGroupId
            artifactId = mArtifactID
            version = mVersionNumber
            artifact("$mFilePath$mLibraryFileName")
            pom {
                name = mLibraryFileName
                description = mLibraryDescription
            }
        }
    }

    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/PDillon21/SchedulMe")
            credentials {
                username = (githubProperties["gpr.usr"] ?: System.getenv("GITHUB_USER")).toString()
                password = (githubProperties["gpr.key"] ?: System.getenv("GITHUB_TOKEN")).toString()
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}