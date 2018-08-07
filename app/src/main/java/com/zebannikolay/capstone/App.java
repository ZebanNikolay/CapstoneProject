package com.zebannikolay.capstone;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.zebannikolay.capstone.core.di.AppComponent;
import com.zebannikolay.capstone.core.di.ContextModule;
import com.zebannikolay.capstone.core.di.DaggerAppComponent;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public final class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();

        initLoger();
    }

    private void initCrashlytics() {
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
    }

    private void initLoger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private AppComponent buildComponents() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appComponent = buildComponents();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }


    private static final class CrashReportingTree extends Timber.Tree {

        @Override
        protected void log(final int priority, final String tag, @NonNull final String message, final Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            Crashlytics.log(priority, tag, message);
            if (t != null) {
                Crashlytics.logException(t);
            }
        }
    }
}