package com.example.ludit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.ludit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FilhoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho);

        mostrarGrafico(); //https://github.com/AnyChart/AnyChart-Android
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_filho, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //tratar opcoes do menu
        return super.onOptionsItemSelected(item);
    }

    private void mostrarGrafico(){
        Cartesian grafico = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        grafico.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.graficoFilho);
        anyChartView.setChart(grafico);
    }

}
