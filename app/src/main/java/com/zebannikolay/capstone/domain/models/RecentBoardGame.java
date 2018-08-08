package com.zebannikolay.capstone.domain.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "resent")
public final class RecentBoardGame {

    @PrimaryKey
    @NonNull private String id;

    @ColumnInfo(name = "time_stamp")
    @NonNull private long timeStamp;

    public RecentBoardGame() {}

    @Ignore
    public RecentBoardGame(@NonNull final String id, final long timeStamp) {
        this.id = id;
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
