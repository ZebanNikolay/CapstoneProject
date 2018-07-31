package com.zebannikolay.capstone;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zebannikolay.capstone.core.di.AppComponent;
import com.zebannikolay.capstone.core.di.ContextModule;
import com.zebannikolay.capstone.core.di.DaggerAppComponent;
import com.zebannikolay.capstone.data.BoardGameRepository;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSource;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSourceImpl;
import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.BoardGamesInteractorImpl;
import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public final class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();

        initLoger();

        List<BoardGame> games = new ArrayList<>(5);

        games.add(new BoardGame("title", "rules", "url", null));
        games.add(new BoardGame("title", "rules", "url", null));
        games.add(new BoardGame("title", "rules", "url", null));
        games.add(new BoardGame("title", "rules", "url", null));
        games.add(new BoardGame("title", "rules", "url", null));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("board_games");
        BoardGamesDataSource dataSource = new BoardGamesDataSourceImpl(myRef);
        BoardGameRepository repository = new BoardGameRepository(dataSource, null);
        BoardGamesInteractor interactor = new BoardGamesInteractorImpl(repository);

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