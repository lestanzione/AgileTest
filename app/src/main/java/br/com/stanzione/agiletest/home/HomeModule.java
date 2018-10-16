package br.com.stanzione.agiletest.home;

import javax.inject.Singleton;

import br.com.stanzione.agiletest.api.GitHubApi;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class HomeModule {

    @Provides
    @Singleton
    HomeContract.Presenter providesPresenter(HomeContract.Model homeModel){
        HomePresenter presenter = new HomePresenter(homeModel);
        return presenter;
    }

    @Provides
    @Singleton
    HomeContract.Model providesModel(GitHubApi gitHubApi, Realm realm){
        HomeModel model = new HomeModel(gitHubApi, realm);
        return model;
    }

}
