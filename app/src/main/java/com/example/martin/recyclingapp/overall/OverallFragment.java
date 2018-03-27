package com.example.martin.recyclingapp.overall;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.martin.recyclingapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by charlie on 2018-02-02.
 */

public class OverallFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall, container, false);

        TextView headerLink = view.findViewById(R.id.text_header_overall);
        TextView numberOfItemsGlobal = view.findViewById(R.id.text_content_number_global_overall);
        TextView numberOfItemsUser = view.findViewById(R.id.text_content_number_user_overall);
        TextView mostScannedMaterial = view.findViewById(R.id.text_content_most_meterial_overall);
        BarChart barChart = view.findViewById(R.id.bar_chart_overall);

        if(headerLink != null) {
            headerLink.setMovementMethod(LinkMovementMethod.getInstance());
        }
        setBarChart(barChart, getActivity().getResources().getColor(R.color.colorPrimary));
        numberOfItemsGlobal.setText("2356");
        numberOfItemsUser.setText("17");
        mostScannedMaterial.setText("Metal");

        return view;
    }

    private void setBarChart(BarChart barChart, int colorResource) {

        //TODO produce it with API when there's an API
        final float[] values =
                { 423f, 101f, 53f, 135f, 321f, 39f, 13f };
        final int leftAxisMaxValue = 430;
        final String[] xValues = new String[]
                { "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80" };

        IAxisValueFormatter formatter = (value, axis) -> xValues[(int) value];

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            barEntries.add(new BarEntry(i, values[i]));
        }

        BarDataSet set = new BarDataSet(barEntries, "BarDataSet");

        set.setColor(colorResource);
        set.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f);

        Description desc = new Description();
        desc.setText("");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setAxisMinValue(0);
        leftAxis.setAxisMaxValue(leftAxisMaxValue);
        leftAxis.setGranularity(1f);

        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);

        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(false);

        barChart.setDescription(desc);
        barChart.invalidate();
    }
}
