package com.example.task2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText cityname;
    TextView centi,faren,lati,longti;

    Toolbar mytoolbar;

    Button result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname =(EditText) findViewById(R.id.city);
        centi= (TextView) findViewById(R.id.resinct);
        faren = (TextView) findViewById(R.id.resincl);
        lati = (TextView) findViewById(R.id.resinlati);
        longti =(TextView)findViewById(R.id.resinlong);
        result = (Button) findViewById(R.id.result);
        mytoolbar =  (Toolbar)findViewById(R.id.mytool);
        setSupportActionBar(mytoolbar);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ct = cityname.getText().toString();

                String url ="https://api.openweathermap.org/data/2.5/weather?q="+ct+"&appid=bfd956cbace006d2c2619dce240d3239";
                if (ct.isEmpty())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Please Enter City Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                }
                else
                {
                    cityname.setError(null);

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject obj = response.getJSONObject("main");

                                JSONObject obj2 = response.getJSONObject("coord");

                                String mytemp = obj.getString("temp");

                                double c=Double.parseDouble(mytemp)-273.15;

                                String myc = String.valueOf(c).substring(0,5);

                                double f = (c*9/5)+32;

                                String myf = String.valueOf(f).substring(0,5);

                                String mylog = obj2.getString("lon");

                                String mylati = obj2.getString("lat");


                                centi.setText(myc);
                                faren.setText(myf);
                                lati.setText(mylati);
                                longti.setText(mylog);



                            } catch (JSONException e) {
                                e.printStackTrace();

                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                        .setMessage("Invalid City Name")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("Invalid City Name")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).show();


                        }
                    });

                    queue.add(request);



                }


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }
}