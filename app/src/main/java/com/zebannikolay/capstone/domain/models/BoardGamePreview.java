package com.zebannikolay.capstone.domain.models;

import android.support.annotation.NonNull;

public final class BoardGamePreview {

    private final String title;

    public BoardGamePreview(@NonNull final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
