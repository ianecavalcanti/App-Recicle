package com.android.user.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class Session {
    private static String TAG = Session.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Name";
    private static final String KEY_CONECTADO = "Logado";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean Conectado) {
        editor.putBoolean(KEY_CONECTADO, Conectado);
        editor.commit();
        Log.d(TAG, "Login do usuário modificado!");
    }

    public boolean Conectado(){
        return pref.getBoolean(KEY_CONECTADO, false);
    }
}