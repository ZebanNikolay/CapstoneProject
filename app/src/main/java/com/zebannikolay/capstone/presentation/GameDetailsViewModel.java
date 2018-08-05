package com.zebannikolay.capstone.presentation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGame;

import timber.log.Timber;

public final class GameDetailsViewModel {

    private final BoardGamesInteractor interactor;

    private final MutableLiveData<BoardGame> game = new MutableLiveData<>();
    private final MutableLiveData<String> playReviewEvent = new MutableLiveData<>();
    private final MutableLiveData<String> openRulesEvent = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiState = new MutableLiveData<>();

    public GameDetailsViewModel(@NonNull final BoardGamesInteractor interactor) {
        this.interactor = interactor;
    }

    @SuppressLint("CheckResult")
    public void fetchGame(@NonNull final String gameId) {
        uiState.setValue(UiState.LOADING);
        interactor.game(gameId)
                .subscribe(game -> {
                    this.game.setValue(game);
                    uiState.setValue(UiState.CONTENT);
                }, t -> {
                    Timber.e(t);
                    uiState.setValue(UiState.FAIL);
                });
    }

    public void onPlayReview(@NonNull final String videoId) {
        playReviewEvent.setValue(videoId);
    }

    public void onDownloadRules(@NonNull final String rulesUrl) {
        openRulesEvent.setValue(rulesUrl);
    }

    public void onFavoriteClick() {
        final BoardGame boardGame = game.getValue();
        boardGame.setFavorite(!boardGame.isFavorite());
        game.setValue(boardGame);
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
}
