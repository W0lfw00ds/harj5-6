package com.example.iirol.harjoitus5_6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iirol.harjoitus5_6.Class.Database.Database;
import com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja.Kirja;
import com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja.KirjaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText numero;
    private EditText nimi;
    private EditText painos;
    private EditText hankintapvm;
    private Button addnew;
    private Button deletefirst;
    private TableLayout listaus;

	private SimpleDateFormat sdf;
    private Database database;

    private void edit_click(View view) {
	    TableRow clickedTableRow = (TableRow)view;
		Kirja editableKirja = (Kirja)clickedTableRow.getTag();

	    // Luo uusi bundle mihin kootaan välitettävät parametrit toiselle activitylle
	    Bundle bundle = new Bundle();
	    bundle.putInt(KirjaRepository.COLUMN_ID, editableKirja.getId());
	    bundle.putInt(KirjaRepository.COLUMN_NUMERO, editableKirja.getNumero());
	    bundle.putString(KirjaRepository.COLUMN_NIMI, editableKirja.getNimi());
	    bundle.putInt(KirjaRepository.COLUMN_PAINOS, editableKirja.getPainos());
	    bundle.putLong(KirjaRepository.COLUMN_HANKINTAPVM, editableKirja.getHankintapvmUnixTime());

	    // Luo uusi intent millä siirrytään activitystä toiseen, ja lisää bundle siihen mukaan
	    Intent intent = new Intent(this, EditKirja.class);
	    intent.putExtras(bundle);

	    // Siirry toiseen activityyn
	    this.startActivity(intent);
    }
    private void addnew_click(View view) {

        // Numero
	    String parsedStringNumero = this.numero.getText().toString();
	    if (parsedStringNumero.isEmpty()) {
		    Toast.makeText(getApplicationContext(),"Ole hyvä ja syötä 'Numero'!", Toast.LENGTH_LONG).show();
		    return;
	    }
        int parsedNumero = Integer.valueOf(parsedStringNumero);

	    // Nimi
        String parsedNimi = this.nimi.getText().toString();
	    if (parsedNimi.isEmpty()) {
		    Toast.makeText(getApplicationContext(),"Ole hyvä ja syötä 'Nimi'!", Toast.LENGTH_LONG).show();
		    return;
	    }

	    // Painos
	    String parsedStringPainos = this.painos.getText().toString();
	    if (parsedStringPainos.isEmpty()) {
		    Toast.makeText(getApplicationContext(),"Ole hyvä ja syötä 'Painos'!", Toast.LENGTH_LONG).show();
		    return;
	    }
        int parsedPainos = Integer.valueOf(parsedStringPainos);

	    // Hankinta pvm
	    String parsedStringHankintapvm = this.hankintapvm.getText().toString();
	    if (parsedStringHankintapvm.isEmpty()) {
		    Toast.makeText(getApplicationContext(),"Ole hyvä ja syötä 'Hankinta pvm'!", Toast.LENGTH_LONG).show();
		    return;
	    }

	    Date parsedHankintapvm = null;
	    try {
		    parsedHankintapvm = this.sdf.parse(parsedStringHankintapvm);
	    } catch (ParseException e) {
		    Toast.makeText(getApplicationContext(),"Ole hyvä ja syötä 'Hankinta pvm' muodossa 'pp.kk.vvvv'!", Toast.LENGTH_LONG).show();
		    return;
	    }

	    // Luo uusi kirja
        Kirja uusiKirja = new Kirja(parsedNumero, parsedNimi, parsedPainos, parsedHankintapvm);
	    this.database.KirjaRepository.add(uusiKirja);

        // Päivitä näkymä
        this.listaaKirjat();

	    Toast.makeText(getApplicationContext(),"Uusi kirja lisätty!", Toast.LENGTH_LONG).show();
    }
    private void deletefirst_click(View view) {

        // Poista ensimmäinen rivi tietokannasta
        if (this.database.KirjaRepository.deleteFirst()) {
	        Toast.makeText(getApplicationContext(),"Ensimmäinen kirja poistettiin!", Toast.LENGTH_LONG).show();
        } else {
	        Toast.makeText(getApplicationContext(),"Kirjoja ei ole!", Toast.LENGTH_LONG).show();
        }

        // Päivitä näkymä
        this.listaaKirjat();
    }
    private void listaaKirjat() {

        // Poista kaikki nykyiset rivit
        this.listaus.removeAllViews();

        // Hae kannasta kaikki kirjat nousevassa järjestyksessä päivämäärän mukaan
        ArrayList<Kirja> kirjat = this.database.KirjaRepository.getAll();

        // Käy jokainen löytynyt kirja läpi
        for (Kirja kirja : kirjat) {

            // Luo kirjalle oma teksti-elementtie UI:lle
            TextView textView = new TextView(this);
            textView.setText(kirja.toString());
            textView.setTextSize(17f);

            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(5, 5, 5, 5);
            tableRow.addView(textView);
			tableRow.setTag(kirja); // Lisää Kirja-olion referenssi 'TableRow'iin

            this.listaus.addView(tableRow);
        }

    }

    // @Activity
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    this.getViews();

        this.sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.database = Database.getInstance(this);

        // Päivitä näytettävät rivit
        this.listaaKirjat();
    }
    private void getViews() {

	    this.numero = findViewById(R.id.numero);
	    this.nimi = findViewById(R.id.nimi);
	    this.painos = findViewById(R.id.painos);
	    this.hankintapvm = findViewById(R.id.hankintapvm);
	    this.addnew = findViewById(R.id.addnew);
	    this.deletefirst = findViewById(R.id.deletefirst);
	    this.listaus = findViewById(R.id.listaus);

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

    }

}
