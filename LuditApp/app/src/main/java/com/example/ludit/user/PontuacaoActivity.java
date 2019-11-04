package com.example.ludit.user;

import android.content.Intent;
import android.os.Bundle;
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
    }


    private void mostrarGrafico(){

        Cartesian grafico = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        for(Habilidade habilidade : habilidades)
        {
            switch (habilidade.getNome())
            {
                case "mat":
                    data.add(new ValueDataEntry("Matem�tica", habilidade.getPorcentagem()*100));
                    break;

                case "mem":
                    data.add(new ValueDataEntry("Mem�ria", habilidade.getPorcentagem()*100));
                    break;

                case "ref":
                    data.add(new ValueDataEntry("Reflexo", habilidade.getPorcentagem()*100));
                    break;

                case "rec":
                    data.add(new ValueDataEntry("Reciclagem", habilidade.getPorcentagem()*100));
                    break;

            }
        }


        Column column = grafico.column(data);

	grafico.title("Progresso");
	grafico.xAxis(0).title("Habilidade");
	grafico.yAxis(0).title("Porcentagem");

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.graficoFilho);
        anyChartView.setChart(grafico);
    }
}
