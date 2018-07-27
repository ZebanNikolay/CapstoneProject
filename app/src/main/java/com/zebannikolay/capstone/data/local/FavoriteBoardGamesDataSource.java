package com.zebannikolay.capstone.data.local;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FavoriteBoardGamesDataSource {

    Single<List<BoardGame>> favoriteGames();

    Completable addFavoriteGame(@NonNull final BoardGame game);

}
