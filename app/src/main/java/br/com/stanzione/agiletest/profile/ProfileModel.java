package br.com.stanzione.agiletest.profile;

import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileModel implements ProfileContract.Model {

    private Realm realm;

    public ProfileModel(Realm realm){
        this.realm = realm;
    }

    @Override
    public Observable<List<Repository>> fetchRepositoriesFromDatabase() {

        RealmResults<Repository> results = realm.where(Repository.class).findAll();
        return Observable.just(realm.copyFromRealm(results));

    }
}
