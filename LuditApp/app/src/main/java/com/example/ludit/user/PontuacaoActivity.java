package com.example.ludit.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ludit.R;
import com.example.ludit.webservice.Habilidade;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PontuacaoActivity extends AppCompatActivity {

    HashMap<String, Habilidade> habilidades;
    ArrayList<String> arrayNomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        Intent i = getIntent();
        String nome = i.getStringExtra("nomeFilho");
        UserService service = RetrofitConfig.getClient().create(UserService.class);
        Call<List<Habilidade>> call = service.habilidades(getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).getString("email", ""), nome);
        //mostrarGrafico(); //https://github.com/AnyChart/AnyChart-Android

        habilidades = new HashMap<String, Habilidade>();
        arrayNomes = new ArrayList<>();

        call.enqueue(new Callback<List<Habilidade>>() {
            @Override
            public void onResponse(Call<List<Habilidade>> call, Response<List<Habilidade>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                for(Habilidade habilidade : response.body()) {
                    habilidades.put(habilidade.getNome(), habilidade);
                    arrayNomes.add(habilidade.getNome());
                }

                mostrarGrafico();
            }

            @Override
            public void onFailure(Call<List<Habilidade>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });



        ((Button)findViewById(R.id.btnSair)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void mostrarGrafico(){

        RadarChart chart = (RadarChart) findViewById(R.id.graficoFilho);

        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.BLACK);
        chart.getDescription().setEnabled(false);

        chart.animateXY(1400,1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(18f);
        xAxis.setXOffset(0);
        xAxis.setYOffset(0);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] qualities = getNomes();


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return qualities[(int) value % qualities.length];
            }
        });
        xAxis.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.endColorGradient));

        chart.getLegend().setEnabled(false);
        YAxis yAxis = chart.getYAxis();

        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(18f);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);

        ArrayList<RadarEntry> employe = new ArrayList<>();
        if(habilidades.containsKey("mat"))
            employe.add(new RadarEntry(habilidades.get("mat").getPorcentagem() * 100));

        if(habilidades.containsKey("mem"))
            employe.add(new RadarEntry(habilidades.get("mem").getPorcentagem() * 100));

        if(habilidades.containsKey("ref"))
            employe.add(new RadarEntry(habilidades.get("ref").getPorcentagem() * 100));

        if(habilidades.containsKey("rec"))
            employe.add(new RadarEntry(habilidades.get("rec").getPorcentagem() * 100));

        if(habilidades.containsKey("rac"))
            employe.add(new RadarEntry(habilidades.get("rac").getPorcentagem() * 100));

        RadarDataSet set = new RadarDataSet(employe, "");

        set.setColor(ContextCompat.getColor(getApplicationContext(), R.color.startColorGradient));
        set.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.startColorGradient));
        set.setDrawFilled(true);
        set.setFillAlpha(100);
        set.setLineWidth(2f);
        set.setDrawHighlightIndicators(true);
        set.setDrawHighlightCircleEnabled(true);


        RadarData data = new RadarData(set);

        chart.setData(data);
        chart.invalidate();

    }

    private String[] getNomes() {
        String[] nomes = new String[arrayNomes.size()];
        for(int i = 0; i < arrayNomes.size(); i++) {
            switch (arrayNomes.get(i))
            {
                case "mat": nomes[i] = "Matematica";break;
                case "rec": nomes[i] = "Reciclagem";break;
                case "rac": nomes[i] = "Racicionio";break;
                case "ref": nomes[i] = "Reflexo";break;
                case "mem": nomes[i] = "Memoria"; break;
            }
        }

        return nomes;
    }

}
