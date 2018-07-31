package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;

public final class BoardGamePreview {

    private final String id;
    private final String title;
    private final String imageUrl;

    public BoardGamePreview(@NonNull final String id, @NonNull final String title, @NonNull final String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
