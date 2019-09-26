package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FilhoCadastroActivity extends AppCompatActivity {

    EditText edtNome,edtDeficiencia,edtTexto;
    DatePicker dataNascimento;
    Button btnCadastrarFilho;
    TextView tvResposta;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho_cadastro);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        final String email = sharedPreferences.getString("email", "default@gmail.com");

        tvResposta = (TextView) findViewById(R.id.tvResposta);
        edtNome = (EditText) findViewById(R.id.edNome);
        edtDeficiencia = (EditText) findViewById(R.id.edDeficiencia);
        edtTexto = (EditText) findViewById(R.id.edTexto);
        dataNascimento = (DatePicker) findViewById(R.id.dtNascimento);
        btnCadastrarFilho  = (Button)findViewById(R.id.btnCadastrar);

        tvResposta.append("O email é: "+ email + "\n");

        btnCadastrarFilho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://177.220.18.104:3000/cadastrarFilho/" + email;

                final HashMap<String, String> params = new HashMap<String, String>();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = dateFormat.format(new Date(dataNascimento.getYear(), dataNascimento.getMonth(),dataNascimento.getDayOfMonth()));

                params.put("nome", edtNome.getText().toString());
                params.put("def", edtDeficiencia.getText().toString());
                params.put("texto", edtTexto.getText().toString());
                params.put("data", date);
                params.put("imgPerfil", "default");


                Conexao.enviarDados(params, url, getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                           tvResposta.append(result.getString("nome"));
                    }

                    @Override
                    public void onError(String erro)  {
                            //avisar o usuário que a não foi possível inseri-lo
                        }
                    });
            }
        });
    }
}
