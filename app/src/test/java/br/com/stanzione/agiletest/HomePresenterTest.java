package br.com.stanzione.agiletest;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.agiletest.home.HomeContract;
import br.com.stanzione.agiletest.home.HomePresenter;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {

    private HomeContract.View mockView;
    private HomeContract.Model mockModel;
    private HomePresenter presenter;

    private String username = "username";

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

        mockView = mock(HomeContract.View.class);
        mockModel = mock(HomeContract.Model.class);

        presenter = new HomePresenter(mockModel);
        presenter.attachView(mockView);

    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void withUsernameShouldFetchRepositories(){

        when(mockModel.searchGitRepositories(anyString())).thenReturn(Observable.just(new ArrayList<>()));

        presenter.searchGitRepositories(username);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).navigateToProfile(username);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showErrorMessage();
        verify(mockView, never()).showNetworkErrorMessage();
        verify(mockModel, times(1)).saveRepositoriesToDatabase(anyList());

    }

    @Test
    public void withWrongUsernameShouldShowMessage(){

        when(mockModel.searchGitRepositories(anyString())).thenReturn(Observable.error(new Throwable()));

        presenter.searchGitRepositories(anyString());

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showErrorMessage();
        verify(mockView, never()).showNetworkErrorMessage();
        verify(mockView, never()).navigateToProfile(anyString());
        verify(mockModel, never()).saveRepositoriesToDatabase(anyList());

    }

    @Test
    public void withNoInternetShouldShowMessage(){

        when(mockModel.searchGitRepositories(anyString())).thenReturn(Observable.error(new IOException()));

        presenter.searchGitRepositories(anyString());

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showNetworkErrorMessage();
        verify(mockView, never()).showErrorMessage();
        verify(mockView, never()).navigateToProfile(anyString());
        verify(mockModel, never()).saveRepositoriesToDatabase(anyList());

    }

}
