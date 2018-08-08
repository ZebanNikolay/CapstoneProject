package com.zebannikolay.capstone.data.local;

import android.support.annotation.NonNull;

import com.zebannikolay.capstone.domain.models.BoardGame;
import com.zebannikolay.capstone.domain.models.RecentBoardGame;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RecentBoardGamesDataSource {

    Single<List<RecentBoardGame>> recentGames();

    Completable addRecentGame(@NonNull final RecentBoardGame game);

    Completable deleteRecentGame(@NonNull final RecentBoardGame game);

}
