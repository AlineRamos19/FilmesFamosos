package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.content.Context;

public interface IVideoPresenter {

       boolean statusNetworkInfo(Context context);
       void getVideoApi(String id);
}
