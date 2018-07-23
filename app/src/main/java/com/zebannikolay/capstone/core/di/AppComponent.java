package com.zebannikolay.capstone.core.di;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ContextModule.class
})
@Singleton
public interface AppComponent {

}
