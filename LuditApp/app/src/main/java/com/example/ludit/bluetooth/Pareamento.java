package com.example.ludit.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Set;

public class Pareamento {
    private BluetoothAdapter myBluetooth = null;

    public Pareamento() throws Exception {
        this.myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (this.myBluetooth == null)
            throw new Exception("Bluetooth ausente");
    }

    public boolean onBluetooth() {
        if (!myBluetooth.isEnabled())
            return false;

        return true;
    }

    public ArrayList listaDispositivos() throws Exception{
        Set<BluetoothDevice> pairedDevices;
        pairedDevices = myBluetooth.getBondedDevices();

        ArrayList list = new ArrayList();

        if (pairedDevices.size() <= 0)
            throw  new Exception("NENHUM APARELHO ENCONTRADO");

        for (BluetoothDevice bt : pairedDevices)
            list.add(bt.getName() + bt.getAddress());

        return  list;
    }

    public String parear(String s) {
        String adress = s.substring(s.length() -17);
        return  adress;
    }
}
