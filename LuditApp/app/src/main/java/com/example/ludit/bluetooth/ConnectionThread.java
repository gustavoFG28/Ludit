package com.example.ludit.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.ludit.atividades.JogosActivity;
import com.example.ludit.games.FormasActivity;
import com.example.ludit.games.GeniusActivity;
import com.example.ludit.games.MatematicaActivity;
import com.example.ludit.games.PinguimActivity;
import com.example.ludit.games.ReciclagemActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class ConnectionThread extends Thread{

    Handler handler;
    BluetoothSocket btSocket = null;
    BluetoothServerSocket btServerSocket = null;
    InputStream input = null;
    OutputStream output = null;
    String btDevAddress = null;
    String myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    boolean server;
    boolean running = false;
    boolean isConnected = false;

    /*  Este construtor prepara o dispositivo para atuar como servidor.
     */
    public ConnectionThread() {

        this.server = true;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    /*  Este construtor prepara o dispositivo para atuar como cliente.
        Tem como argumento uma string contendo o endereço MAC do dispositivo
    Bluetooth para o qual deve ser solicitada uma conexão.
     */
    public ConnectionThread(String btDevAddress) {
        this.running = true;
        this.handler = null;
        this.server = false;
        this.btDevAddress = btDevAddress;
    }

    /*  O método run() contem as instruções que serão efetivamente realizadas
    em uma nova thread.
     */
    public void run() {

        /*  Anuncia que a thread está sendo executada.
            Pega uma referência para o adaptador Bluetooth padrão.
         */
        this.running = true;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        /*  Determina que ações executar dependendo se a thread está configurada
        para atuar como servidor ou cliente.
         */
        if(this.server) {

            /*  Servidor.
             */
            try {

                /*  Cria um socket de servidor Bluetooth.
                    O socket servidor será usado apenas para iniciar a conexão.
                    Permanece em estado de espera até que algum cliente
                estabeleça uma conexão.
                 */
                btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord("Super Counter", UUID.fromString(myUUID));
                btSocket = btServerSocket.accept();

                /*  Se a conexão foi estabelecida corretamente, o socket
                servidor pode ser liberado.
                 */
                if(btSocket != null) {
                    btServerSocket.close();
                }

            } catch (IOException e) {

                /*  Caso ocorra alguma exceção, exibe o stack trace para debug.
                    Envia um código para a Activity principal, informando que
                    a conexão falhou.
                 */
                e.printStackTrace();

                toActivity("Erro".getBytes());
                //toMainActivity("---N".getBytes());
            }


        } else {

            /*  Cliente.
             */
            try {

                /*  Obtem uma representação do dispositivo Bluetooth com
                endereço btDevAddress.
                    Cria um socket Bluetooth.
                 */
                BluetoothDevice btDevice = btAdapter.getRemoteDevice(btDevAddress);
                btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));

                /*  Envia ao sistema um comando para cancelar qualquer processo
                de descoberta em execução.
                 */
                btAdapter.cancelDiscovery();

                /*  Solicita uma conexão ao dispositivo cujo endereço é
                btDevAddress.
                    Permanece em estado de espera até que a conexão seja
                estabelecida.
                 */
                if (btSocket != null) {
                    btSocket.connect();
                }

            } catch (IOException e) {

                /*  Caso ocorra alguma exceção, exibe o stack trace para debug.
                    Envia um código para a Activity principal, informando que
                    a conexão falhou.
                 */
                e.printStackTrace();

                //toMainActivity("---N".getBytes());
            }

        }

        /*  Pronto, estamos conectados! Agora, só precisamos gerenciar a conexão.
            ...
         */

        if(btSocket != null) {

            /*  Envia um código para a Activity principal informando que a
            a conexão ocorreu com sucesso.
             */
            this.isConnected = true;
            //toMainActivity("---S".getBytes());

            try {

                /*  Obtem referências para os fluxos de entrada e saída do
                socket Bluetooth.
                 */
                input = btSocket.getInputStream();
                output = btSocket.getOutputStream();

                /*  Permanece em estado de espera até que uma mensagem seja
                recebida.
                    Armazena a mensagem recebida no buffer.
                    Envia a mensagem recebida para a Activity principal, do
                primeiro ao último byte lido.
                    Esta thread permanecerá em estado de escuta até que
                a variável running assuma o valor false.
                 */
                while(running) {

                    /*  Cria um byte array para armazenar temporariamente uma
                    mensagem recebida.
                        O inteiro bytes representará o número de bytes lidos na
                    última transmissão recebida.
                        O inteiro bytesRead representa o número total de bytes
                    lidos antes de uma quebra de linha. A quebra de linha
                    representa o fim da mensagem.
                     */
                    byte[] buffer = new byte[1024];
                    int bytes;
                    int bytesRead = -1;

                    /*  Lê os bytes recebidos e os armazena no buffer até que
                    uma quebra de linha seja identificada. Nesse ponto, assumimos
                    que a mensagem foi transmitida por completo.
                     */
                    do {
                        bytes = input.read(buffer, bytesRead+1, 1);
                        bytesRead+=bytes;
                    } while(buffer[bytesRead] != '\n');

                    /*  A mensagem recebida é enviada para a Activity principal.
                     */
                    toActivity(Arrays.copyOfRange(buffer, 0, bytesRead-1));

                }

            } catch (IOException e) {

                /*  Caso ocorra alguma exceção, exibe o stack trace para debug.
                    Envia um código para a Activity principal, informando que
                    a conexão falhou.
                 */
                e.printStackTrace();
                //toMainActivity("---N".getBytes());
                this.isConnected = false;
            }
        }


    }

    /*  Utiliza um handler para enviar um byte array à Activity principal.
        O byte array é encapsulado em um Bundle e posteriormente em uma Message
    antes de ser enviado.*/
    private void toActivity(byte[] data) {

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", data);
        message.setData(bundle);

        if(handler != null) {
            handler.sendMessage(message);
        }

    }

    /*  Método utilizado pela Activity principal para transmitir uma mensagem ao
     outro lado da conexão.
        A mensagem deve ser representada por um byte array.
     */
    public void write(byte[] data) {

        if(output != null) {
            try {

                /*  Transmite a mensagem.
                 */
                output.write(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            /*  Envia à Activity principal um código de erro durante a conexão.
             */
            toActivity("---N".getBytes());
        }
    }

    /*  Método utilizado pela Activity principal para encerrar a conexão
     */
    public void cancel() {

        try {

            running = false;
            this.isConnected = false;
            if(btServerSocket != null)
                btServerSocket.close();
            btSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.running = false;
        this.isConnected = false;
        this.interrupt();
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}