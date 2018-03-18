package com.example.martin.recyclingapp.history;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.Item;

import java.util.ArrayList;

/**
 * Created by charlie on 2018-02-02.
 */

public class HistoryFragment extends Fragment {

    ArrayList<Item> items;
    private RecyclerView historyList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyList = view.findViewById(R.id.historyList);



        return view;
    }
}
