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

import com.example.martin.recyclingapp.MainActivity;
import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.ResultFragment;
import com.example.martin.recyclingapp.db.Item;
import com.facebook.share.model.ShareLinkContent;

import java.util.ArrayList;

/**
 * Created by martinhuynh on 17/03/2018.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Item> items;

    public HistoryListAdapter(Context context, ArrayList<Item> items) {
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.history_row, parent, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        MainActivity main = new MainActivity();
        holder.itemView.setOnClickListener(v -> {
            Bundle itemBundle = new Bundle();
            itemBundle.putString("name",
                    items.get(holder.getAdapterPosition()).getProductName());
            itemBundle.putString("category",
                    items.get(holder.getAdapterPosition()).getCategory());
            itemBundle.putString("dateScanned",
                    Long.toString(items.get(holder.getAdapterPosition()).getDateScanned()));
            itemBundle.putString("materials",
                    items.get(holder.getAdapterPosition()).getProductMaterial());
            itemBundle.putString("barcode",
                    items.get(holder.getAdapterPosition()).getBarcodeNumber());

            // Put product image

            ResultFragment result = new ResultFragment();
            result.setArguments(itemBundle);
            main.getFragmentManager().beginTransaction()
                    .add(android.R.id.content, result).commit();

        });

        return holder;

    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        Item current = items.get(position);
        holder.productName.setText(current.getProductName());
        holder.dateScanned.setText(Long.toString(current.getDateScanned())); //Create resource for this with format %d1 etc.
        holder.productMaterial.setText(current.getProductMaterial());
//        holder.productClass.setImageBitmap();

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public ShareLinkContent getShareLinkContent() {
        return new ShareLinkContent.Builder()
                .setQuote("Check out the app here!")
                .setContentUrl(Uri.parse("https://github.com/04HuynhM"))
                .build();
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
