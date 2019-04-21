package com.example.lab43;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView textView;
ArrayList<String> stocks;//is a resizable array
ArrayAdapter<String> adapter; //Returns a view for each object in a collection of data objects you provide
    RequestQueue queue; //Cola peticiones volley

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
       queue = Volley.newRequestQueue(this); //Creamos una requestqueue para que gestione hilos, peticiones y demas.
        stocks = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stocks); //Pasing the context, the row layout  and the resource?
        textView.setAdapter(adapter); //Setting to the listview the arrayadapter that returns the view from the arraylist
        addstock();

    }
//TODO: add the rest of the stocks
    private void addstock() {
        final String stockID = "AAPL";
        final String stockName = "Apple";
        String url = "https://financialmodelingprep.com/api/company/price/" + stockID + "?datatype=json";

        //Making the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            JSONObject value = response.getJSONObject(stockID);

                            String price = value.getString("price");
                            String Linestock = stockName+ ":"+price+"$";
                            stocks.add(Linestock);//Adding it to the arraylist
                            adapter.notifyDataSetChanged(); //And to the view
                        } catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }

                });
            queue.add(jsonObjectRequest);
    }
}
