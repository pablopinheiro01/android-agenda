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

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BASE_DADOS = "agenda.db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context context){
      return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BASE_DADOS)
                .allowMainThreadQueries()
              .addMigrations(
                      new Migration(1, 2) {
                                 @Override
                                 public void migrate(@NonNull SupportSQLiteDatabase database) {
                                     database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                                 }
                             },
                      //mesmo sendo o caso de um retorno, qualquer alteracao na base de dados sempre sera progressiva
                      new Migration(2,3) { //voltando o aluno para remocao do sobre nome
                          @Override
                          public void migrate(@NonNull SupportSQLiteDatabase database) {
                              //o sqlite nao permite realizar a remocao com o drop column devido ser uma base reduzida, infelizmente nao e possivel fazer da forma abaixo
                              //database.execSQL("ALTER TABLE aluno DROP COLUMN sobrenome");

                              //O Sqlite possui a peculiaridade de executar uma remocao conforme tecnica abaixo:
                              //step 1 - Criar nova tabela com as informacoes desejadas - Dica podemos analisar o codigo gerado pelo Room na pasta Generated
                                database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                        "`nome` TEXT, `telefone` TEXT, `email` TEXT)");
                              //step 2 - Copiar dados da tabela antiga para a nova
                                database.execSQL("INSERT INTO Aluno_novo (id,nome,telefone,email) " +
                                        "SELECT id, nome, telefone, email FROM Aluno");
                              //step 3 - remove tabela antiga
                                database.execSQL("DROP TABLE Aluno");
                              //step 4 - Renomear a tabela nova com o nome da tabela antiga
                                database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                          }
                      })
                //.fallbackToDestructiveMigration() //nao pode utilizar isso em producao somente em ambiente de testes devido a destruicao que e realizada no app.
                .build();
    }
}
