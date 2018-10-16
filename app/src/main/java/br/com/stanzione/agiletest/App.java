package br.com.stanzione.agiletest;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import br.com.stanzione.agiletest.di.ApplicationComponent;
import br.com.stanzione.agiletest.di.DaggerApplicationComponent;
import br.com.stanzione.agiletest.di.NetworkModule;
import br.com.stanzione.agiletest.home.HomeModule;
import br.com.stanzione.agiletest.profile.ProfileModule;
import io.realm.Realm;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .homeModule(new HomeModule())
                .profileModule(new ProfileModule())
                .build();

        Realm.init(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @VisibleForTesting
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

}
