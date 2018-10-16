package br.com.stanzione.agiletest.profile;

import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;
    private ProfileContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ProfilePresenter(ProfileContract.Model model){
        this.model = model;
    }

    @Override
    public void attachView(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void loadRepositories() {

        compositeDisposable.add(
                model.fetchRepositoriesFromDatabase()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::onReceiveRepositories
                    )
        );

    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }

    private void onReceiveRepositories(List<Repository> repositories) {
        view.showRepositoryList(repositories);

        if(!repositories.isEmpty()){
            view.showProfileImage(repositories.get(0).getOwner().getImageUrl());
        }

    }

}
