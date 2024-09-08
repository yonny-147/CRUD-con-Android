package com.narvasoft.my_appi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtId,txtUser, txtTitle, txtBody;
    Button btnCreate;
    Button btnGet;
    Button btnUpdate;
    Button btnDelete;
    TextView txtLog; // Para mostrar el log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId = findViewById(R.id.txtId);
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btnCreate = findViewById(R.id.btnCreate);
        btnGet = findViewById(R.id.btnGet);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        txtLog = findViewById(R.id.txtLog);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getById(txtId.getText().toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString(), txtId.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWs(txtId.getText().toString());
            }
        });
}

    private void getById(final String id) {

        String url = "https://jsonplaceholder.typicode.com/posts/"+ id;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                txtId.setText(jsonObject.getString("id"));
                txtUser.setText(jsonObject.getString("userId"));
                txtTitle.setText(jsonObject.getString("title"));
                txtBody.setText(jsonObject.getString("body"));

                // Mostrar log en TextView
                updateLog("Object Get: " + jsonObject.toString(2)); // Muestra JSON formateado

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("Error", error.getMessage());
            Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            updateLog("Get Error: " + error.getMessage());
        });
        Volley.newRequestQueue(this).add(getRequest);
    }

    private void createWs(final String title, final String body, final String userId) {

        String url = "https://jsonplaceholder.typicode.com/posts";


            StringRequest postRequest = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(MainActivity.this, "Post creado: " + response, Toast.LENGTH_LONG).show();
                updateLog("Object Create: " + response);
            }, error -> {
                Log.e("Error", error.getMessage());
                Toast.makeText(MainActivity.this, "Error al crear el post", Toast.LENGTH_SHORT).show();
                updateLog("Create Error: " + error.getMessage());
            })
            {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void updateWs(final String title, final String body, final String Id, final String id) {

        String url = "https://jsonplaceholder.typicode.com/posts/"+ id;

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url, response -> {
            Toast.makeText(MainActivity.this, "Post actualizado: " + response, Toast.LENGTH_LONG).show();
            updateLog("Object Update: " + response);
        }, error -> {
            Log.e("Error", error.getMessage());
            Toast.makeText(MainActivity.this, "Error al actualizar el post", Toast.LENGTH_SHORT).show();
            updateLog("Update Error: " + error.getMessage());
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", Id);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(putRequest);
    }

    private void deleteWs(final String id) {

        String url = "https://jsonplaceholder.typicode.com/posts/"+ id;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, response -> {
            Toast.makeText(MainActivity.this, "Post eliminado: " + response, Toast.LENGTH_LONG).show();
            updateLog("Object Delete: " + response);
        }, error -> {
            Log.e("Error", error.getMessage());
            Toast.makeText(MainActivity.this, "Error al eliminar el post", Toast.LENGTH_SHORT).show();
            updateLog("Delete Error: " + error.getMessage());
        });
        Volley.newRequestQueue(this).add(deleteRequest);
    }

    // Método para actualizar el TextView del log
    private void updateLog(String message) {
        txtLog.setText(message);
    }
}