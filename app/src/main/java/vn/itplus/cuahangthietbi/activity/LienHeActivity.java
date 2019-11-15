package vn.itplus.cuahangthietbi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.itplus.cuahangthietbi.R;

public class LienHeActivity extends AppCompatActivity {
    Button btnFacebook,btnEmail,btnLienHe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("0387663520"));
                startActivity(intent);
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void addControls() {
        btnEmail     = findViewById(R.id.btnEmail);
        btnFacebook  = findViewById(R.id.btnFacebook);
        btnLienHe    = findViewById(R.id.btnLienHe);
    }
}
