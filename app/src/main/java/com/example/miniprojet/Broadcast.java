package com.example.miniprojet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Broadcast extends AppCompatActivity {

    CheckBox bluetoothCheckbox, wifiCheckbox, chargerCheckbox;
    private boolean previousBluetoothState = false;
    private boolean previousWifiState = false;
    private boolean previousChargingState = false;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        bluetoothCheckbox = findViewById(R.id.bluetooth);
        wifiCheckbox = findViewById(R.id.wifi);
        chargerCheckbox = findViewById(R.id.charger);

        dbHelper = new DatabaseHelper(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, filter);

        updateBluetoothCheckbox();
        updateWifiCheckbox();
        updateChargerCheckbox();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case BluetoothAdapter.ACTION_STATE_CHANGED:
                        boolean currentBluetoothState = isBluetoothEnabled();
                        if (currentBluetoothState != previousBluetoothState) {
                            updateBluetoothCheckbox();
                            if (currentBluetoothState) {
                                Log.d("BroadcastReceiver", "Bluetooth enabled, deleting last item.");

                                // Delete the last user from the database
                                dbHelper.deleteLastItem();
                                Toast.makeText(context, "Bluetooth enabled. Last user deleted.", Toast.LENGTH_SHORT).show();
                            }
                            previousBluetoothState = currentBluetoothState;
                        }
                        break;
                    case WifiManager.WIFI_STATE_CHANGED_ACTION:
                        boolean currentWifiState = isWifiEnabled();
                        if (currentWifiState != previousWifiState) {
                            updateWifiCheckbox();
                            Toast.makeText(context, "WiFi state changed", Toast.LENGTH_SHORT).show();
                            previousWifiState = currentWifiState;
                        }
                        break;
                    case Intent.ACTION_BATTERY_CHANGED:
                        boolean currentChargingState = isCharging(intent);
                        if (currentChargingState != previousChargingState) {
                            updateChargerCheckbox();
                            if (currentChargingState) {
                                Toast.makeText(context, "Battery is charging", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Battery is not charging", Toast.LENGTH_SHORT).show();
                            }
                            previousChargingState = currentChargingState;
                        }
                        break;
                }
            }
        }
    };


    private void updateBluetoothCheckbox() {
        boolean isBluetoothEnabled = isBluetoothEnabled();
        bluetoothCheckbox.setChecked(isBluetoothEnabled);
        previousBluetoothState = isBluetoothEnabled;
    }

    private boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    private void updateWifiCheckbox() {
        boolean isWifiEnabled = isWifiEnabled();
        wifiCheckbox.setChecked(isWifiEnabled);
        previousWifiState = isWifiEnabled;
    }

    private boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    private void updateChargerCheckbox() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        boolean isCharging = isCharging(batteryStatus);
        chargerCheckbox.setChecked(isCharging);
        previousChargingState = isCharging;
    }

    private boolean isCharging(Intent batteryStatus) {
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void goback(View w) {
        Intent p = new Intent(this, MainActivity.class);
        startActivity(p);
    }
}
