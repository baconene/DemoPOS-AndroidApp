# Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**

-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**

-keepclasseswithmembers class * {
    @retrofit2.http.<*> <methods>;
}

# Gson
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-dontwarn com.google.gson.**
-dontwarn sun.misc.Unsafe
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue

# Hilt
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-dontwarn dagger.hilt.**

# Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Your app data models
-keep class com.demopos.data.models.** { *; }
-keep class com.demopos.domain.entities.** { *; }
-keep class com.demopos.data.local.entities.** { *; }

# Keep data classes
-keep class com.demopos.** { *; }
-keepclassmembers class com.demopos.** {
    *** get*();
    void set*(...);
}