package com.zebannikolay.capstone.presentation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.databinding.ActivityGameDetailBinding;

public class GameDetailActivity extends AppCompatActivity {

    private static final String EXTRA_GAME_ID = GameDetailActivity.class.getSimpleName() + "extra_game_id";
    private ActivityGameDetailBinding binding;

    public static void start(@NonNull final Context context, @NonNull final String gameId) {
        final Intent intent = new Intent(context, GameDetailActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_detail);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getExtra();
    }

    private void getExtra() {
        Intent intent = getIntent();
        final String gameId = intent.getStringExtra(EXTRA_GAME_ID);
    }
}
