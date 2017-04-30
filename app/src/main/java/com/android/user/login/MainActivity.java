package com.android.user.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btLogout;
    private SQLiteHandler sqlite;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogout = (Button) findViewById(R.id.btnLogout);

        sqlite = new SQLiteHandler(getApplicationContext());
        session = new Session(getApplicationContext());
        if (!session.Conectado()) {
            logoutUser();
        }

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);
        sqlite.deleteUsers();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
