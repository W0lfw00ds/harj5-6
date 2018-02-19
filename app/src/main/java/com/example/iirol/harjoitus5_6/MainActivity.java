package com.example.iirol.harjoitus5_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.iirol.harjoitus5_6.Class.Database.Database;
import com.example.iirol.harjoitus5_6.Class.Kirja;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText numero;
    private EditText nimi;
    private EditText painos;
    private EditText hankintapvm;
    private Button addnew;
    private Button deletefirst;
    private TableRow listaus;

    private void addnew_click(View view) {

        // Parsi syötetyt tiedot
        int parsedNumero = Integer.valueOf(this.numero.getText().toString());
        String parsedNimi = this.nimi.getText().toString();
        int parsedPainos = Integer.valueOf(this.painos.getText().toString());
        Date parsedHankintapvm = new Date(this.hankintapvm.getText().toString());

        // Luo uusi kirja
        Kirja uusiKirja = new Kirja(parsedNumero, parsedNimi, parsedPainos, parsedHankintapvm);

        // Päivitä näkymä
        listaaKirjat();
    }
    private void deletefirst_click(View view) {

        // Poista ensimmäinen rivi tietokannasta
        Database.getDatabase().getKirjaRepository().deleteFirst();

        // Päivitä näkymä
        listaaKirjat();
    }
    private void listaaKirjat() {

        // Poista kaikki nykyiset rivit
        this.listaus.removeAllViews();

        // Hae kannasta kaikki kirjat nousevassa järjestyksessä päivämäärän mukaan
        ArrayList<Kirja> kirjat = Database.getDatabase().getKirjaRepository().getAll();

        // Käy jokainen löytynyt kirja läpi
        for (Kirja kirja : kirjat) {

            // Luo kirjalle oma teksti-elementtie UI:lle
            TextView textView = new TextView(this);
            textView.setText(kirja.toString());

            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(5, 5, 5, 5);
            tableRow.addView(textView);

            this.listaus.addView(tableRow);
        }

    }

    // @Activity
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hae komponentit
        this.numero = findViewById(R.id.numero);
        this.nimi = findViewById(R.id.nimi);
        this.painos = findViewById(R.id.painos);
        this.hankintapvm = findViewById(R.id.hankintapvm);
        this.addnew = findViewById(R.id.addnew);
        this.deletefirst = findViewById(R.id.deletefirst);
        this.listaus = findViewById(R.id.listaus);

        // Aseta kuuntelijat
        this.addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnew_click(view);
            }
        });
        this.deletefirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletefirst_click(view);
            }
        });

        // Aseta tietokanta
        Database.setDatabase(this);

        // Päivitä näytettävät rivit
        this.listaaKirjat();
    }
}
