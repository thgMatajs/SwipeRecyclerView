package com.thgmobi.swiperecyclerview.Helper;

import com.thgmobi.swiperecyclerview.remote.IMenuRequest;
import com.thgmobi.swiperecyclerview.remote.RetrofitClient;

public class Common {

    public static IMenuRequest getMenuRequest(){
        return RetrofitClient.getRetrofit("https://api.androidhive.info/").create(IMenuRequest.class);
    }

}
