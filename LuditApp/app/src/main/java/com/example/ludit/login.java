package com.example.ludit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login extends AppCompatActivity {
    EditText edtLogin, edtSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edLogin);
        edtSenha = (EditText)findViewById(R.id.edSenha);
        btnLogin =(Button)findViewById(R.id.btnLogar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://177.220.18.104:3000/loginUsuario";

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("login", edtLogin.getText().toString());
                params.put("senha", edtSenha.getText().toString());

                Conexao.enviarDados(params, url, getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.putString("email", result.getString("email"));
                        editor.commit();

                        Intent i = new Intent(login.this, MainActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError(String erro) {

                    }
                });

            }
        });
    }
}
