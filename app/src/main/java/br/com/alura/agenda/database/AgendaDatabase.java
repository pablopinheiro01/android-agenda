package br.com.alura.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 2, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BASE_DADOS = "agenda.db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context context){
      return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BASE_DADOS)
                .allowMainThreadQueries()
              .addMigrations(new Migration(1, 2) {
                  @Override
                  public void migrate(@NonNull SupportSQLiteDatabase database) {
                      database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                  }
              })
                //.fallbackToDestructiveMigration() //nao pode utilizar isso em producao somente em ambiente de testes devido a destruicao que e realizada no app.
                .build();
    }
}
