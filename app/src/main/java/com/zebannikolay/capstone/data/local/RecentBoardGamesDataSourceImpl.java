package com.zebannikolay.capstone.data.local;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.RecentBoardGame;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RecentBoardGamesDataSourceImpl implements RecentBoardGamesDataSource {

    private final RecentBoardGameDao dataBase;

    public RecentBoardGamesDataSourceImpl(@NonNull final RecentBoardGameDao dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Single<List<RecentBoardGame>> recentGames() {
        return dataBase.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable addRecentGame(@NonNull RecentBoardGame game) {
        return Completable.fromAction(() -> dataBase.insertAll(game))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteRecentGame(@NonNull RecentBoardGame game) {
        return Completable.fromAction(() -> dataBase.delete(game))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
