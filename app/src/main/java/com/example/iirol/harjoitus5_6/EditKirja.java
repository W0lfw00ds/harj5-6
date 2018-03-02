package com.example.iirol.harjoitus5_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iirol.harjoitus5_6.Class.Database.Database;
import com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja.Kirja;
import com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja.KirjaRepository;

public class EditKirja extends AppCompatActivity {

	private EditText numero;
	private EditText nimi;
	private EditText painos;
	private EditText hankintapvm;
	private Button tallenna;
	private Button peruuta;

	private Database database;
	private Kirja editableKirja;

	private void tallenna_click(View view) {

	}
	private void peruuta_click(View view) {
		this.finish();
	}
	private void listaaKirja() {
		this.numero.setText(this.editableKirja.getNumero());
		this.nimi.setText(this.editableKirja.getNimi());
		this.painos.setText(this.editableKirja.getPainos());
		this.hankintapvm.setText(this.editableKirja.getHankintapvmString());
	}

	// @Activity
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_kirja);
		this.getViews();

		// Lue lähetetyn kirjan tiedot
		int id = getIntent().getIntExtra(KirjaRepository.COLUMN_ID, 0);
		int numero = getIntent().getIntExtra(KirjaRepository.COLUMN_NUMERO, 0);
		String nimi = getIntent().getStringExtra(KirjaRepository.COLUMN_NIMI);
		int painos = getIntent().getIntExtra(KirjaRepository.COLUMN_PAINOS, 0);
		long hankintapvmUnixTime = getIntent().getLongExtra(KirjaRepository.COLUMN_HANKINTAPVM, 0L);

		this.editableKirja = new Kirja(id, numero, nimi, painos, hankintapvmUnixTime);
		this.database = Database.getInstance(this);

		// Listaa kirja editoitavaksi
		this.listaaKirja();
	}
	private void getViews() {

		this.numero = findViewById(R.id.numero);
		this.nimi = findViewById(R.id.nimi);
		this.painos = findViewById(R.id.painos);
		this.hankintapvm = findViewById(R.id.hankintapvm);
		this.tallenna = findViewById(R.id.tallenna);
		this.peruuta = findViewById(R.id.peruuta);

		this.tallenna.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tallenna_click(view);
			}
		});
		this.peruuta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				peruuta_click(view);
			}
		});

	}

}