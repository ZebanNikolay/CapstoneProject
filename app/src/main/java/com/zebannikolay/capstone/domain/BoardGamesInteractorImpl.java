package com.zebannikolay.capstone.domain;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.data.BoardGamesRepository;
import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public final class BoardGamesInteractorImpl implements BoardGamesInteractor {

    private final BoardGamesRepository repository;

    public BoardGamesInteractorImpl(@NonNull final BoardGamesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<BoardGame> game(@NonNull final String id) {
        return repository.game(id);
    }

    @Override
    public Single<List<BoardGame>> games() {
        return repository.games()
                .map(map -> new ArrayList<>(map.values()));
    }

    @Override
    public Single<List<BoardGamePreview>> gamesPreviews() {
        return repository.games()
                .zipWith(repository.favoriteGames(), this::convertBoardGame);
    }

    @Override
    public Single<List<BoardGamePreview>> favoriteGamesPreviews() {
        return repository.favoriteGames()
                .map(this::convertBoardGame);
    }

    private List<BoardGamePreview> convertBoardGame(@NonNull final Map<String, BoardGame> allGames,
                                                    @NonNull final List<BoardGame> favoriteGames) {
        final List<BoardGamePreview> result = new ArrayList<>(allGames.size());
        for (BoardGame favoriteGame : favoriteGames) {
            final BoardGame game = allGames.get(favoriteGame.getId());
            result.add(new BoardGamePreview(game.getId(), game.getTitle(), game.getImageUrl(), true));
            allGames.remove(favoriteGame.getId());
        }
        for (BoardGame game : new ArrayList<>(allGames.values())) {
            result.add(new BoardGamePreview(game.getId(), game.getTitle(), game.getImageUrl(), false));
        }
        return result;
    }

    private List<BoardGamePreview> convertBoardGame(@NonNull final List<BoardGame> favoriteGames) {
        final List<BoardGamePreview> result = new ArrayList<>(favoriteGames.size());
        for (BoardGame game : favoriteGames) {
            result.add(new BoardGamePreview(game.getId(), game.getTitle(), game.getImageUrl(), true));
        }
        return result;
    }

    @Override
    public Completable addGames(@NonNull final List<BoardGame> games) {
        final HashMap<String, BoardGame> gamesMap = new HashMap<>(games.size());
        for (BoardGame game : games) {
            gamesMap.put(game.getId(), game);
        }
        return repository.addGames(gamesMap);
    }

    @Override
    public Completable addFavoriteGame(@NonNull final BoardGame game) {
        return repository.addFavoriteGame(game);
    }

    @Override
    public Completable deleteFavoriteGame(@NonNull final BoardGame game) {
        return repository.deleteFavoriteGame(game);
    }

    @Override
    public Single<Boolean> isFavorite(@NonNull final String id) {
        return repository.isFavorite(id);
    }
}
