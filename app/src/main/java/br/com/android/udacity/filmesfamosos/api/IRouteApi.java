package br.com.android.udacity.filmesfamosos.api;


import br.com.android.udacity.filmesfamosos.models.MoviesReceiver;
import br.com.android.udacity.filmesfamosos.models.VideoReceiver;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRouteApi {

    @GET("3/movie/upcoming")
    Call<MoviesReceiver> getAllMoviesComing(@Query("api_key") String apiKey,
                                            @Query("language") String language);

    @GET("3/movie/top_rated")
    Call<MoviesReceiver> getAllMoviesTopRated(@Query("api_key") String apiKey,
                                              @Query("language") String language);

    @GET("3/movie/popular")
    Call<MoviesReceiver> getAllMoviesPopular(@Query("api_key") String apiKey,
                                             @Query("language") String language);

    @GET("3/movie/{movie_id}/videos")
    Call<VideoReceiver> getTrailer(@Path("movie_id") String id,
                                  @Query("api_key") String apiKey,
                                   @Query("language") String language);


}
