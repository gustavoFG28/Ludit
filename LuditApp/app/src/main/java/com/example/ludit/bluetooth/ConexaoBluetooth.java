package com.example.ludit.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ConexaoBluetooth extends AsyncTask<String, Void, String> {
    protected String connectSuccess = "OK";

    BluetoothSocket btSocket;
    BluetoothAdapter adapter;
    BluetoothDevice device;

    UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    protected boolean isBtConnected;


    @Override
    protected String doInBackground(String... devices) {
        try {
            if (btSocket == null || !isBtConnected) {
                adapter = BluetoothAdapter.getDefaultAdapter();

                String address = devices[0];

                device = adapter.getRemoteDevice(address);

                btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);

                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

                btSocket.connect();
            }
        } catch (IOException e) {
            connectSuccess = "NOK";
        }

        return connectSuccess;
    }

    @Override
    protected void onPostExecute(String resultado) {
        if (resultado == "NOK") try {
            throw new Exception("Bluetooth - Erro");
        } catch (Exception e) {
        }

        isBtConnected = true;
    }

    protected void Desconectar() throws Exception {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                throw new Exception("ERRO AO DESCONECTAR");
            }
        }
    }

    public void enviaDados(String msg){
        try {
            btSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recebeDados(){
        try {
            InputStream in = btSocket.getInputStream();
            byte[] bytes = new byte[1024];
            int length;
            length = in.read(bytes);

            String msg = new String(bytes, 0, length);

            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
  /* BluetoothAdapter myBluetooth = null;
    BluetoothDevice dispositivo = null;
    BluetoothSocket btSocket = null;

    //Atribui um identificador exclusivo. Universally Unique IDentifier
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        address = params.getString("endereco");

    }


    private void Connect() {
        ConnectBT bt = new ConnectBT();
        System.out.println("Endereço no LedControl:"+ address);
        bt.execute(address);
    }
*/

