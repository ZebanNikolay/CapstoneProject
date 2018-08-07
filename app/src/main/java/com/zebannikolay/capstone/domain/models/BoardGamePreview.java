package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;

public final class BoardGamePreview {

    private final String id;
    private final String title;
    private final String imageUrl;
    private boolean favorite;

    public BoardGamePreview(@NonNull final String id, @NonNull final String title, @NonNull final String imageUrl, final boolean favorite) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.favorite = favorite;
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

    public boolean isFavorite() {
        return favorite;
    }

}
