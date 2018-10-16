package br.com.stanzione.agiletest.profile;

import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.Observable;

public interface ProfileContract {

    interface View {
        void showRepositoryList(List<Repository> repositoryList);
        void showProfileImage(String imageUrl);
    }

    interface Presenter {
        void attachView(ProfileContract.View view);
        void loadRepositories();
        void dispose();
    }

    interface Model {
        Observable<List<Repository>> fetchRepositoriesFromDatabase();
    }

}
