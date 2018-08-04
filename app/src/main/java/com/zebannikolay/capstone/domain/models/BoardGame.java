package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public final class BoardGame {

    @NonNull private String id;

    @NonNull private String title;

    @NonNull private String summary;

    @NonNull private String rulesUrl;

    @NonNull private String imageUrl;

    @Nullable private String videoUrl;

    public BoardGame() {}

    public BoardGame(@NonNull final String title,
                     @NonNull final String summary,
                     @NonNull final String rulesUrl,
                     @NonNull final String  imageUrl,
                     @Nullable final String  videoUrl) {
        this.summary = summary;
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.rulesUrl = rulesUrl;
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
    public String getSummary() {
        return summary;
    }

    @NonNull
    public String getRulesUrl() {
        return rulesUrl;
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
