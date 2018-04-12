package com.thgmobi.swiperecyclerview;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.thgmobi.swiperecyclerview.Helper.Common;
import com.thgmobi.swiperecyclerview.Helper.RecyclerItemTouchHelper;
import com.thgmobi.swiperecyclerview.Helper.RecyclerItemTouchHelperListener;
import com.thgmobi.swiperecyclerview.adapter.CardListAdapter;
import com.thgmobi.swiperecyclerview.model.Item;
import com.thgmobi.swiperecyclerview.remote.IMenuRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    private final String URL_API = "https://api.androidhive.info/json/menu.json";
    private RecyclerView rvMain;
    private List<Item> list;
    private CardListAdapter adapter;

    private CoordinatorLayout rootLayout;

    IMenuRequest mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getMenuRequest();

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Don Ramon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvMain = findViewById(R.id.rv_main);
        rootLayout = findViewById(R.id.rootLayout);

        list = new ArrayList<>();
        adapter = new CardListAdapter(this, list);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(rvMain);
        addItemToCart();
    }

    private void addItemToCart() {
        mService.getMenuList(URL_API).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                list.clear();
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof CardListAdapter.MyViewHolder){
            String name = list.get(viewHolder.getAdapterPosition()).getName();

            final Item deletedItem = list.get(viewHolder.getAdapterPosition());

            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            Snackbar snackbar = Snackbar.make(rootLayout, name + "foi removido da lista!", Snackbar.LENGTH_LONG);
            snackbar.setAction("DESFAZER", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoreItem(deletedItem, deleteIndex);
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
