apply plugin: 'com.android.application'

android {
    compileSdkVersion 34 // Asegúrate de usar la versión más reciente

    defaultConfig {
        applicationId "com.mapagps.js"
        minSdkVersion 21
        targetSdkVersion 34
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation(libs.play.services.maps)
    implementation(libs.support.annotations)
    implementation(libs.play.services.location)
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.gms:play-services-maps:18.2.0' // Actualiza a la versión más reciente
    implementation 'com.google.android.gms:play-services-location:21.2.0' // Actualiza a la versión más reciente
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}