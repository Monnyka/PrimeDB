package com.nyka.primedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class activity_dashboard extends AppCompatActivity {
    int count[]={10,20,50,15};
    String label[]={"Action","Drama","Crime","Horror","Comedy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        setUpPieChart();
    }

    private void setUpPieChart(){
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i=0;i<=count.length;i++) {
            pieEntries.add(new PieEntry(count[i], label[i]));
        }
        PieDataSet dataSet =new PieDataSet(pieEntries,"Dashboard");
        PieData data =new PieData(dataSet);
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieChart.setData(data);
        pieChart.invalidate();
    }


}
