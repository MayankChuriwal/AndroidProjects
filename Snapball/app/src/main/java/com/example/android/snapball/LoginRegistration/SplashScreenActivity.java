package com.example.android.snapball.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.snapball.LoginRegistration.ChooseLoginRegistrationActivity;
import com.example.android.snapball.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mayankchuriwal on 16/01/18.
 */

public class SplashScreenActivity extends AppCompatActivity {

    public static Boolean started = false;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() !=null)
        {
            Intent intent = new Intent(getApplication(),MainActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;

        }
        else
        {
            Intent intent = new Intent(getApplication(),ChooseLoginRegistrationActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

    }




}
