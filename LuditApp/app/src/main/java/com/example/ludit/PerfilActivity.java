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
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

        construirLista();

        btnAlteraEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalEmail();
            }
        });

        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirConta();
            }
        });

        btnAlteraNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalNome();
            }
        });

        btnAlteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalSenha();
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

                if(edtSenhaConfirmacao.getText().toString() == null || edtNovoEmail.getText().toString() == null)
                {
                    dlgAlert.setMessage("Preencha todos os Campos");
                    dlgAlert.create().show();
                }else {
                    final UserService service = RetrofitConfig.getClient().create(UserService.class);

                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                Call<Void> alt = service.alteraEmail(email, edtNovoEmail.getText().toString());

                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (!response.isSuccessful()) {

                                            dlgAlert.setMessage("Erro mudar nome, verifique os dados");
                                            dlgAlert.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("email", edtNovoEmail.getText().toString());
                                        editor.commit();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Usuario>> call, Throwable t) {
                            dlgAlert.setMessage("Senha Inválida");
                            edtNovoEmail.setText("");
                            edtSenhaConfirmacao.setText("");
                            dlgAlert.create().show();
                        }
                    });
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("Alteração de Email da Conta");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public  void modalNome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_update, null);
        final EditText edtNovoNome, edtSenhaConfirmacao;
        edtNovoNome = (EditText) dialogView.findViewById(R.id.edtAlterar);
        edtNovoNome.setHint("Novo Nome");
        edtSenhaConfirmacao = (EditText) dialogView.findViewById(R.id.edtSenha);

        builder.setView(dialogView);
        builder.setNegativeButton("CANCELAR", null);

        builder.setPositiveButton("ALTERAR NOME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PerfilActivity.this);
                dlgAlert.setTitle("LUDIT - Erro ao Alterar Nome");
                dlgAlert.setPositiveButton("OK", null);

                if (edtSenhaConfirmacao.getText().toString() == null || edtNovoNome.getText().toString() == null) {
                    dlgAlert.setMessage("Preencha todos os Campos");
                    dlgAlert.create().show();
                } else {
                    final  UserService service = RetrofitConfig.getClient().create(UserService.class);

                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString().trim());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                String nome = edtNovoNome.getText().toString();
                                Call<Void> alt = service.alteraNome(email, nome);
                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (!response.isSuccessful()) {

                                            dlgAlert.setMessage("Erro ao mudar nome, verifique os dados");
                                            dlgAlert.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("nome", edtNovoNome.getText().toString());
                                        editor.commit();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Usuario>> call, Throwable t) {
                            dlgAlert.setMessage(t.getMessage());
                            //dlgAlert.setMessage("Senha Inválida");
                            edtNovoNome.setText("");
                            edtSenhaConfirmacao.setText("");
                            dlgAlert.create().show();
                        }
                    });
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("Alteração de Nome da Conta");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public  void modalSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_update, null);
        final EditText edtSenha, edtSenhaConfirmacao;
        edtSenha = (EditText)dialogView.findViewById(R.id.edtAlterar);
        edtSenha.setHint("Nova Senha");
        edtSenhaConfirmacao = (EditText)dialogView.findViewById(R.id.edtSenha);

        builder.setView(dialogView);
        builder.setNegativeButton("CANCELAR", null);

        builder.setPositiveButton("ALTERAR SENHA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PerfilActivity.this);
                dlgAlert.setTitle("LUDIT - Erro ao Alterar Senha");
                dlgAlert.setPositiveButton("OK", null);

                if(edtSenhaConfirmacao.getText().toString() == null || edtSenha.getText().toString() == null)
                {
                    dlgAlert.setMessage("Preencha todos os Campos");
                    dlgAlert.create().show();
                }else
                {
                    final  UserService service = RetrofitConfig.getClient().create(UserService.class);

                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                Call<Void> alt = service.alteraSenha(email, edtSenha.getText().toString());
                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(!response.isSuccessful()){

                                            dlgAlert.setMessage("Erro ao mudar Senha, verifique os dados");
                                            dlgAlert.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("email", edtSenha.getText().toString());
                                        editor.commit();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Usuario>> call, Throwable t) {
                            dlgAlert.setMessage("Senha Antiga Inválida");
                            edtSenha.setText("");
                            edtSenhaConfirmacao.setText("");
                            dlgAlert.create().show();
                        }
                    });
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("Alteração de Senha da Conta");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public  void excluirConta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_update, null);
        final EditText edtEmail, edtSenhaConfirmacao;
        edtSenhaConfirmacao = (EditText)dialogView.findViewById(R.id.edtSenha);

        builder.setView(dialogView);
        builder.setNegativeButton("CANCELAR", null);

        builder.setPositiveButton("EXCLUIR CONTA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PerfilActivity.this);
                dlgAlert.setTitle("LUDIT - Erro ao Excluir Conta");
                dlgAlert.setPositiveButton("OK", null);

                if(edtSenhaConfirmacao.getText().toString() == null)
                {
                    dlgAlert.setMessage("Preencha todos os Campos");
                    dlgAlert.create().show();
                }else
                {
                    final  UserService service = RetrofitConfig.getClient().create(UserService.class);

                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful()) {
                                Call<Void> exc = service.excluirConta(email);
                                exc.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(!response.isSuccessful()){

                                            dlgAlert.setMessage("Erro ao mudar Excluir, verifique os dados");
                                            dlgAlert.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("email", null);
                                        editor.putString("nome", null);
                                        editor.commit();

                                        Intent i = new Intent(PerfilActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Usuario>> call, Throwable t) {
                            dlgAlert.setMessage("Senha Inválida");
                            edtSenhaConfirmacao.setText("");
                            dlgAlert.create().show();
                        }
                    });
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("Exclusão de Conta");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    public  void  construirLista() {
        UserService service = RetrofitConfig.getClient().create(UserService.class);

        Call<List<Filho>> call = service.buscarFilhos(email);

        call.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                if(!response.isSuccessful()){
                        return;
                }

                FilhoAdapter lista = new FilhoAdapter(PerfilActivity.this, response.body());
                lista_filhos.setAdapter(lista);
            }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            });
    }
}
