package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    /* =============== VARIABLES =============== */
    private Button writeBtn, readBtn;
    private EditText editName, editType, editAcro;
    /* ========================================= */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to buttons and text-fields
        writeBtn = findViewById(R.id.writeBtn);
        readBtn = findViewById(R.id.readBtn);

        editName = findViewById(R.id.topEditText);
        editType = findViewById(R.id.middleEditText);
        editAcro = findViewById(R.id.lowerEditText);
    }
}
