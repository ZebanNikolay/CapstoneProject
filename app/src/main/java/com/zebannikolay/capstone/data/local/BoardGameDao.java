package com.zebannikolay.capstone.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BoardGameDao {

    @Query("SELECT * FROM favorite")
    Single<List<BoardGame>> getAll();

    @Query("SELECT * FROM favorite WHERE id LIKE :id LIMIT 1")
    Single<BoardGame> findById(String id);

    @Insert
    void insertAll(BoardGame... boardGames);

    @Delete
    void delete(BoardGame boardGame);

}
