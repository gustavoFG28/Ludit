package com.example.ludit.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.ludit.R;

import java.util.ArrayList;
import java.util.List;

public class PontuacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        //mostrarGrafico(); //https://github.com/AnyChart/AnyChart-Android
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
