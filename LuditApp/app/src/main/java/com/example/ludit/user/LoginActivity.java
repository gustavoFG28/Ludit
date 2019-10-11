package com.example.ludit.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserLogin;
import com.example.ludit.webservice.UserService;
import com.example.ludit.webservice.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
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
                logar();
            }
        });
    }

    public  void  logar()   {

        final  AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("LUDIT - Erro no Login");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        if(edtLogin.getText().toString() == null || edtSenha.getText().toString() == null)
        {
            dlgAlert.setMessage("Preencha todos os Campos");
            dlgAlert.create().show();
        } else {
            UserService service = RetrofitConfig.getClient().create(UserService.class);

            UserLogin user = new UserLogin(edtLogin.getText().toString(), edtSenha.getText().toString());

            Call<List<Usuario>> call = service.logar(user);

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    if(!response.isSuccessful()){
                        dlgAlert.setMessage("Erro ao Logar, verifique os dados");
                        edtSenha.setText("");
                        edtLogin.setText("");
                        dlgAlert.create().show();
                        return;
                    }

                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                    editor.putString("email", response.body().get(0).getEmail());
                    editor.putString("nome", response.body().get(0).getNome());

                    editor.commit();

                    Intent i = new Intent(LoginActivity.this, PerfilActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    dlgAlert.setMessage("Erro ao Logar, Email ou Senha não existentes");
                    edtSenha.setText("");
                    edtLogin.setText("");
                    dlgAlert.create().show();
                    return;
                }
            });
        }
    }
}

