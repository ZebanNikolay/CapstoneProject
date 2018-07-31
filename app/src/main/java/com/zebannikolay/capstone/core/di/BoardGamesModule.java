package com.zebannikolay.capstone.core.di;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zebannikolay.capstone.data.BoardGamesRepository;
import com.zebannikolay.capstone.data.local.FavoriteBoardGamesDataSource;
import com.zebannikolay.capstone.data.local.FavoriteBoardGamesDataSourceImpl;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSource;
import com.zebannikolay.capstone.data.remote.BoardGamesDataSourceImpl;
import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.BoardGamesInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BoardGamesModule {

    private static final String BOARD_GAMES_DATABASE_REFERENCE = "board_games";

    @Singleton
    @Provides
    public BoardGamesInteractor provideBoardGamesInteractor(@NonNull final BoardGamesRepository repository) {
        return new BoardGamesInteractorImpl(repository);
    }

    @Singleton
    @Provides
    public BoardGamesRepository provideBoardGameRepository(@NonNull final BoardGamesDataSource boardGamesDataSource, @NonNull final FavoriteBoardGamesDataSource favoriteBoardGamesDataSource) {
        return new BoardGamesRepository(boardGamesDataSource, favoriteBoardGamesDataSource);
    }

    @Singleton
    @Provides
    public BoardGamesDataSource provideBoardGamesDataSource() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(BOARD_GAMES_DATABASE_REFERENCE);
        return new BoardGamesDataSourceImpl(reference);
    }

    @Singleton
    @Provides
    public FavoriteBoardGamesDataSource provideFavoriteBoardGamesDataSource() {
        return new FavoriteBoardGamesDataSourceImpl();
    }
}