package com.example.martin.recyclingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.ResultFragment;
import com.example.martin.recyclingapp.db.Item;
import com.facebook.share.model.ShareLinkContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martinhuynh on 17/03/2018.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Item> items;
    private Context context;

    public HistoryListAdapter(Context context, List<Item> items) {
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.history_row, parent, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            Bundle itemBundle = new Bundle();
            itemBundle.putString("name",
                    items.get(holder.getAdapterPosition()).getProductName());
//            itemBundle.putString("category",
//                    items.get(holder.getAdapterPosition()).getCategory());
            itemBundle.putString("dateScanned",
                    items.get(holder.getAdapterPosition()).getDateScanned());
            itemBundle.putString("materials",
                    items.get(holder.getAdapterPosition()).getProductMaterial());
            itemBundle.putString("barcode",
                    items.get(holder.getAdapterPosition()).getBarcodeNumber());

            // Put product image

            ResultFragment result = new ResultFragment();
            result.setArguments(itemBundle);
            ((MainActivity) context).getFragmentManager().beginTransaction()
                    .add(android.R.id.content, result).commit();

        });

        return holder;

    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        Item current = items.get(position);
        holder.productName.setText(current.getProductName());
        holder.dateScanned.setText(current.getDateScanned());
        holder.productMaterial.setText(current.getProductMaterial());
//        holder.productClass.setImageBitmap();

    }

    @Override
    public int getItemCount() {
        return items.size();
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
            //productImage = itemView.findViewById(R.id.productHistoryImage);
            productClass = itemView.findViewById(R.id.historyClassImage);
        }
    }


}
