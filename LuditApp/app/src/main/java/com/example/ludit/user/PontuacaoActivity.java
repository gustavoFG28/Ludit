package com.example.ludit.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.example.ludit.R;
import com.example.ludit.webservice.Habilidade;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PontuacaoActivity extends AppCompatActivity {

    ArrayList<Habilidade> habilidades;
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

        call.enqueue(new Callback<List<Habilidade>>() {
            @Override
            public void onResponse(Call<List<Habilidade>> call, Response<List<Habilidade>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                habilidades = (ArrayList<Habilidade>) response.body();
            }

            @Override
            public void onFailure(Call<List<Habilidade>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });

        mostrarGrafico();
    }


    private void mostrarGrafico(){

        Cartesian grafico = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        if(habilidades != null) {
            for (Habilidade habilidade : habilidades) {
                switch (habilidade.getNome()) {
                    case "mat":
                        data.add(new ValueDataEntry("Matematica", habilidade.getPorcentagem() * 100));
                        break;

                    case "mem":
                        data.add(new ValueDataEntry("Memoria", habilidade.getPorcentagem() * 100));
                        break;

                    case "ref":
                        data.add(new ValueDataEntry("Reflexo", habilidade.getPorcentagem() * 100));
                        break;

                    case "rec":
                        data.add(new ValueDataEntry("Reciclagem", habilidade.getPorcentagem() * 100));
                        break;

                }
            }
        }
        data.add(new ValueDataEntry("Matematica", 0));
        data.add(new ValueDataEntry("Memoria", 0));
        data.add(new ValueDataEntry("Reflexo", 0));
        data.add(new ValueDataEntry("Reciclagem", 0));


        Column column = grafico.column(data);

	    grafico.title("Progresso");
	    grafico.xAxis(0).title("Habilidade");
	    grafico.yAxis(0).title("Porcentagem(%)");

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.graficoFilho);
        anyChartView.setChart(grafico);

    }
}
