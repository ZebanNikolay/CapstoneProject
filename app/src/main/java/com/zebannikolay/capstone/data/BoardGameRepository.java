package com.zebannikolay.capstone.data;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.data.local.FavoriteBoardGamesDataSource;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSource;
import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class BoardGameRepository implements BoardGamesDataSource, FavoriteBoardGamesDataSource {

    private final BoardGamesDataSource boardGamesDataSource;
    private final FavoriteBoardGamesDataSource favoriteBoardGamesDataSource;

    public BoardGameRepository(@NonNull final BoardGamesDataSource boardGamesDataSource, @NonNull final FavoriteBoardGamesDataSource favoriteBoardGamesDataSource) {
        this.boardGamesDataSource = boardGamesDataSource;
        this.favoriteBoardGamesDataSource = favoriteBoardGamesDataSource;
    }

    @Override
    public Single<List<BoardGame>> favoriteGames() {
        return favoriteBoardGamesDataSource.favoriteGames();
    }

    @Override
    public Completable addFavoriteGame(@NonNull final BoardGame game) {
        return favoriteBoardGamesDataSource.addFavoriteGame(game);
    }

    @Override
    public Single<BoardGame> game(@NonNull final String id) {
        return boardGamesDataSource.game(id);
    }

    @Override
    public Single<HashMap<String, BoardGame>> games() {
        return boardGamesDataSource.games();
    }

    @Override
    public Completable addGames(@NonNull final HashMap<String, BoardGame> games) {
        return boardGamesDataSource.addGames(games);
    }
}
