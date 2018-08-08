package com.zebannikolay.capstone.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.RecentBoardGame;

@Database(entities = {BoardGame.class, RecentBoardGame.class}, version = 1)
public abstract class BoardGameDataBase extends RoomDatabase {

    public abstract FavoriteBoardGameDao favoriteBoardGameDao();

    public abstract RecentBoardGameDao recentBoardGameDao();

}
