package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;

public final class BoardGamePreview {

    private final String title;
    private final String url;

    public BoardGamePreview(@NonNull final String title,@NonNull final String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
