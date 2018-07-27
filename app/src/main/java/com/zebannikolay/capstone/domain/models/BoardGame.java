package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public final class BoardGame {

    @NonNull private String id;

    @NonNull private String title;

    @NonNull private String rules;

    @NonNull private String imageUrl;

    @Nullable private String videoUrl;

    public BoardGame() {
    }

    public BoardGame(@NonNull final String title, @NonNull final String rules, @NonNull final String  imageUrl, @Nullable final String  videoUrl) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.rules = rules;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getRules() {
        return rules;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public String getVideoUrl() {
        return videoUrl;
    }
}
