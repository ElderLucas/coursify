package com.envixo.coursify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

public class InitSys extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle the splash screen transition Coursify Logo
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        try {
            splashScreen.wait(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_init_sys);

        final View content = findViewById(android.R.id.content);

        content.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        content.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                }
        );

        /* Iniciando a Main Activity*/
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);



    }
}