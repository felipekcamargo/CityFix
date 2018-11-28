package com.eflmenterprises.cyfix.cityfix;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;


public class create_account extends AppCompatActivity {

    private Button bCriarConta;
    private EditText eNome;
    private EditText eEmail;
    private EditText eSenha;
    private EditText eRSenha;
    private EditText eCpf;

    public void screenMessage(String mensagem) //Método da toast message
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, mensagem, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        bCriarConta = findViewById(R.id.bCriarContaId);
        eNome = findViewById(R.id.eNomeId);
        eEmail = findViewById(R.id.eEmailId);
        eSenha = findViewById(R.id.eSenhaId);
        eRSenha = findViewById(R.id.eRSenhaId);
        eCpf = findViewById(R.id.eCpfId);


        bCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Colocar o codigo para mandar para a base de dados

                if (!eNome.getText().toString().equals("") && !eEmail.getText().toString().equals("")) {
                    if (!eSenha.getText().toString().equals(eRSenha.getText().toString())) //APENHAS UM TESTE DE VALIDAÇÃO DA SENHA
                        screenMessage("Senhas não coincidem!");

                    else {

                        if ((eSenha.length() < 7)) //APENHAS UM TESTE DE VALIDAÇÃO DA SENHA
                            screenMessage("A senha deve conter 8 ou mais caracteres");
                        else {
                            if (!validate_cpf.isCPF(eCpf.getText().toString()))
                                screenMessage("CPF Inválido!");
                            else {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("cidadaos");

                                HashMap<String, Object> usuario = new HashMap<>();
                                usuario.put("Nome", eNome.getText().toString());
                                usuario.put("E-mail", eEmail.getText().toString());
                                usuario.put("Senha", eRSenha.getText().toString());

                                myRef.child(eCpf.getText().toString()).setValue(usuario);

                                screenMessage("Conta criada com sucesso!");
                                startActivity(new Intent(create_account.this, login.class)); // Ir para outra tela, nesse caso saindo de login e indo para create_account
                            }
                        }
                    }
                }
                else
                {
                    screenMessage("Preencha os campos Nome e E-mail");
                }
            }
        });


    }
}
