package br.com.stanzione.agiletest.home;

import java.util.List;

import br.com.stanzione.agiletest.api.GitHubApi;
import br.com.stanzione.agiletest.data.Owner;
import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.Observable;
import io.realm.Realm;

public class HomeModel implements HomeContract.Model {

    private GitHubApi gitHubApi;
    private Realm realm;

    public HomeModel(GitHubApi gitHubApi, Realm realm){
        this.gitHubApi = gitHubApi;
        this.realm = realm;
    }

    @Override
    public Observable<List<Repository>> searchGitRepositories(String username) {
        return gitHubApi.getRepositories(username);
    }

    @Override
    public void saveRepositoriesToDatabase(final List<Repository> repositoryList) {


        realm.executeTransaction(new Realm.Transaction() {
              @Override
              public void execute(Realm bgRealm) {
                  bgRealm.delete(Owner.class);
                  bgRealm.delete(Repository.class);
                  bgRealm.copyToRealm(repositoryList);
              }
          });

    }
}
