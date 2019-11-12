package com.example.ludit.games;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class PinguimActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    Button btnCima;
    SharedPreferences preferences;
    String email, nomeFilho;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinguim);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "sasa";
        nomeFilho = "Henrique";

        linearLayout = (LinearLayout) findViewById(R.id.linear);
        final MyView view = new MyView(this, 50, nomeFilho, email);
        linearLayout.addView(view);

        btnCima = (Button) findViewById(R.id.btnPula);

        btnCima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.mexerPinguim(-100);
            }
        });
    }
}



