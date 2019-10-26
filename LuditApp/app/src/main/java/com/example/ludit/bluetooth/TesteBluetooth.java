package com.example.ludit.bluetooth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ludit.R;

import java.util.ArrayList;

public class TesteBluetooth extends AppCompatActivity {
    Button btn;
    ConexaoBluetooth conexaoBluetooth;
    Pareamento pareamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_bluetooth);
        btn = (Button) findViewById(R.id.btnTeste);

        conexaoBluetooth = new ConexaoBluetooth();

        try {
            buscarAdress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        iniciarThread();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexaoBluetooth.enviaDados("L");
            }
        });
    }

    public  void  iniciarThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String val = conexaoBluetooth.recebeDados();

                        if(val == "M"){
                            btn.setBackgroundColor(Color.parseColor("#795548"));
                        }
                        else if(val == "G") {
                            btn.setBackgroundColor(Color.parseColor("#4caf50 "));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public  void  buscarAdress() throws Exception{
        pareamento = new Pareamento();
        if(pareamento.onBluetooth())
        {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }


        ArrayList list = pareamento.listaDispositivos();

        final AlertDialog dialog;
        AlertDialog.Builder builder =  new AlertDialog.Builder(TesteBluetooth.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_lista_bluetooths, null);
        builder.setView(dialogView);
        dialog = builder.create();

        ListView lv = (ListView)dialogView.findViewById(R.id.lvBluetooth);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String info = ((TextView) v).getText().toString();
                System.out.println("info: " + info);

                String address = info.substring(info.length() - 17);
                System.out.println("address:" + address);

                conexaoBluetooth.execute(address);
            }
        });

        dialog.setTitle("SELECIONE A CONEXÂO");
        dialog.show();
    }
}
