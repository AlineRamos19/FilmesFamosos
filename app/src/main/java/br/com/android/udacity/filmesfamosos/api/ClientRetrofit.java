package br.com.android.udacity.filmesfamosos.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRetrofit {

    private  Retrofit retrofit = null;

    public ClientRetrofit(String url){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public IRouteApi getApiService(){
        return this.retrofit.create(IRouteApi.class);
    }
}









