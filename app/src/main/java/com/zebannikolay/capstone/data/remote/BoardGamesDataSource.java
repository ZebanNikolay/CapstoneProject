package com.zebannikolay.capstone.data.remote;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BoardGamesDataSource {

    Single<BoardGame> game(@NonNull final String id);

    Single<HashMap<String, BoardGame>> games();

    Completable addGames(@NonNull final HashMap<String, BoardGame> games);

}
