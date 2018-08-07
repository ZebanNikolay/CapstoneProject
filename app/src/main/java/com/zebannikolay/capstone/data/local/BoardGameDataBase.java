package com.zebannikolay.capstone.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.zebannikolay.capstone.domain.models.BoardGame;

@Database(entities = {BoardGame.class}, version = 1)
public abstract class BoardGameDataBase extends RoomDatabase {

    public abstract BoardGameDao boardGameDao();

}
