package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dao.TelefoneDAO;
import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.converter.ConversorTipoTelefone;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

import static br.com.alura.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BASE_DADOS = "agenda.db";

    public abstract AlunoDAO getAlunoDAO();

    public abstract TelefoneDAO getTelefoneDAO();

    public static AgendaDatabase getInstance(Context context){
      return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BASE_DADOS)
              .allowMainThreadQueries()
              .addMigrations(TODAS_MIGRATIONS)
                //.fallbackToDestructiveMigration() //nao pode utilizar isso em producao somente em ambiente de testes devido a destruicao que e realizada no app.
                .build();
    }
}
