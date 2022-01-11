package com.example.week15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);

        String path = Environment.getExternalStorageDirectory().getPath();
        File root = new File(path);

        File[] filesAndFolders = root.listFiles();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FileAdapter fileAdapter = new FileAdapter(this, filesAndFolders);
        Log.v("TAG", "num" + fileAdapter.getItemCount());
        recyclerView.setAdapter(fileAdapter);

        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "permission granted");
            } else {
                Log.v("TAG", "permission denied");
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1234);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1234) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "permission granted");
            } else {
                Log.v("TAG", "permission denied");
            }
        }
    }
}