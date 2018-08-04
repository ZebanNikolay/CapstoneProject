package com.zebannikolay.capstone.core.di;

import com.zebannikolay.capstone.presentation.GamesListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ContextModule.class,
        BoardGamesModule.class
})
@Singleton
public interface AppComponent {

    void inject(GamesListFragment gamesListFragment);

}
