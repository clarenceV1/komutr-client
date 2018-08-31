# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#--------Common Android Component----------
-verbose
-dontpreverify
-ignorewarnings
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes Exceptions, Signature, InnerClasses
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep public class javax.**
-keep public class android.webkit.**
-keep class android.support.v4.** {*; }
-keep public class * extends android.app.Service
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep class * extends java.lang.annotation.Annotation
-keep public class * extends android.preference.Preference
-keep class * extends java.lang.annotation.Annotation { *; }
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.support.v4.app.Fragment


-dontwarn android.support.**
-dontwarn android.support.v4.**
-dontwarn android.webkit.WebView
-dontwarn com.google.android.maps.**
-keepclasseswithmembernames class * {native <methods>;}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * implements java.io.Serializable {public *;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class sun.misc.Unsafe { *; }
-dontwarn okio.**
-dontwarn javax.annotation.**
-keep class android.support.v4.app.NotificationCompat**{
    public *;
}
#-----------------------------------------------------------------------------------------

#-------- app component----------
-keep class com.komutr.client.bean.** { * ; }

# FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

#--------Dagger----------
#for dagger
#Keep the annotated things annotated
#Keep the Modules intact
-keep @dagger.Module class *
#Keep the dagger annotation classes themselves
-keep @interface dagger.*,javax.inject.*
#-Keep the fields annotated with @Inject of any class that is not deleted.
-keepclassmembers class * { @javax.inject.* <fields>;}
#-Keep the names of classes that have fields annotated with @Inject and the fields themselves.
-keepclasseswithmembernames class * {@javax.inject.* <fields>;}
# Keep the generated classes by dagger-compile
-dontwarn dagger.**
-keep class dagger.**{*;}
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection

#--------RxJava 0.21--------
-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.internal.schedulers.ImmediateThinScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-dontwarn rx.**
-keepclassmembers class rx.** { *; }

# retrolambda
-dontwarn java.lang.invoke.*

#--------EventBus----------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#--------proguard-support-v7-appcompat--------
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
#--------support-design--------
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# fastjson proguard rules
# https://github.com/alibaba/fastjson
-dontwarn com.alibaba.fastjson.**
-keepattributes Signature
-keepattributes *Annotation*

#--------Glide--------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#--------Retrofit 2.X--------
## https://square.github.io/retrofit/ ##
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#--------okhttp3--------
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}
-dontwarn com.squareup.okhttp.**

#--------okio--------
#-dontwarn okio.**
#-keep class okio.**{*;}
#-keep interface okio.**{*;}
#-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

#--------fastjson--------
-keep class com.alibaba.**{*;}
-keep class com.yuyh.**{*;}

#--------七牛--------
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}

#--------aroute--------
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider


#--------sharesdk--------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*
-dontwarn com.tencent.**
-keep class com.tencent.** {*;}

#--------buggly--------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#--------umeng--------
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class one.more.R$*{
public static final int *;
}