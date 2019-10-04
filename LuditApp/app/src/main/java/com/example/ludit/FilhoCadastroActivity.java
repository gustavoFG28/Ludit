package com.example.ludit;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
<<<<<<< HEAD
=======
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
>>>>>>> e7e9d57bdc2989a4b5c4420c9cf6dc0206cd783a
import android.widget.Toast;

import com.example.ludit.ui.filho.Filho;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
<<<<<<< HEAD
=======
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
>>>>>>> e7e9d57bdc2989a4b5c4420c9cf6dc0206cd783a
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilhoCadastroActivity extends AppCompatActivity {

    EditText edtNome,edtDeficiencia,edtTexto;
    DatePicker dataNascimento;
    Button btnCadastrarFilho, imgPerfilFilho;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho_cadastro);

        sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        final String email = sharedPreferences.getString("email", null);

        imgPerfilFilho = findViewById(R.id.imgPerfilFilho);
        edtNome = (EditText) findViewById(R.id.edNomeFilho);
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

        imgPerfilFilho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialog();
            }
        });
    }

    private void abrirDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(FilhoCadastroActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_lista_imagens, null);
        int img = R.drawable.xvbfxb;
        final List<Integer> array = new ArrayList<>();
        array.add(img);
        array.add(img);
        array.add(img);
        array.add(img);


        ListView lvDialogImagens = dialogView.findViewById(R.id.dialogListImagens);
        ListaImagensAdapter adapter = new ListaImagensAdapter(FilhoCadastroActivity.this, array);
        lvDialogImagens.setAdapter(adapter);


        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("Selecione uma imagem de perfil");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        lvDialogImagens.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    imgPerfilFilho.setBackground(ResourcesCompat.getDrawable(getResources(), array.get(position), null));
                    dialog.dismiss();
            }});
    }

    public  void  cadastarFilho(String email) {
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(FilhoCadastroActivity.this);
        dlgAlert.setTitle("LUDIT - Erro no Cadastro do Filho");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, dataNascimento.getYear());
        c.set(Calendar.MONTH, dataNascimento.getMonth());
        c.set(Calendar.DAY_OF_MONTH, dataNascimento.getDayOfMonth());
        String date = dateFormat.format(c.getTime());

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
}
