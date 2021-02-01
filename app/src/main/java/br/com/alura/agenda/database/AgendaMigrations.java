package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class AgendaMigrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN sobrenome TEXT");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) { //voltando o aluno para remocao do sobre nome
        //mesmo sendo o caso de um retorno, qualquer alteracao na base de dados sempre sera progressiva
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
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
        }
    };

    public static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4};

}
