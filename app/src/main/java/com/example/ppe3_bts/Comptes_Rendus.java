package com.example.ppe3_bts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ppe3_bts.myrequest.Myrequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comptes_Rendus extends AppCompatActivity {

    private Spinner spinnerpraticien;
    private Button btn_valider;
    private EditText et_echantillon, et_numero, et_motif, et_bilan;

    private ProgressBar pb_loader;
    private RequestQueue queue;
    private Myrequest request;

    private void sendAndRequestResponse(String motif, String bilan, String echantillon, String numero){

        queue = Volley.newRequestQueue(this);
        StringRequest mStringRequest;

        Log.d("PRO:", "sendAndRequestResponse");
        String url = "http://10.0.2.2/ppe3bts/comptes_rendus.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("PRO: Response", response);

                        Map<String, String> errors = new HashMap<>();

                        System.out.println("request onresponse");

                        try {
                            JSONObject json = new JSONObject(response);
                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // La création de la competiton s'est bien deroulé
                                Intent intent = new Intent(getApplicationContext(), Menu.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // error
                        Log.d("Error.Response", volleyError.toString());
                        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("motif",motif);
                params.put("bilan",bilan);
                params.put("echantillon",echantillon);
                params.put("numero",numero);

                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes_rendus);

        btn_valider = (Button) findViewById(R.id.btn_valider);
        et_motif = (EditText) findViewById(R.id.editTextTextMotif);
        et_bilan = (EditText) findViewById(R.id.editTextTextBilan);
        et_echantillon = (EditText) findViewById(R.id.editTextTextEchantillon);
        et_numero = (EditText) findViewById(R.id.editTextTextNumero);


        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String motif = et_motif.getText().toString().trim();
                final String bilan = et_bilan.getText().toString().trim();
                final String echantillon = et_echantillon.getText().toString().trim();
                final String numero = et_numero.getText().toString().trim();


                sendAndRequestResponse(motif, bilan, echantillon, numero);
            }
        });


        this.spinnerpraticien = (Spinner) findViewById(R.id.spinner_praticien);

        Praticien[] praticien = PraticienDataUtils.getPraticien();


        ArrayAdapter<Praticien> adapter = new ArrayAdapter<Praticien>(this,
                android.R.layout.simple_spinner_item,
                praticien);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerpraticien.setAdapter(adapter);

        // When user select a List-Item.
        this.spinnerpraticien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Praticien praticien = (Praticien) adapter.getItem(position);

        Toast.makeText(getApplicationContext(), "Selected Praticien: " + praticien.getFullName() ,Toast.LENGTH_SHORT).show();
    }
}