package com.zebannikolay.capstone.presentation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.EmptyResultSetException;
import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGame;

import timber.log.Timber;

public final class GameDetailsViewModel {

    private final BoardGamesInteractor interactor;
    private final String gameId;

    private final MutableLiveData<BoardGame> game = new MutableLiveData<>();
    private final MutableLiveData<Boolean> favorite = new MutableLiveData<>();
    private final MutableLiveData<String> playReviewEvent = new MutableLiveData<>();
    private final MutableLiveData<String> openRulesEvent = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiState = new MutableLiveData<>();

    public GameDetailsViewModel(@NonNull final BoardGamesInteractor interactor, @NonNull final String gameId) {
        this.interactor = interactor;
        this.gameId = gameId;

        fetchGame();
        checkFavorite();
    }

    @SuppressLint("CheckResult")
    private void checkFavorite() {
        interactor.isFavorite(gameId)
                .subscribe(favorite::setValue, throwable -> {
                    if (throwable instanceof EmptyResultSetException) {
                        favorite.setValue(false);
                        return;
                    }
                    Timber.e(throwable);
                });
    }

    @SuppressLint("CheckResult")
    private void fetchGame() {
        uiState.setValue(UiState.LOADING);
        interactor.game(gameId)
                .subscribe(game -> {
                    this.game.setValue(game);
                    updateRecent(game);
                    uiState.setValue(UiState.CONTENT);
                }, t -> {
                    Timber.e(t);
                    uiState.setValue(UiState.FAIL);
                });
    }

    @SuppressLint("CheckResult")
    public void updateRecent(@NonNull final BoardGame game) {
        interactor.addRecentGame(game)
                .subscribe(() -> {}, Timber::e);
    }

    public void onPlayReview(@NonNull final String videoId) {
        playReviewEvent.setValue(videoId);
    }

    public void onDownloadRules(@NonNull final String rulesUrl) {
        openRulesEvent.setValue(rulesUrl);
    }

    @SuppressLint("CheckResult")
    public void onFavoriteClick() {
        final BoardGame boardGame = game.getValue();
        if (favorite.getValue()) {
            deleteFavorite(boardGame);
        } else {
            addFavorite(boardGame);
        }

    }

    @SuppressLint("CheckResult")
    private void deleteFavorite(@NonNull final BoardGame game) {
        interactor.deleteFavoriteGame(game)
                .subscribe(() -> favorite.setValue(false), Timber::e);
    }

    @SuppressLint("CheckResult")
    private void addFavorite(@NonNull final BoardGame boardGame) {
        interactor.addFavoriteGame(boardGame)
                .subscribe(() -> favorite.setValue(true), Timber::e);
    }

    public LiveData<BoardGame> getGame() {
        return game;
    }

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public LiveData<String> getPlayReviewEvent() {
        return playReviewEvent;
    }

    public LiveData<String> getOpenRulesEvent() {
        return openRulesEvent;
    }

    public LiveData<Boolean> isFavorite() {
        return favorite;
    }
}
