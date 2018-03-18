package com.example.martin.recyclingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.Item;

import java.util.ArrayList;

/**
 * Created by martinhuynh on 17/03/2018.
 */

public class HistoryListAdapterRV extends RecyclerView.Adapter<HistoryListAdapterRV.HistoryViewHolder>{

    private LayoutInflater inflator;
    private ArrayList<Item> items;

    public HistoryListAdapterRV(Context context, ArrayList<Item> items){
        inflator = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.history_row, parent, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        Item current = items.get(position);

        //TODO
        //Fill out recyclerView with Items

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView productMaterial;
        TextView dateScanned;
        ImageView productImage;
        ImageView productClass;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.historyProductName);
            productMaterial = itemView.findViewById(R.id.historyMaterialTextView);
            dateScanned = itemView.findViewById(R.id.historyScanDate);
            productImage = itemView.findViewById(R.id.productHistoryImage);
            productClass = itemView.findViewById(R.id.historyClassImage);
        }
    }
}
