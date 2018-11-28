package com.eflmenterprises.cyfix.cityfix;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class OcorrenciaActivity extends AppCompatActivity {

    ImageView imgFoto;
    TextView txtDescricao;
    TextView txtEndereco;
   // TextView txtAvaliacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }else {
            Ocorrencia ocorrencia = (Ocorrencia) extras.getSerializable("OCORRENCIA");

            getSupportActionBar().setTitle(ocorrencia.getTitulo());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            imgFoto = findViewById(R.id.imgFotoId);
            txtDescricao = findViewById(R.id.txtDescricaoId);
            txtEndereco = findViewById(R.id.txtEnderecoId);
            //txtAvaliacao = findViewById(R.id.txtAvaliacaoId);
            FirebaseStorage.getInstance().getReference(ocorrencia.getPhotoUrl())
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(OcorrenciaActivity.this)
                            .load(uri)
                            .into(imgFoto);
                }
            });

            txtDescricao.setText(ocorrencia.getDescricao());
            txtEndereco.setText(ocorrencia.getEndereco());
          //  txtAvaliacao.setText(ocorrencia.getSumAvaliacao()/ocorrencia.getCountAvaliacao());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
