package com.eflmenterprises.cyfix.cityfix;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Ocorrencia implements Serializable {

    private String id;
    private String titulo;
    private String descricao;
    private String endereco;
    private String photoUrl;
    private String emailUsuario;
  //  private int sumAvaliacao;
 //   private int countAvaliacao;
    public Ocorrencia(){}

    public Ocorrencia(String id, String titulo, String descricao, String endereco, String photoUrl, String emailUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.endereco = endereco;
        this.photoUrl = photoUrl;
        this.emailUsuario = emailUsuario;
      //  this.sumAvaliacao = sumAvaliação;
      //  this.countAvaliacao = countAvaliacao;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmailUsuario(){
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
       this.emailUsuario = emailUsuario;
    }
  //  public int getCountAvaliacao(){
   //     return countAvaliacao;
   // }
   // public void setCountAvaliacao(int countAvaliacao) {
     //   this.countAvaliacao = countAvaliacao;
   // }
    @NonNull
    @Override
    public String toString() {
        return this.titulo;
    }
}
