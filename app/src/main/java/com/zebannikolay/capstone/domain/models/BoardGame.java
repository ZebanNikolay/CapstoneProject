package com.zebannikolay.capstone.domain.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity(tableName = "favorite")
public final class BoardGame {

    @PrimaryKey
    @NonNull private String id;

    @ColumnInfo(name = "title")
    @NonNull private String title;

    @ColumnInfo(name = "summary")
    @NonNull private String summary;

    @ColumnInfo(name = "rules_url")
    @NonNull private String rulesUrl;

    @ColumnInfo(name = "image_url")
    @NonNull private String imageUrl;

    @ColumnInfo(name = "video_url")
    @Nullable private String videoUrl;

    public BoardGame() {}

    @Ignore
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

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setSummary(@NonNull String summary) {
        this.summary = summary;
    }

    public void setRulesUrl(@NonNull String rulesUrl) {
        this.rulesUrl = rulesUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVideoUrl(@Nullable String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
