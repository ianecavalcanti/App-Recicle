package com.android.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private JsonObject jsonObject;
    private EditText txtName;
    private EditText txtPassword;
    private Session session;
    private SQLiteHandler sqlite;

    private String name, pass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        sqlite = new SQLiteHandler(getApplicationContext());

        session = new Session(getApplicationContext());
        if (session.Conectado()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                name = txtName.getText().toString().trim();
                pass = txtPassword.getText().toString().trim();
                if (!name.isEmpty() && !pass.isEmpty()) {
                    ParsingJson("http://www.mocky.io/v2/5650cd44110000303aac9c8f" + name);
                }
                break;
        }
    }

    private void ParsingJson(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {;
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                jsonObject = mGson.fromJson(response, JsonObject.class);

                if (name.equals(jsonObject.login.get(0).nama.toString())) {
                    if (pass.equals(jsonObject.login.get(0).password.toString())) {
                        session.setLogin(true);
                        sqlite.addUser(name, pass);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"A senha está errada",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"O usuário está errado",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Verificar conexão com a internet",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(getRequest);
    }
}
