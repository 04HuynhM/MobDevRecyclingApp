package com.example.martin.recyclingapp.adapters;

import android.content.Context;
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
            itemBundle.putString("category",
                    items.get(holder.getAdapterPosition()).getProductCategory());
            itemBundle.putString("dateScanned",
                    items.get(holder.getAdapterPosition()).getDateScanned());
            itemBundle.putString("materials",
                    items.get(holder.getAdapterPosition()).getProductMaterial());
            itemBundle.putString("barcode",
                    items.get(holder.getAdapterPosition()).getBarcodeNumber());

            // Put product image

            ResultFragment result = new ResultFragment();
            result.setArguments(itemBundle);
            ((MainActivity) context).getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
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

        switch (current.getProductCategory()) {
                case "Paper":
                    holder.productCategory.setImageResource(R.drawable.ic_paper);
                    break;
                case "Plastic":
                    holder.productCategory.setImageResource(R.drawable.ic_bottle);
                    break;
                case "Burnable":
                    holder.productCategory.setImageResource(R.drawable.ic_burnables);
                    break;
                case "Lightbulb":
                    holder.productCategory.setImageResource(R.drawable.ic_bulb);
                    break;
                case "Battery":
                    holder.productCategory.setImageResource(R.drawable.ic_battery);
                    break;
                case "Can":
                    holder.productCategory.setImageResource(R.drawable.ic_can);
                    break;
                case "Oil":
                    holder.productCategory.setImageResource(R.drawable.ic_oil);
                    break;
        }
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
        ImageView productCategory;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.historyProductName);
            productMaterial = itemView.findViewById(R.id.historyMaterialTextView);
            dateScanned = itemView.findViewById(R.id.historyScanDate);
            //productImage = itemView.findViewById(R.id.productHistoryImage);
            productCategory = itemView.findViewById(R.id.historyCategoryImage);
        }
    }


}
