package com.example.ludit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ludit.ui.filho.Filho;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {


    Button btnAlteraSenha, btnAlteraNome,btnAlteraEmail, btnExcluirConta;
    TextView tvEmail, tvNome;
    ListView lista_filhos;
    SharedPreferences sharedPreferences;

    String email, nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btnAlteraEmail =(Button)findViewById(R.id.btnAlteraEmail);
        btnAlteraNome =(Button)findViewById(R.id.btnAlteraNome);
        btnAlteraSenha =(Button)findViewById(R.id.btnAlteraSenha);
        btnExcluirConta = (Button) findViewById(R.id.btnDeletar);

        tvEmail = (TextView) findViewById(R.id.tvEmailUser);
        tvNome = (TextView) findViewById(R.id.tvNomeUser);
        lista_filhos = (ListView) findViewById(R.id.lista_filhos);

        sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = sharedPreferences.getString("email", null);
        nome = sharedPreferences.getString("nome", null);

        tvEmail.setText(email);
        tvNome.setText(nome);

        construirLista(email);

        btnAlteraEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalEmail();
            }
        });

        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnAlteraNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnAlteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        lista_filhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public  void modalEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_update, null);
        final EditText edtNovoEmail, edtSenhaConfirmacao;
        edtNovoEmail = (EditText)dialogView.findViewById(R.id.edtAlterar);
        edtNovoEmail.setHint("Novo Email");
        edtSenhaConfirmacao = (EditText)dialogView.findViewById(R.id.edtSenha);

        builder.setView(dialogView);
        builder.setNegativeButton("CANCELAR", null);

        builder.setPositiveButton("ALTERAR EMAIL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PerfilActivity.this);
                dlgAlert.setTitle("LUDIT - Erro ao Alterar Email");
                dlgAlert.setPositiveButton("OK", null);

                UserService service = RetrofitConfig.getClient().create(UserService.class);

                if(edtSenhaConfirmacao.getText().toString() == null || edtNovoEmail.getText().toString() == null)
                {
                    dlgAlert.setMessage("Preencha todos os Campos");
                    dlgAlert.create().show();
                }else
                {
                    Call<Void> call = service.alteraEmail(email, edtNovoEmail.getText().toString());

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

                            Intent i = new Intent(FilhoCadastroActivity.this, FilhoActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<List<Filho>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    });
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("Selecione uma imagem de perfil");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }



    public  void  construirLista(String email) {
        UserService service = RetrofitConfig.getClient().create(UserService.class);

        Call<List<Filho>> call = service.buscarFilhos(email);

        call.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                if(!response.isSuccessful()){
                        return;
                }


            }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            });
    }

}
