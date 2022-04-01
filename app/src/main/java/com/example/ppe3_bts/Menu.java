package com.example.ppe3_bts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button btn_comptesRendus,btn_visiteurs, btn_praticiens, btn_medicaments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_comptesRendus = (Button) findViewById(R.id.btn_comptesRendus);
        btn_visiteurs = (Button) findViewById(R.id.btn_visiteurs);
        btn_praticiens = (Button) findViewById(R.id.btn_praticiens);
        btn_medicaments = (Button) findViewById(R.id.btn_medicaments);


        btn_comptesRendus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Comptes_Rendus.class);
                startActivity(intent);
            }
        });

        btn_visiteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Visiteurs.class);
                startActivity(intent);
            }
        });

        btn_praticiens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PraticiensActivity.class);
                startActivity(intent);
            }
        });

        btn_medicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medicaments.class);
                startActivity(intent);
            }
        });
    }
}