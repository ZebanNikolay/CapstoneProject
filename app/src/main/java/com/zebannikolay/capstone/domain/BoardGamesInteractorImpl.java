package com.zebannikolay.capstone.domain;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.data.BoardGameRepository;
import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public final class BoardGamesInteractorImpl implements BoardGamesInteractor {

    private final BoardGameRepository repository;

    public BoardGamesInteractorImpl(@NonNull final BoardGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<BoardGame> game(@NonNull String id) {
        return repository.game(id);
    }

    @Override
    public Single<List<BoardGame>> games() {
        return repository.games()
                .map(map -> new ArrayList<>(map.values()));
    }

    @Override
    public Single<List<BoardGamePreview>> gamesPreviews() {
        return games()
                .flatMapObservable(Observable::fromIterable)
                .map(this::convertBoardGame)
                .toList();
    }

    private BoardGamePreview convertBoardGame(@NonNull final BoardGame game) {
        return new BoardGamePreview(game.getId(), game.getTitle(), game.getImageUrl());
    }

    @Override
    public Completable addGames(@NonNull List<BoardGame> games) {
        final HashMap<String, BoardGame> gamesMap = new HashMap<>(games.size());
        for (BoardGame game : games) {
            gamesMap.put(game.getId(), game);
        }
        return repository.addGames(gamesMap);
    }
}
