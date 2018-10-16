package br.com.stanzione.agiletest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.agiletest.data.Owner;
import br.com.stanzione.agiletest.data.Repository;
import br.com.stanzione.agiletest.profile.ProfileContract;
import br.com.stanzione.agiletest.profile.ProfilePresenter;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfilePresenterTest {

    private ProfileContract.View mockView;
    private ProfileContract.Model mockModel;
    private ProfilePresenter presenter;

    private List<Repository> repositoryList;

    @BeforeClass
    public static void setupRxSchedulers() {

        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }

    @Before
    public void setup() {

        mockView = mock(ProfileContract.View.class);
        mockModel = mock(ProfileContract.Model.class);

        presenter = new ProfilePresenter(mockModel);
        presenter.attachView(mockView);

        createRepositoryList();

    }

    private void createRepositoryList() {
        repositoryList = new ArrayList<>();

        Owner owner = new Owner();
        owner.setImageUrl("image url");

        Repository repository = new Repository();
        repository.setOwner(owner);

        repositoryList.add(repository);
    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void withRepositoriesShouldShowList(){

        when(mockModel.fetchRepositoriesFromDatabase()).thenReturn(Observable.just(repositoryList));

        presenter.loadRepositories();

        verify(mockModel, times(1)).fetchRepositoriesFromDatabase();
        verify(mockView, times(1)).showProfileImage(anyString());
        verify(mockView, times(1)).showRepositoryList(anyList());

    }

}
