package com.envixo.coursify.CategoryList;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.envixo.coursify.R;

public class DetailPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        String title = getIntent().getStringExtra("TITLE");
        String full_text = getIntent().getStringExtra("TEXT");

        TextView PostItemTitle = findViewById(R.id.detail_title);
        TextView FullText = findViewById(R.id.full_text);
        TextView SaibaMais = findViewById(R.id.more);
        TextView About = findViewById(R.id.sobre);

        PostItemTitle.setText(title);

        FullText.setText(full_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FullText.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        About.setText("O Coursify.me é uma plataforma de ensino a distância, onde qualquer possoa ou empresa pode construir o seu EAD e vender cursos pela internet");

        String url = "https://coursify.me/";

        SaibaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }




}