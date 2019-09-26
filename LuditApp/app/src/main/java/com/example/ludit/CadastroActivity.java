package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ludit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtEmail, edtSenha, edtConSenha;
    Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = (EditText) findViewById(R.id.edNome);
        edtEmail = (EditText) findViewById(R.id.edEmail);
        edtConSenha = (EditText) findViewById(R.id.edConfimarSenha);
        edtSenha = (EditText) findViewById(R.id.edSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://177.220.18.104:3000/cadastrarUsuario";

                final HashMap<String, String> params = new HashMap<String, String>();

                params.put("nome", edtNome.getText().toString());
                params.put("email", edtEmail.getText().toString());
                params.put("senha", edtSenha.getText().toString());

                if(edtSenha.getText().toString().equals(edtConSenha.getText().toString()))
                    Conexao.enviarDados(params, url, getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                        editor.putString("email", result.getString("email"));
                        editor.commit();

                        Intent i = new Intent(CadastroActivity.this, FilhoCadastroActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError(String erro)  {
                            //avisar o usuário que a não foi possível inseri-lo
                    }
                });
                else {
                    //avisar o usuário que a senha não é compatível
                }
            }
        });
    }
}
