package com.eflmenterprises.cyfix.cityfix;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class login extends AppCompatActivity {

    private Button bCreateAccount; //Cria uma váriavel do tipo button
    private Button bLogin;
    private EditText eEmail;
    private EditText ePassword;


    public void screenMessage(String mensagem) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, mensagem, duration);
        toast.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bCreateAccount = findViewById(R.id.bCreateAccountId); // Linca o que contem na tela com a variavel do tipo button
        bLogin = findViewById(R.id.bLoginId);
        eEmail = findViewById(R.id.eEmailId);
        ePassword = findViewById(R.id.ePasswordId);

        bCreateAccount.setOnClickListener(new View.OnClickListener() { //Qaundo você clica no botão de criar conta
            @Override
            public void onClick(View view) {

                startActivity(new Intent(login.this, create_account.class)); // Ir para outra tela, nesse caso saindo de login e indo para create_account
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Colocar o codigo para recurar as informacoes da base de dados
                if ((ePassword.length() < 7)) //APENHAS UM TESTE DE VALIDAÇÃO DA SENHA
                    screenMessage("A senha deve conter 8 ou mais caracteres");

                else {
                    FirebaseDatabase.getInstance().getReference("cidadaos").orderByChild("E-mail").equalTo(eEmail.getText().toString()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                String minhaVariavelFeliz = (String) dataSnapshot.getChildren().iterator().next().child("Senha").getValue();

                                if (ePassword.getText().toString().equals(minhaVariavelFeliz)) {
                                    startActivity(new Intent(login.this, mainWindow.class));
                                    finish();
                                } else {
                                    screenMessage("Senha incorreta!");
                                }

                            } else {
                                screenMessage("E-mail Incorreto!");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

}
