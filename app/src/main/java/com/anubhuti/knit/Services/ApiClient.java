package com.anubhuti.knit.Services;

import android.content.Context;

import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static Context context= ApplicationContextProvider.getContext();

    public static final String MAIN_DOMAIN = context.getResources().getString(R.string.MainDomain);



    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(MAIN_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();

    public Retrofit getClient(){
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public JsonObject convertArrayToJsonObject(String[] keys, Object[] values){
        JsonObject jsonObject=new JsonObject();
        for(int i=0;i<keys.length;i++){
            if(values[i] instanceof String){
                jsonObject.addProperty(keys[i], (String)values[i]);
            }else{
                jsonObject.addProperty(keys[i], (int)values[i]);
            }
        }
        return jsonObject;
    }

    public void apiErrorSync(Context context, retrofit2.Response<?> response){
        Config configr = new Config();
        Config.logd("errorE", response.toString());
        switch (response.code()){
            case 405:
                //configr.setLogMessage(Configr.ERROR_405);
                break;
            case 401:
                //configr.setLogMessage(Configr.ERROR_401);
                break;
            case 403:
                //configr.setLogMessage(Configr.ERROR_403);
                break;
            case 409:
                try{
                    // configr.setLogMessage(response.errorBody().toString());
                }catch (NullPointerException i){
                    //configr.setLogMessage(Configr.ERROR_409);
                }
                break;
            case 422:
                try{
                    //configr.setLogMessage(response.errorBody().toString());
                }catch (NullPointerException i){
                    //configr.setLogMessage(Configr.ERROR_422);
                }
                break;
            default:
                //configr.setLogMessage(Configr.ERROR_500);
                break;
        }
    }
    public void failureErrorSync(Context context, Throwable t){
        Config configr = new Config();
        if(t instanceof IOException){
            //configr.setLogMessage(Configr.ERROR_INTERNET);
        }else if(t instanceof IllegalStateException){
            //configr.setLogMessage(Configr.ERROR_ILLEGAL_STATE);
        }else{
            //configr.setLogMessage(Configr.ERROR_GENERAL);
        }
    }

    public void logicalErrorSync(Context context, Exception e){
        Config configr = new Config();
        //configr.setLogMessage(e.toString());
        Config.logd("errorE", e.toString());
    }

    public void apiErrorAsync(Context context, retrofit2.Response<?> response){
        Config.logd("errorE", response.toString());
        switch (response.code()){
            case 405:
                Config.toastShort(context, Config.ERROR_405);
                break;
            case 401:
                Config.toastShort(context, Config.ERROR_401);
                break;
            case 403:
                Config.toastShort(context, Config.ERROR_403);
                break;
            case 409:
                try{
                    //Configr.toastShortMessage(context,response.errorBody().toString());
                }catch (NullPointerException i){
                    Config.toastShort(context, Config.ERROR_409);
                }
                break;
            case 422:
                try{
                    Config.toastShort(context,response.errorBody().toString());
                }catch (NullPointerException i){
                    Config.toastShort(context, Config.ERROR_422);
                }
                break;
            default:
                Config.toastShort(context, Config.ERROR_500);
                break;
        }
    }
    public void failureErrorAsync(Context context, Throwable t){
        if(t instanceof IOException){
            Config.toastShort(context, Config.ERROR_INTERNET);
        }else if(t instanceof IllegalStateException){
            Config.toastShort(context, Config.ERROR_ILLEGAL_STATE);
        }else{
            Config.toastShort(context, Config.ERROR_GENERAL);
        }
    }

    public void logicalErrorAsync(Context context, Exception e){
        //Configr.toastShortMessage(context,"here");
        Config.toastShort(context,e.toString());
        Config.logd("errorE", e.toString());
    }

}