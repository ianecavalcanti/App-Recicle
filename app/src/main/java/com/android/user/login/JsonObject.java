package com.android.user.login;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Isfahani on 27-Mei-16.
 */

public class JsonObject {
    @SerializedName("user")
    List<Results> login;

    public class Results {
        @SerializedName("name")
        public String nama;

        @SerializedName("password")
        public String password;
    }
}
