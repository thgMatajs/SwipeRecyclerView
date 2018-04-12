package com.thgmobi.swiperecyclerview.remote;

import com.thgmobi.swiperecyclerview.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IMenuRequest {
    @GET
    Call<List<Item>> getMenuList (@Url String url);
}
