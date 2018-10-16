package br.com.stanzione.agiletest.api;

import java.util.List;

import br.com.stanzione.agiletest.data.Repository;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApi {

    @GET("users/{username}/repos")
    Observable<List<Repository>> getRepositories(@Path("username") String username);

}
