package br.com.stanzione.agiletest.di;

import javax.inject.Singleton;

import br.com.stanzione.agiletest.home.HomeActivity;
import br.com.stanzione.agiletest.home.HomeModule;
import br.com.stanzione.agiletest.profile.ProfileActivity;
import br.com.stanzione.agiletest.profile.ProfileModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                HomeModule.class,
                ProfileModule.class
        }
)
public interface ApplicationComponent {
    void inject(HomeActivity activity);
    void inject(ProfileActivity activity);
}
