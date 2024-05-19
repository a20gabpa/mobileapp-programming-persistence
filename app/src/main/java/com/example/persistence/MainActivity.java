package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* =============== VARIABLES =============== */
    private Button writeBtn, readBtn;
    private EditText editName, editType, editAcro;
    private TextView dbText;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
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
        dbText = findViewById(R.id.dbText);

        // Initialize
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Write to database when clicked
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues myValues = new ContentValues();
                myValues.put(DatabaseTables.Country.COLUMN_NAME_ID, editName.getText().toString());
                myValues.put(DatabaseTables.Country.COLUMN_NAME_TYPE, editType.getText().toString());
                myValues.put(DatabaseTables.Country.COLUMN_NAME_ACRONYM, editAcro.getText().toString());

                // Insert to database
                db.insert(DatabaseTables.Country.TABLE_NAME, null, myValues);

                // Empty fields
                editName.setText("");
                editType.setText("");
                editAcro.setText("");
            }
        });

        // Read from database when clicked
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.query(DatabaseTables.Country.TABLE_NAME, null,
                        null, null, null, null, null);

                List<String> countries = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int countryID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Country.COLUMN_NAME_ID));
                    String countryName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Country.COLUMN_NAME_TYPE));
                    String countryAcro = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Country.COLUMN_NAME_ACRONYM));

                    countries.add("ID: "+ countryID + " Name: " + countryName + " Acronym: " + countryAcro);
                }
                cursor.close();

                String fancyText = "";
                for (String country : countries) {
                    fancyText = fancyText + country + "\n";
                }
                dbText.setText(fancyText);
            }
        });
    }
}
