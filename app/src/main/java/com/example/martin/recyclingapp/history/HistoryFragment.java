package com.example.martin.recyclingapp.history;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.adapters.HistoryListAdapter;
import com.example.martin.recyclingapp.db.AppDatabase;
import com.example.martin.recyclingapp.db.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 2018-02-02.
 */

public class HistoryFragment extends Fragment {

    List<Item> items;
    private RecyclerView historyList;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        items = new ArrayList<>();

        new Thread(() -> {
            items = AppDatabase.getAppDatabase(getActivity()).itemDao().getItems();
            getActivity().runOnUiThread(() -> {
                historyList = view.findViewById(R.id.historyList);
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                historyList.setItemAnimator(new DefaultItemAnimator());
                HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), items);
                historyList.setAdapter(adapter);
                historyList.setLayoutManager(layoutManager);
            });
        }).start();

        return view;
    }
}
