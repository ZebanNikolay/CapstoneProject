package com.zebannikolay.capstone.presentation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.List;

import timber.log.Timber;

public final class FavoriteGamesListViewModel {

    private final BoardGamesInteractor interactor;

    private final MutableLiveData<List<BoardGamePreview>> gamesPreview = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiState = new MutableLiveData<>();

    public FavoriteGamesListViewModel(@NonNull final BoardGamesInteractor interactor) {
        this.interactor = interactor;
        fetchGamesPreviews();
    }

    @SuppressLint("CheckResult")
    public void fetchGamesPreviews() {
        uiState.setValue(UiState.LOADING);
        interactor.favoriteGamesPreviews()
                .subscribe(list -> {
                    gamesPreview.setValue(list);
                    uiState.setValue(UiState.CONTENT);
                }, t -> {
                    Timber.e(t);
                    uiState.setValue(UiState.FAIL);
                });
    }

    public LiveData<List<BoardGamePreview>> getGamesPreview() {
        return gamesPreview;
    }

    public MutableLiveData<UiState> getUiState() {
        return uiState;
    }
}
