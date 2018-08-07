package com.zebannikolay.capstone.domain;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BoardGamesInteractor {

    Single<BoardGame> game(@NonNull final String id);

    Single<List<BoardGame>> games();

    Single<List<BoardGamePreview>> gamesPreviews();

    Completable addGames(@NonNull final List<BoardGame> games);

    Completable addFavoriteGame(@NonNull final BoardGame game);

    Completable deleteFavoriteGame(@NonNull final BoardGame game);

    Single<Boolean> isFavorite(@NonNull final String id);

}
