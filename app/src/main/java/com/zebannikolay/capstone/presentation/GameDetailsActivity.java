package com.zebannikolay.capstone.presentation;

import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zebannikolay.capstone.App;
import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.databinding.ActivityGameDetailBinding;
import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGame;

import javax.inject.Inject;

import timber.log.Timber;

public class GameDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_GAME_ID = GameDetailsActivity.class.getSimpleName() + "extra_game_id";
    private ActivityGameDetailBinding binding;
    @Inject BoardGamesInteractor interactor;
    private GameDetailsViewModel viewModel;

    public static void start(@NonNull final Context context, @NonNull final String gameId) {
        final Intent intent = new Intent(context, GameDetailsActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_detail);
        App.getAppComponent().inject(this);
        viewModel = new GameDetailsViewModel(interactor, getGameId());
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subscribeViewModel();
    }

    private void subscribeViewModel() {
        viewModel.getPlayReviewEvent().observe(this, this::openYouTubeVideo);
        viewModel.getOpenRulesEvent().observe(this, this::openRules);
        viewModel.getGame().observe(this, this::updateBakingWidget);
    }


    private void updateBakingWidget(@NonNull final BoardGame game) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, BoardGameWidget.class));
        BoardGameWidget.updateBoardGameWidgets(this, widgetManager, appWidgetIds, game);
    }


    private String getGameId() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_GAME_ID);
    }

    public void openYouTubeVideo(@NonNull final String videoId) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId)));
        } catch (ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId)));
        }
    }

    public void openRules(@NonNull final String rulesUrl) {
        final StorageReference loadRef = FirebaseStorage.getInstance().getReference().child("rules/" + rulesUrl);
        loadRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(browserIntent);
                })
                .addOnFailureListener(Timber::e);
    }
}
