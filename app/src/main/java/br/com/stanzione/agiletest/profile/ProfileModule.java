package br.com.stanzione.agiletest.profile;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class ProfileModule {

    @Provides
    @Singleton
    ProfileContract.Presenter providesPresenter(ProfileContract.Model model){
        ProfilePresenter presenter = new ProfilePresenter(model);
        return presenter;
    }

    @Provides
    @Singleton
    ProfileContract.Model providesModel(Realm realm){
        ProfileModel model = new ProfileModel(realm);
        return model;
    }

}
