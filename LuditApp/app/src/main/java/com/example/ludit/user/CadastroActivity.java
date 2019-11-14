package com.example.ludit.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;
import com.example.ludit.webservice.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtEmail, edtSenha, edtConSenha;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        edtNome = (EditText) findViewById(R.id.edNome);
        edtEmail = (EditText) findViewById(R.id.edEmail);
        edtConSenha = (EditText) findViewById(R.id.edConfimarSenha);
        edtSenha = (EditText) findViewById(R.id.edSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });

        ((Button)findViewById(R.id.btnSair)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void cadastrarUsuario() {
        final  AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("LUDIT - Erro no Cadastro");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        if(edtConSenha.getText().toString() == null || edtSenha.getText().toString() == null || edtNome.getText().toString() == null || edtEmail.getText().toString() == null)
        {
            dlgAlert.setMessage("Preencha todos os Campos");
            dlgAlert.create().show();
        }
        if(!edtSenha.getText().toString().equals(edtConSenha.getText().toString()))
        {
            dlgAlert.setMessage("Senhas Diferentes");
            edtSenha.setText("");
            edtConSenha.setText("");
            dlgAlert.create().show();
        } else {
            UserService service = RetrofitConfig.getClient().create(UserService.class);

            Usuario user = new Usuario(edtEmail.getText().toString(), edtNome.getText().toString(), edtSenha.getText().toString());

            Call<List<Usuario>> call = service.inserirUsuario(user);

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>>response) {
                    if(!response.isSuccessful()){
                        dlgAlert.setMessage("Erro ao Cadastrar, verifique os dados");
                        edtSenha.setText("");
                        edtConSenha.setText("");
                        edtEmail.setText("");
                        edtNome.setText("");
                        dlgAlert.create().show();
                        return;
                    }

                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                    editor.putString("email", response.body().get(0).getEmail());

                    editor.putString("nome", response.body().get(0).getNome());
                    editor.commit();

                    Intent i = new Intent(CadastroActivity.this, PerfilActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            });
        }
    }

}
