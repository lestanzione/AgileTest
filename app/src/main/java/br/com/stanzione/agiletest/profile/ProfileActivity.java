package br.com.stanzione.agiletest.profile;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.agiletest.App;
import br.com.stanzione.agiletest.Configs;
import br.com.stanzione.agiletest.R;
import br.com.stanzione.agiletest.data.Repository;
import br.com.stanzione.agiletest.profile.adapter.RepositoryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    @Inject
    ProfileContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.profileImageView)
    CircleImageView profileImageView;

    @BindView(R.id.repositoriesRecyclerView)
    RecyclerView repositoriesRecyclerView;

    private String username;
    private String imageUrl;
    private List<Repository> repositoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = getIntent().getStringExtra(Configs.ARG_USERNAME);

        setUpInjector();
        setUpUi();

        if(savedInstanceState == null) {
            presenter.loadRepositories();
        }
        else{
            imageUrl = savedInstanceState.getString(Configs.SAVED_IMAGE_URL);
            repositoryList = (List<Repository>) savedInstanceState.getSerializable(Configs.SAVED_REPOSITORY_LIST);
            showProfileImage(imageUrl);
            showRepositoryList(repositoryList);
        }

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        collapsingToolbarLayout.setTitle(username);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        repositoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repositoriesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Configs.SAVED_IMAGE_URL, imageUrl);
        outState.putSerializable(Configs.SAVED_REPOSITORY_LIST, (Serializable) repositoryList);
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    public void showRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
        repositoriesRecyclerView.setAdapter(new RepositoryAdapter(repositoryList));
    }

    @Override
    public void showProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
        Picasso.with(this).load(imageUrl).into(profileImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
