package com.example.ludit.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ludit.R;

public class BluetoothActivity extends AppCompatActivity {

    ConnectionThread connect;
    static Context context;
    static String mensagem;
    static TextView tvMensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        tvMensagem = findViewById(R.id.tvMensagem);
        context = getApplicationContext();
        mensagem = "";
         /* Teste rápido. O hardware Bluetooth do dispositivo Android
            está funcionando ou está bugado de forma misteriosa?
            Será que existe, pelo menos? Provavelmente existe.
         */
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(this,"Que pena! Hardware Bluetooth não está funcionando", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ótimo! Hardware Bluetooth está funcionando", Toast.LENGTH_SHORT).show();


        /* A chamada do seguinte método liga o Bluetooth no dispositivo Android
            sem pedido de autorização do usuário. É altamente não recomendado no
            Android Developers, mas, para simplificar este app, que é um demo,
            faremos isso. Na prática, em um app que vai ser usado por outras
            pessoas, não faça isso.
         */
            if(!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 3);
            }

        /* Definição da thread de conexão como cliente.
            Aqui, você deve incluir o endereço MAC do seu módulo Bluetooth.
            O app iniciará e vai automaticamente buscar por esse endereço.
            Caso não encontre, dirá que houve um erro de conexão.
         */
            connect = new ConnectionThread("98:D3:31:FD:40:2A");
            connect.start();

            /* Um descanso rápido, para evitar bugs esquisitos.
             */
            try {
                Thread.sleep(1000);
            } catch (Exception E) {
                E.printStackTrace();
            }
        }

    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            /* Esse método é invocado na Activity principal
                sempre que a thread de conexão Bluetooth recebe
                uma mensagem.
             */
            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            /* Aqui ocorre a decisão de ação, baseada na string
                recebida. Caso a string corresponda à uma das
                mensagens de status de conexão (iniciadas com --),
                atualizamos o status da conexão conforme o código.
             */
            if(dataString.equals("---N"))
                Toast.makeText(context,"Ocorreu um erro durante a conexão", Toast.LENGTH_SHORT).show();
            else if(dataString.equals("---S"))
                Toast.makeText(context,"Conectado", Toast.LENGTH_SHORT).show();
            else {

                /* Se a mensagem não for um código de status,
                    então ela deve ser tratada pelo aplicativo
                    como uma mensagem vinda diretamente do outro
                    lado da conexão. Nesse caso, simplesmente
                    atualizamos o valor contido no TextView do
                    contador.
                 */
                tvMensagem.setText(dataString);
            }
        }
    };
}
