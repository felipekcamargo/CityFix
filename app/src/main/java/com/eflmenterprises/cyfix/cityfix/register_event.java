package com.eflmenterprises.cyfix.cityfix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class register_event extends AppCompatActivity {

    public void screenMessage(String mensagem) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, mensagem, duration);
        toast.show();
    }

    private Button bFoto;
    private Button btnSalvar;
    private ImageView imgFoto;
    private EditText edtTitulo;
    private EditText edtDescricao;
    private EditText edtEndereco;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int RC_CAMERA = 3000;

    private ArrayList<Image> images = new ArrayList<>();

    private File imagemOcorrencia;

    private void dispatchTakePictureIntent() {
        captureImage();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        bFoto = findViewById(R.id.bFotoId);
        imgFoto = findViewById(R.id.imgFotoId);
        btnSalvar = findViewById(R.id.btnSalvarId);
        edtTitulo = findViewById(R.id.edtTituliId);
        edtDescricao = findViewById(R.id.edtDescricaoId);
        edtEndereco = findViewById(R.id.edtEnderecoId);



        bFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = edtTitulo.getText().toString();
                String descricao = edtDescricao.getText().toString();
                String endereco = edtEndereco.getText().toString();

                // TODO validar campos preenchidos / foto tirada
                if (edtTitulo.getText().toString().equals(""))
                {
                    screenMessage("Titulo vazio");
                }
                else
                    {
                    if (edtDescricao.getText().toString().equals(""))
                    {
                        screenMessage("Descricao vazia");
                    } else
                        {
                        if (edtEndereco.getText().toString().equals(""))
                        {
                            screenMessage("Endereço vazio");
                        }
                        else {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ocorrencias")
                                    .push();
                            String pathUrl = String.format("ocorrencias/%s.jpg", ref.getKey());
                            //todo inserir email -> colocar o email do login
                            SharedPreferences sp = getSharedPreferences("cityFix",Context.MODE_PRIVATE);
                            Ocorrencia ocorrencia = new Ocorrencia(ref.getKey(), titulo, descricao, endereco, pathUrl, sp.getString("Email",null));
                            ref.setValue(ocorrencia);

                            btnSalvar.setEnabled(false);

                            salvarFoto(pathUrl,
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(register_event.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(register_event.this, "Ocorrencia cadastrada", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                        }
                    }
                }
            }
        });

    }


    private void salvarFoto(String pathUrl,
                            OnFailureListener onError,
                            OnSuccessListener<UploadTask.TaskSnapshot> onSuccess){

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference ocorrenciaRef = storageRef.child(pathUrl);

        try {
            InputStream stream = new FileInputStream(imagemOcorrencia);

            UploadTask uploadTask = ocorrenciaRef.putStream(stream);
            uploadTask.addOnFailureListener(onError).addOnSuccessListener(onSuccess);

        }catch (FileNotFoundException ignore){
            onError.onFailure(new Exception("Arquivo nao existe"));

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_CAMERA) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void captureImage() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .imageDirectory("CityFix")
                .folderMode(true)
                .single()
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);

            String path = images.get(0).getPath();
            imagemOcorrencia = new File(path);

            Uri imageUri = Uri.fromFile(imagemOcorrencia);

            Glide.with(this)
                    .load(imageUri)
                    .into(imgFoto);
            printImages(images);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void printImages(List<Image> images) {
        if (images == null) return;

        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0, l = images.size(); i < l; i++) {
            stringBuffer.append(images.get(i).getPath()).append("\n");
        }
        Log.d("log", stringBuffer.toString());
    }

}
