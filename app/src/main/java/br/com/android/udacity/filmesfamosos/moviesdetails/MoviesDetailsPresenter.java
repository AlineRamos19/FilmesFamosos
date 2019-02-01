package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.api.ClientRetrofit;
import br.com.android.udacity.filmesfamosos.constant.ConstData;
import br.com.android.udacity.filmesfamosos.models.ResultReviews;
import br.com.android.udacity.filmesfamosos.models.ResultVideo;
import br.com.android.udacity.filmesfamosos.models.ReviewsReceiver;
import br.com.android.udacity.filmesfamosos.models.VideoReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesDetailsPresenter implements IMoviesDetailsPresenter {

    private MoviesDetailsView view;

    public MoviesDetailsPresenter(MoviesDetailsView view) {
        this.view = view;
    }

    @Override
    public boolean statusNetworkInfo(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();

        } catch (NullPointerException e) {
            view.showMessageToast(R.string.error_server);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void getVideoApi(String id) {
        Call<VideoReceiver> call = new ClientRetrofit(ConstData.URL_BASE)
                .getApiService()
                .getTrailer(id, ConstData.API_KEY, ConstData.LANGUAGE_DEFAULT);
        call.enqueue(new Callback<VideoReceiver>() {
            @Override
            public void onResponse(Call<VideoReceiver> call, Response<VideoReceiver> response) {
                if(response.isSuccessful()){
                    List<ResultVideo> listVideo = response.body().getResults();
                    if(listVideo.size() > 0){
                        view.showFrameVideo();
                        view.setupRecyclerVideo(listVideo);
                    }else{
                        view.hideFrameVideo();
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoReceiver> call, Throwable t) {

            }
        });
    }

    @Override
    public void getReviewsApi(String id) {
        Call<ReviewsReceiver> call = new ClientRetrofit(ConstData.URL_BASE)
                .getApiService().getReviews(id, ConstData.API_KEY);
        call.enqueue(new Callback<ReviewsReceiver>() {
            @Override
            public void onResponse(Call<ReviewsReceiver> call, Response<ReviewsReceiver> response) {
                if(response.isSuccessful()){
                    List<ResultReviews> listReviews = response.body().getResults();
                    if(listReviews.size() > 0){
                        view.showFrameReviews();
                        view.setupRecycerReviews(listReviews);
                    }else{
                        view.hideFrameReviews();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewsReceiver> call, Throwable t) {

            }
        });
    }
}
