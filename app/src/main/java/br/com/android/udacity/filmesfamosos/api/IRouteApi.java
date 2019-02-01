package br.com.android.udacity.filmesfamosos.api;


import br.com.android.udacity.filmesfamosos.models.MoviesReceiver;
import br.com.android.udacity.filmesfamosos.models.ReviewsReceiver;
import br.com.android.udacity.filmesfamosos.models.VideoReceiver;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IRouteApi {


    @GET
    Call<MoviesReceiver> getAllMovies(
            @Url String url,
            @Query("api_key") String apiKey,
            @Query("language") String language);


    @GET("3/movie/{movie_id}/videos")
    Call<VideoReceiver> getTrailer(@Path("movie_id") String id,
                                   @Query("api_key") String apiKey,
                                   @Query("language") String language);

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewsReceiver> getReviews(@Path("movie_id") String id,
                                     @Query("api_key") String apiKey);


}
