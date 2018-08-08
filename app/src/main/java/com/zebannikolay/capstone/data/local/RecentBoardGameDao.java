package com.zebannikolay.capstone.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.RecentBoardGame;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface RecentBoardGameDao {

    @Query("SELECT * FROM resent ORDER BY time_stamp DESC")
    Single<List<RecentBoardGame>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(RecentBoardGame... boardGames);

    @Delete
    void delete(RecentBoardGame boardGame);

}
