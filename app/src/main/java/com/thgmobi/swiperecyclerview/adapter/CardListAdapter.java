package com.thgmobi.swiperecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thgmobi.swiperecyclerview.R;
import com.thgmobi.swiperecyclerview.model.Item;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {

    private Context context;
    private List<Item> list;

    public CardListAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = list.get(position);
        holder.name.setText(item.getName());
        holder.descricao.setText(item.getDescription());
        holder.preco.setText(item.getPrice());
        Picasso.get().load(item.getThumbnail()).into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Item item, int position){
        list.add(position, item);
        notifyItemInserted(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView descricao;
        public TextView preco;
        public ImageView foto;

        public ConstraintLayout vBackground;
        public ConstraintLayout vForeground;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            descricao = itemView.findViewById(R.id.tv_descricao);
            preco = itemView.findViewById(R.id.tv_preco);

            foto = itemView.findViewById(R.id.iv_foto);

            vBackground = itemView.findViewById(R.id.vBackground);
            vForeground = itemView.findViewById(R.id.vForeground);
        }
    }
}
