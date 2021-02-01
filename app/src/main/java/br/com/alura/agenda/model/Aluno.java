package br.com.alura.agenda.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Aluno implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String telefoneFixo;
    private String email;
    private Calendar momentoDeCadastro = Calendar.getInstance();
    private String telefoneCelular;

    public String getNome() {
        return nome;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean temIdValido() {
        return this.id > 0;
    }

    public String getNomeCompleto(){
        return this.getNome() ;
    }
    public Calendar getMomentoDeCadastro() {
        return momentoDeCadastro;
    }

    public void setMomentoDeCadastro(Calendar momentoDeCadastro) {
        this.momentoDeCadastro = momentoDeCadastro;
    }

    public String dataFormatada(){
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(momentoDeCadastro.getTime());
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }
}
