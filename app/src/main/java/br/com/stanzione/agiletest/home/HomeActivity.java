package br.com.stanzione.agiletest.home;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import javax.inject.Inject;

import br.com.stanzione.agiletest.App;
import br.com.stanzione.agiletest.Configs;
import br.com.stanzione.agiletest.R;
import br.com.stanzione.agiletest.profile.ProfileActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @Inject
    HomeContract.Presenter presenter;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.gitNameEditText)
    EditText gitNameEditText;

    @BindView(R.id.searchButton)
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpInjector();
        setUpUi();

    }

    private void setUpInjector(){
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    private void setUpUi(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    public void navigateToProfile(String username) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(Configs.ARG_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage() {
        showSnackbar(getResources().getString(R.string.message_user_not_found));
    }

    @Override
    public void showNetworkErrorMessage() {
        showSnackbar(getResources().getString(R.string.message_api_error));
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if(visible){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.searchButton)
    public void onSearchClicked(){
        String username = gitNameEditText.getText().toString().trim();
        presenter.searchGitRepositories(username);
    }

    private void showSnackbar(String message){
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.snackbar_default), view -> snackbar.dismiss());
        snackbar.show();
    }

}
