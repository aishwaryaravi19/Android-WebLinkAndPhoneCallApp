package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText website, phone;
    View view;
    private static final int REQUEST_PHONE_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.logo);
        website = (EditText) findViewById(R.id.editText);
        phone = (EditText) findViewById(R.id.editText2);
    }

    public void closeApp(View view){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void go_to_website(View view) {
        String s = website.getText().toString();
        if (!s.startsWith("https://") && !s.startsWith("http://")){
            s = "http://" + s;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        startActivity(intent);
    }

    public void call_to_phone(View view) {
        this.view = view;
        //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:".concat(phone.toString())));
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:6692502248"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call_to_phone(view);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
