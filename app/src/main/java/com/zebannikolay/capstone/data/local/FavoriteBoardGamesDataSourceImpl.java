package com.zebannikolay.capstone.data.local;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class FavoriteBoardGamesDataSourceImpl implements FavoriteBoardGamesDataSource {
    @Override
    public Single<List<BoardGame>> favoriteGames() {
        return null;
    }

    @Override
    public Single<BoardGame> favoriteGame(@NonNull String id) {
        return null;
    }

    @Override
    public Completable addFavoriteGame(@NonNull BoardGame game) {
        return null;
    }
}
