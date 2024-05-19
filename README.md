# Rapport
Det första steget var, likt tidigare, att skapa en fork av projektet på LenaSYS Github. Först ut var att uppdatera layouten i huvudaktiviteten. Jag valde att lägga till 5 st _textView_'s, 3 st textfält, 2 st knappar och några linjer för att dela upp layouten. Dessa ändringar gjordes i _AndroidManifest.xml_. Nästa steg var att sedan skaffa referenser till nödvändiga element för att sedan kunna manipulera dem i kod.

**MainActivity.java**
```
    /* =============== VARIABLES =============== */
        private Button writeBtn, readBtn;
        private EditText editName, editType, editAcro;
        private TextView dbText;
        private SQLiteDatabase db;
    /* ========================================= */
    ...

    // Get references to buttons and text-fields
        writeBtn = findViewById(R.id.writeBtn);
        readBtn = findViewById(R.id.readBtn);

        editName = findViewById(R.id.topEditText);
        editType = findViewById(R.id.middleEditText);
        editAcro = findViewById(R.id.lowerEditText);
        dbText = findViewById(R.id.dbText);
```

För att kunna skapa en databas, skapades och implementerades två stycken klasser, _DatabaseTables.java_ och _DatabaseHelper.java_.
I _DatabaseTables.java_ definierades hur databasen skulle se. I denna uppgift fick det representera olika länder.

**DatabaseTables.java**
```
    static class Country  {
        static final String TABLE_NAME = "country";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_TYPE = "NAME";
        static final String COLUMN_NAME_ACRONYM = "acronym";
    }
```

Samt två fördefinierade textsträngar för att både skapa och ta bort en tabell. 

**DatabaseTables.java**
```
    static final String SQL_CREATE_TABLE_COUNTRY =
            // Table --> "CREATE TABLE country(id INTEGER PRIMARY KEY, name TEXT, type TEXT, acronym TEXT)"
            "CREATE TABLE " + Country.TABLE_NAME + " (" +
                    Country.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    Country.COLUMN_NAME_TYPE + " TEXT," +
                    Country.COLUMN_NAME_ACRONYM + " TEXT)";

    static final String SQL_DELETE_TABLE_COUNTRY =
            // Table --> "DROP TABLE IF EXISTS country"
            "DROP TABLE IF EXISTS " + Country.TABLE_NAME;
```

Den andra klassen, _DatabaseHelper.java_, användes för att göra interaktionen mellan databasen och applikationen enklare. Nästa steg var att initializera hjälpklasserna och implementera funktioner för att läsa från och skriva till databasen. 

**MainActivity.java**
```
    ...
    // Initialize
    dbHelper = new DatabaseHelper(this);
    db = dbHelper.getWritableDatabase();
    ...
```

Att skriva till databasen gjorden genom att implementera _onClick()_. För att skriva till en databas skapades en instance av _ContentValues_ vilket innehöll de nya värdena på landet som skulle skrivas till databasen. Värden som lades in kom ifrån textfälten på startskärmen. Efter att datan har skrivits till databasen, rensades textfälten för att visuellt indikera att datan har skrivits till databasen. 

**MainActivity.java**
```
    ...
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
    ...
```

Att läsa/hämta datan från databasen gjordes med hjälp av en _Cursor_ vilket går igenom databasen rad för rad, fullständiga rader. För varje rad hämtades nödvändig information och en ny textsträng skapades utifrån inmatad data och lagrades i en lista. Samma lista användes därefter för att skapa ett textblock som användes för att uppdatera en _textView_ i nedre delen av skärmen.  
**MainActivity.java**
```
    ...
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
    ...
```


Skärmklipp på skärmen för _MainActivity_:
![Screenshot_20240519_210955](https://github.com/a20gabpa/mobileapp-programming-persistence/assets/102604680/fff33840-ba18-4f9f-830c-6e8e83d1ea78)
Skärmklipp med information skriven i varje textfält, _MainActivity_: 
![Screenshot_20240519_211028](https://github.com/a20gabpa/mobileapp-programming-persistence/assets/102604680/90c475a5-4798-4b03-a6ee-6e4d1683c97b)
Skärmklipp när användaren har tryckt på att läsa från databasen, _MainActivity_:
![Screenshot_20240519_211040](https://github.com/a20gabpa/mobileapp-programming-persistence/assets/102604680/4ae7e03e-76e7-4e32-b4a2-049fad12206a)
Skärmklipp efter att användaren har läst från databasen, men har matat in några rader tidigare, _MainActivity_:
![Screenshot_20240519_211112](https://github.com/a20gabpa/mobileapp-programming-persistence/assets/102604680/8eee3dcc-52f8-4349-a646-f64507da3af6)
