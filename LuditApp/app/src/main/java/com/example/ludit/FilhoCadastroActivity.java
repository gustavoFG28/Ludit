package com.example.ludit;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ludit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilhoCadastroActivity extends AppCompatActivity {

    EditText edtNome,edtDeficiencia,edtTexto;
    DatePicker dataNascimento;
    Button btnCadastrarFilho;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho_cadastro);

        sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        final String email = sharedPreferences.getString("email", null);

        edtNome = (EditText) findViewById(R.id.edNome);
        edtDeficiencia = (EditText) findViewById(R.id.edDeficiencia);
        edtTexto = (EditText) findViewById(R.id.edTexto);
        dataNascimento = (DatePicker) findViewById(R.id.dtNascimento);
        btnCadastrarFilho  = (Button)findViewById(R.id.btnCadastrarFilho);

        btnCadastrarFilho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cadastarFilho(email);
            }
        });
    }

    public  void  cadastarFilho(String email) {
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(FilhoCadastroActivity.this);
        dlgAlert.setTitle("LUDIT - Erro no Cadastro do Filho");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, dataNascimento.getYear());
        c.set(Calendar.MONTH, dataNascimento.getMonth());
        c.set(Calendar.DAY_OF_MONTH, dataNascimento.getDayOfMonth());
        String date = dateFormat.format(c);

        UserService service = RetrofitConfig.getClient().create(UserService.class);

        Filho filho = new Filho(date, edtTexto.getText().toString(), "null", edtNome.getText().toString(), edtDeficiencia.getText().toString());
        if(edtTexto.getText().toString() == null || edtDeficiencia.getText().toString() == null || edtNome.getText().toString() == null || date == null)
        {
            dlgAlert.setMessage("Preencha todos os Campos");
            dlgAlert.create().show();
        }else
        {
            Call<List<Filho>> call = service.inserirFilho(email, filho);

            call.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                    if(!response.isSuccessful()){

                        dlgAlert.setMessage("Erro ao Cadastrar, verifique os dados");
                        dlgAlert.create().show();
                        return;
                    }

                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                    editor.putString("nomeFilho", response.body().get(0).getNome());
                    editor.commit();

                    Intent i = new Intent(FilhoCadastroActivity.this, Filho.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            });
        }

    }
}
