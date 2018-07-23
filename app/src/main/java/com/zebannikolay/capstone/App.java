package com.zebannikolay.capstone;

import android.app.Application;
import android.content.Context;

import com.zebannikolay.capstone.core.di.AppComponent;
import com.zebannikolay.capstone.core.di.ContextModule;
import com.zebannikolay.capstone.core.di.DaggerAppComponent;

import timber.log.Timber;

public final class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    private AppComponent buildComponents() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
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

}