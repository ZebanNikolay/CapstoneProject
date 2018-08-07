package com.zebannikolay.capstone.data.local;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class FavoriteBoardGamesDataSourceImpl implements FavoriteBoardGamesDataSource {

    private final BoardGameDao dataBase;

    public FavoriteBoardGamesDataSourceImpl(@NonNull final BoardGameDao dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Single<List<BoardGame>> favoriteGames() {
        return dataBase.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<BoardGame> favoriteGame(@NonNull String id) {
        return dataBase.findById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable addFavoriteGame(@NonNull BoardGame game) {
        return Completable.fromAction(() -> dataBase.insertAll(game))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFavoriteGame(@NonNull final BoardGame game) {
        return Completable.fromAction(() -> dataBase.delete(game))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
