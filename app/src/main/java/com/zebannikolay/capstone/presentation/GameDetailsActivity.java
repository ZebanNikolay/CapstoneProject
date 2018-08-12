package com.zebannikolay.capstone.presentation;

import android.Manifest;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.zebannikolay.capstone.App;
import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.databinding.ActivityGameDetailBinding;
import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGame;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import timber.log.Timber;

public class GameDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_GAME_ID = GameDetailsActivity.class.getSimpleName() + "extra_game_id";
    private static final String SCROLL_POSITION = "SCROLL_POSITION";
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

        if (savedInstanceState != null) {

            final int[] position = savedInstanceState.getIntArray(SCROLL_POSITION);
            if (position != null) {
                binding.scrollView.post(() -> binding.scrollView.scrollTo(position[0], position[1]));
            }
        }


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
                    Dexter.withActivity(GameDetailsActivity.this)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new BasePermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    new DownloadFileFromURL().execute(uri.toString(), rulesUrl);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    Toast.makeText(GameDetailsActivity.this, getString(R.string.write_external_storage_error), Toast.LENGTH_SHORT).show();
                                }
                            }).check();
                })
                .addOnFailureListener(Timber::e);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final int[] position = savedInstanceState.getIntArray(SCROLL_POSITION);
        if (position != null) {
            binding.scrollView.postDelayed(() -> binding.scrollView.scrollTo(position[0], position[1]), 500);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(SCROLL_POSITION,
                new int[]{binding.scrollView.getScrollX(), binding.scrollView.getScrollY()});
    }

    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

            dialog = new ProgressDialog(GameDetailsActivity.this);
            dialog.setMessage("Loading... Please wait...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            final String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + f_url[1];
            try {

                final URL url = new URL(f_url[0]);

                final URLConnection conection = url.openConnection();
                conection.connect();

                final InputStream input = new BufferedInputStream(url.openStream(), 8192);
                final OutputStream output = new FileOutputStream(path);
                final byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Timber.e(e.getMessage());
            }

            return path;
        }


        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            dialog.dismiss();
            Toast.makeText(GameDetailsActivity.this, "File downloaded: " + file_url, Toast.LENGTH_LONG).show();
        }

    }
}
