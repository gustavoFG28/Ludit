package com.example.ludit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.ludit.adapters.FilhoAdapter;
import com.example.ludit.ui.filho.Filho;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfiguracoesActivity extends AppCompatActivity {


    Button btnAlteraSenha, btnAlteraNome,btnAlteraEmail, btnExcluirConta;
    EditText edtAlterar, edtSenhaConfirmacao;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    UserService service;
    String email, nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        btnAlteraEmail =(Button)findViewById(R.id.btnAlteraEmail);
        btnAlteraNome =(Button)findViewById(R.id.btnAlteraNome);
        btnAlteraSenha =(Button)findViewById(R.id.btnAlteraSenha);
        btnExcluirConta = (Button) findViewById(R.id.btnDeletar);


        sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = sharedPreferences.getString("email", null);
        nome = sharedPreferences.getString("nome", null);

        construirDialog();

        service = RetrofitConfig.getClient().create(UserService.class);

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
    }

    public void construirDialog() {
        builder =  new AlertDialog.Builder(ConfiguracoesActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_update, null);
        builder.setView(dialogView);
        edtAlterar = (EditText)dialogView.findViewById(R.id.edtAlterar);
        edtSenhaConfirmacao = (EditText)dialogView.findViewById(R.id.edtSenha);
        builder.setNegativeButton("CANCELAR", null);
    }

    public  void modalEmail() {
        edtAlterar.setHint("Novo Email");

        builder.setPositiveButton("ALTERAR EMAIL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle("LUDIT - Erro ao Alterar Email");
                builder.setPositiveButton("OK", null);

                if(edtSenhaConfirmacao.getText().toString() == null || edtAlterar.getText().toString() == null) {
                    builder.setMessage("Preencha todos os Campos");
                    builder.create().show();
                } else {
                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                final String novoEmail =  edtAlterar.getText().toString();
                                Call<Void> alt = service.alteraEmail(email,novoEmail);

                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (!response.isSuccessful()) {

                                            builder.setMessage("Erro mudar nome, verifique os dados");
                                            builder.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("email",novoEmail);
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
                            builder.setMessage("Senha Inválida");
                            edtAlterar.setText("");
                            edtSenhaConfirmacao.setText("");
                            builder.create().show();
                        }
                    });
                }
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Alteração de Email da Conta");
        dialog.show();
    }
    public  void modalNome() {
        edtAlterar.setHint("Novo Nome");

        builder.setPositiveButton("ALTERAR NOME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle("LUDIT - Erro ao Alterar Nome");
                builder.setPositiveButton("OK", null);

                if (edtSenhaConfirmacao.getText().toString() == null || edtAlterar.getText().toString() == null) {
                    builder.setMessage("Preencha todos os Campos");
                    builder.create().show();
                } else {
                    Call<List<Usuario>> user = service.verificaUser(email, edtSenhaConfirmacao.getText().toString().trim());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                final String nome = edtAlterar.getText().toString();
                                Call<Void> alt = service.alteraNome(email, nome);
                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (!response.isSuccessful()) {
                                            builder.setMessage("Erro ao mudar nome, verifique os dados");
                                            builder.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("nome", nome);
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
                            builder.setMessage("Senha Inválida");
                            edtAlterar.setText("");
                            edtSenhaConfirmacao.setText("");
                            builder.create().show();
                        }
                    });
                }
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Alteração de Nome da Conta");
        dialog.show();
    }
    public  void modalSenha() {
        edtAlterar.setHint("Nova Senha");

        builder.setPositiveButton("ALTERAR SENHA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle("LUDIT - Erro ao Alterar Senha");
                builder.setPositiveButton("OK", null);

                if(edtSenhaConfirmacao.getText().toString() == null || edtAlterar.getText().toString() == null)
                {
                    builder.setMessage("Preencha todos os Campos");
                    builder.create().show();
                }else
                {
                    Call<List<Usuario>> user = service.verificaUser(email, edtAlterar.getText().toString());

                    user.enqueue(new Callback<List<Usuario>>() {
                        @Override
                        public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                            if(response.isSuccessful())
                            {
                                Call<Void> alt = service.alteraSenha(email, edtAlterar.getText().toString());
                                alt.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(!response.isSuccessful()){
                                            builder.setMessage("Erro ao mudar Senha, verifique os dados");
                                            builder.create().show();
                                            return;
                                        }
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
                            builder.setMessage("Senha Antiga Inválida");
                            edtAlterar.setText("");
                            edtSenhaConfirmacao.setText("");
                            builder.create().show();
                        }
                    });
                }
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Alteração de Senha da Conta");
        dialog.show();
    }
    public  void excluirConta() {
        edtAlterar.setVisibility(View.INVISIBLE);

        builder.setPositiveButton("EXCLUIR CONTA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle("LUDIT - Erro ao Excluir Conta");
                builder.setPositiveButton("OK", null);

                if(edtSenhaConfirmacao.getText().toString() == null) {
                    builder.setMessage("Preencha todos os Campos");
                    builder.create().show();
                }else
                {
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
                                            builder.setMessage("Erro ao Excluir, verifique os dados");
                                            builder.create().show();
                                            return;
                                        }

                                        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                                        editor.putString("email", null);
                                        editor.putString("nome", null);
                                        editor.commit();

                                        Intent i = new Intent(ConfiguracoesActivity.this, MenuActivity.class);
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
                            builder.setMessage("Senha Inválida");
                            edtSenhaConfirmacao.setText("");
                            builder.create().show();
                        }
                    });
                }
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Exclusão de Conta");
        dialog.show();
    }


}
