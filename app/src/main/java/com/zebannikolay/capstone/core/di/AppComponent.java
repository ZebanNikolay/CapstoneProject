package com.zebannikolay.capstone.core.di;

import com.zebannikolay.capstone.presentation.FavoriteGamesListFragment;
import com.zebannikolay.capstone.presentation.GameDetailsActivity;
import com.zebannikolay.capstone.presentation.GamesListFragment;
import com.zebannikolay.capstone.presentation.RecentGamesListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ContextModule.class,
        BoardGamesModule.class
})
@Singleton
public interface AppComponent {

    void inject(GamesListFragment gamesListFragment);
    void inject(FavoriteGamesListFragment favoriteGamesListFragment);
    void inject(RecentGamesListFragment recentGamesListFragment);
    void inject(GameDetailsActivity gameDetailsActivity);

}
