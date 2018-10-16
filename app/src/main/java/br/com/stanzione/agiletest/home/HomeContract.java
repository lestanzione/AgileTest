package br.com.stanzione.agiletest.home;

import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.Observable;

public interface HomeContract {

    interface View {
        void navigateToProfile(String username);
        void showErrorMessage();
        void showNetworkErrorMessage();
        void setProgressBarVisible(boolean visible);
    }

    interface Presenter {
        void attachView(HomeContract.View view);
        void searchGitRepositories(String username);
        void dispose();
    }

    interface Model {
        Observable<List<Repository>> searchGitRepositories(String username);
        void saveRepositoriesToDatabase(List<Repository> repositoryList);
    }

}
