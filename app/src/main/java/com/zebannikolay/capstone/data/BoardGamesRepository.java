package com.zebannikolay.capstone.data;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.data.local.FavoriteBoardGamesDataSource;
import com.zebannikolay.capstone.data.local.RecentBoardGamesDataSource;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSource;
import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.RecentBoardGame;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class BoardGamesRepository implements BoardGamesDataSource, FavoriteBoardGamesDataSource, RecentBoardGamesDataSource {

    private final BoardGamesDataSource boardGamesDataSource;
    private final RecentBoardGamesDataSource recentBoardGamesDataSource;
    private final FavoriteBoardGamesDataSource favoriteBoardGamesDataSource;

    public BoardGamesRepository(@NonNull final BoardGamesDataSource boardGamesDataSource,
                                @NonNull final RecentBoardGamesDataSource recentBoardGamesDataSource,
                                @NonNull final FavoriteBoardGamesDataSource favoriteBoardGamesDataSource) {
        this.boardGamesDataSource = boardGamesDataSource;
        this.recentBoardGamesDataSource = recentBoardGamesDataSource;
        this.favoriteBoardGamesDataSource = favoriteBoardGamesDataSource;
    }

    @Override
    public Single<List<BoardGame>> favoriteGames() {
        return favoriteBoardGamesDataSource.favoriteGames();
    }

    @Override
    public Single<BoardGame> favoriteGame(@NonNull final String id) {
        return favoriteBoardGamesDataSource.favoriteGame(id);
    }

    @Override
    public Completable addFavoriteGame(@NonNull final BoardGame game) {
        return favoriteBoardGamesDataSource.addFavoriteGame(game);
    }

    @Override
    public Completable deleteFavoriteGame(@NonNull final BoardGame game) {
        return favoriteBoardGamesDataSource.deleteFavoriteGame(game);
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

    public Single<Boolean> isFavorite(@NonNull final String id) {
        return favoriteBoardGamesDataSource.favoriteGame(id)
                .map(game -> game != null);
    }

    @Override
    public Single<List<RecentBoardGame>> recentGames() {
        return recentBoardGamesDataSource.recentGames();
    }

    @Override
    public Completable addRecentGame(@NonNull final RecentBoardGame game) {
        return recentBoardGamesDataSource.addRecentGame(game);
    }

    @Override
    public Completable deleteRecentGame(@NonNull final RecentBoardGame game) {
        return recentBoardGamesDataSource.deleteRecentGame(game);
    }

}
