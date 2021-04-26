import java.util.concurrent.TimeUnit

object  Versions {

    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    val versionCode = 1
    val versionName = "1.1"
    val versionSuffixDebug = getDebugSuffix()
    val versionSuffixInternal = getInternalSuffix()
    const val dataBaseVersion = "1"
    const val kotlin_version = "1.3.70"
    const val objectboxVersion = "2.7.1"
    const val retrofit_version = "2.9.0"
    const val moshi_version = "1.8.0"
    const val rxImagePickerVersion = "2.5.6"
    const val butterknife_version = "10.1.0"
    const val bottomsheet_version = "1.5.3"
    const val glide_version = "4.11.0"
    const val playServices_maps_version = "17.0.0"
    const val playServices_location_version = "17.0.0"
    const val playServices_auth_version = "17.0.0"
    const val playServices_analytics_version = "17.0.0"

    const val googleApiClient_version = "1.30.2"
    const val googlePlaces_version = "2.0.0"
    const val epoxy_version = "3.7.0"
    const val mvrx_version = "1.3.0"
    const val gander_version = "3.1.0"

    private fun getDebugSuffix(): String {
        val branch = getCurrentBranch()
        val commitHash = getCommitHash()
        return "_${branch}_$commitHash"
    }

    private fun getInternalSuffix() =
        if (isCircleCI()) "-${getBuildNumber()}" else "-${getCommitCount()}"

    private fun getCommitCount(): String {
        val getLatestTagCommand = "git rev-list HEAD --count"
            .split(' ')
        return runProcess(getLatestTagCommand)[0]
    }

    private fun getLatestTag(): String {
        val getLatestTagCommand = "git describe --abbrev=0 --tags"
            .split(' ')
        return runProcess(getLatestTagCommand)[0]
    }

    private fun getLatestVersionCode(): Int {
        val getLatestTagCommand = "git tag --list"
            .split(' ')
        return runProcess(getLatestTagCommand).size.plus(32)
    }

    private fun getCurrentBranch(): String {
        val currentBranchCommand = "git rev-parse --abbrev-ref HEAD"
            .split(' ')
        return runProcess(currentBranchCommand)[0]
    }

    private fun getCommitHash(): String {
        val commitHashHEAD = "git rev-parse --short HEAD"
            .split(' ')
        return runProcess(commitHashHEAD)[0]
    }

    private fun isCircleCI(): Boolean = System.getenv("CIRCLECI")?.toBoolean() ?: false

    private fun getBuildNumber() =
        System.getenv("CIRCLE_BUILD_NUM") ?: "NO_PR"

    private fun runProcess(command: List<String>): List<String> {
        val process = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        process
            .waitFor(10, TimeUnit.SECONDS)

        return process.inputStream.bufferedReader().readLines()
    }
}

object Deps {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"

    const val ktx_core = "androidx.core:core-ktx:1.3.1"
    const val appcompat_v7 = "androidx.appcompat:appcompat:1.2.0"
    const val recyclerview_v7 = "androidx.recyclerview:recyclerview:1.1.0"
    const val design = "com.google.android.material:material:1.3.0"
    const val cardview_v7 = "androidx.cardview:cardview:1.0.0"
    const val customtabs = "androidx.browser:browser:1.2.0"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.0.0"
    const val multidex = "androidx.multidex:multidex:2.0.1"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    val converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_version}"
    val adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_version}"
    val converter_scalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit_version}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    const val pretty_logging_interceptor = "com.github.dkorobtsov:LoggingInterceptor:4.5"

    val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_version}"
    val moshi_adapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi_version}"

    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
    const val rxjava = "io.reactivex.rxjava2:rxjava:2.1.3"
    val rxImagePicker = "com.github.qingmei2:rximagepicker:${Versions.rxImagePickerVersion}"
    val rxImagePickerSupport =
        "com.github.qingmei2:rximagepicker_support:${Versions.rxImagePickerVersion}"
    val rxImagePickerZhihu =
        "com.github.qingmei2:rximagepicker_support_zhihu:${Versions.rxImagePickerVersion}"

    val objectboxRxjava = "io.objectbox:objectbox-rxjava:${Versions.objectboxVersion}"

    val butterknife = "com.jakewharton:butterknife:${Versions.butterknife_version}"
    val butterknife_compiler =
        "com.jakewharton:butterknife-compiler:${Versions.butterknife_version}"

    val play_services_maps =
        "com.google.android.gms:play-services-maps:${Versions.playServices_maps_version}"
    val play_services_location =
        "com.google.android.gms:play-services-location:${Versions.playServices_location_version}"
    val play_services_auth =
        "com.google.android.gms:play-services-auth:${Versions.playServices_auth_version}"
    const val play_services_auth_phone =
        "com.google.android.gms:play-services-auth-api-phone:17.1.0"
    const val play_services_ads = "com.google.android.gms:play-services-ads-identifier:17.0.0"

    //Firebase
    const val firebase_bom =
        "com.google.firebase:firebase-bom:26.3.0"

    const val firebase_messaging =
        "com.google.firebase:firebase-messaging-ktx"
    const val firebase_perf = "com.google.firebase:firebase-perf-ktx"
    const val firebase_analytics =
        "com.google.firebase:firebase-analytics-ktx"
    const val firebase_crashlytics =
        "com.google.firebase:firebase-crashlytics-ktx"
    const val firebase_dynamic_links =
        "com.google.firebase:firebase-dynamic-links-ktx"
    const val firebase_remote_config =
        "com.google.firebase:firebase-config-ktx"
    const val firebase_inappmessaging =
        "com.google.firebase:firebase-inappmessaging-display-ktx"

    val google_api_client =
        "com.google.api-client:google-api-client:${Versions.googleApiClient_version}"
    val google_api_cleint_adroid =
        "com.google.api-client:google-api-client-android:${Versions.googleApiClient_version}"
    const val google_api_services_people =
        "com.google.apis:google-api-services-people:v1-rev20190716-1.30.1"

    val google_places =
        "com.google.android.libraries.places:places:${Versions.googlePlaces_version}"

    const val facebook_sdk = "com.facebook.android:facebook-android-sdk:9.0.0"

    val bottomsheet_core = "com.flipboard:bottomsheet-core:${Versions.bottomsheet_version}"
    val bottomsheet_commons = "com.flipboard:bottomsheet-commons:${Versions.bottomsheet_version}"

    const val shimmerlayout = "io.supercharge:shimmerlayout:2.1.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"

    val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    const val nineoldandroids = "com.nineoldandroids:library:2.4.0"

    val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy_version}"
    val epoxy_processor = "com.airbnb.android:epoxy-processor:${Versions.epoxy_version}"

    val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx_version}"
    val mvrxTesting = "com.airbnb.android:mvrx-testing:${Versions.mvrx_version}"

    const val swipe_reveal_layout = "com.chauthai.swipereveallayout:swipe-reveal-layout:1.4.1"

    const val intercom = "io.intercom.android:intercom-sdk:8.2.0"

    const val photoview = "com.github.chrisbanes:PhotoView:2.3.0"

    const val smartlocation = "io.nlopez.smartlocation:library:3.3.3"

    const val junit = "junit:junit:4.12"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val powerMock_module = "org.powermock:powermock-module-junit4:2.0.2"
    const val powerMock_api = "org.powermock:powermock-api-mockito2:2.0.2"
    const val powerMock_core = "org.powermock:powermock-core:2.0.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.2.0"

    const val flurry = "com.flurry.android:analytics:11.6.0@aar"

    const val buglife = "com.buglife.sdk:buglife-android:1.5.1"

    const val material_stepper = "com.stepstone.stepper:material-stepper:4.3.1"

    const val deviceNames = "com.jaredrummler:android-device-names:2.0.0"

    //Payfort Dependencies
    const val victor = "com.victor:lib:1.0.1"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val font_icon = "com.shamanland:fonticon:0.1.8"
    const val snackbar = "com.nispok:snackbar:2.11.0"
    const val bouncy_castle = "org.bouncycastle:bcprov-jdk16:1.46"
    const val guava = "com.google.guava:guava:19.0"
    const val commons_codec = "commons-codec:commons-codec:1.10"

    const val flex_box = "com.google.android:flexbox:2.0.1"

    const val rtl_view_pager = "com.duolingo.open:rtl-viewpager:2.0.0"

    const val web_engage = "com.webengage:android-sdk:3.+"
    const val android_referrer = "com.android.installreferrer:installreferrer:2.1"

    const val mix_panel = "com.mixpanel.android:mixpanel-android:5.8.1"
    const val adjust_android_sdk = "com.adjust.sdk:adjust-android:4.24.1"
    val play_service_analytics =
        "com.google.android.gms:play-services-analytics:${Versions.playServices_analytics_version}"

    const val phoenix = "com.jakewharton:process-phoenix:2.0.0"

    const val konfetti = "nl.dionsegijn:konfetti:1.1.3"

    const val viewModelAndLiveData = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val lifecycleDefaultInterface = "androidx.lifecycle:lifecycle-common-java8:2.2.0"

    const val litecycle = "com.github.Ahmed-Adel-Ismail:LiteCycle:1.2.1"

    val gander = "com.ashokvarma.android:gander:${Versions.gander_version}"
    val ganderPersistence = "com.ashokvarma.android:gander-persistence:${Versions.gander_version}"
    val ganderNoOp = "com.ashokvarma.android:gander-no-op:${Versions.gander_version}"

    const val mapUtils = "com.google.maps.android:android-maps-utils:0.5"

    const val gravitySnapHelper = "com.github.rubensousa:gravitysnaphelper:2.2.0"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val viewPageIndicator = "com.github.adrielcafe:PageIndicatorView:1.0.6"

    const val googlePlayCoreKtx = "com.google.android.play:core-ktx:1.8.1"
    const val lottie = "com.airbnb.android:lottie:3.7.0"
}
