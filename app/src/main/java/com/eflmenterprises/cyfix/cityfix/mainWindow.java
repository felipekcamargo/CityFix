package com.eflmenterprises.cyfix.cityfix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainWindow extends AppCompatActivity {

    private ListView lV;
    private Button bOcorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        lV = findViewById(R.id.lVId);
        bOcorrencia = findViewById(R.id.bOcorrenciaId);

        FirebaseDatabase.getInstance().getReference("ocorrencias").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final ArrayList<Ocorrencia> list = new ArrayList<Ocorrencia>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            list.add(snapshot.getValue(Ocorrencia.class));
                        }

                        final ArrayAdapter<Ocorrencia> adapter = new ArrayAdapter<Ocorrencia>(mainWindow.this, android.R.layout.simple_list_item_1, list);

                        //---------------------------------Em teste---------------------------------
                        lV.setAdapter(adapter);

                        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view,int position, long id)
                            {
                                final Ocorrencia item = (Ocorrencia) parent.getItemAtPosition(position);

                                Intent intent =  new Intent(mainWindow.this, OcorrenciaActivity.class);
                                intent.putExtra("OCORRENCIA", item);

                                startActivity(intent);
                            }

                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );


        //---------------------------------Em teste---------------------------------


        bOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mainWindow.this, register_event.class)); // Ir para outra tela, nesse caso saindo de login e indo para create_account
            }
        });


    }
}
