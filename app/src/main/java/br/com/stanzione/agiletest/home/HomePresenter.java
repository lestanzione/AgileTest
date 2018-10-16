package br.com.stanzione.agiletest.home;

import java.io.IOException;
import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    public HomeContract.View view;
    public HomeContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String username;

    public HomePresenter(HomeContract.Model model){
        this.model = model;
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void searchGitRepositories(String username) {

        this.username = username;
        view.setProgressBarVisible(true);

        compositeDisposable.add(
                model.searchGitRepositories(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::onReceiveRepositories,
                            this::onError
                    )
        );

    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    private void onReceiveRepositories(List<Repository> repositories){
        model.saveRepositoriesToDatabase(repositories);
        view.setProgressBarVisible(false);
        view.navigateToProfile(username);
    }

    private void onError(Throwable throwable) {
        view.setProgressBarVisible(false);

        if(throwable instanceof IOException){
            view.showNetworkErrorMessage();
        }
        else{
            view.showErrorMessage();
        }
    }

}
