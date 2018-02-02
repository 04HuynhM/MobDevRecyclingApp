package com.example.martin.recyclingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MartinsMainActivity extends AppCompatActivity {

    Button scanBarcode;
    TextView barcodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.martins_activity_main);
        scanBarcode = findViewById(R.id.scanBarcodeButton);
        barcodeText = findViewById(R.id.barcodeText);
    }

    public void scanBarcode(View v){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==0){
            if(resultCode== CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeText.setText("Barcode Value: " + barcode.displayValue);
                }
                else{
                    barcodeText.setText("No Barcode Found");
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
