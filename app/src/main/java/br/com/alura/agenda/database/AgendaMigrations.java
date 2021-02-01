package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import br.com.alura.agenda.model.TipoTelefone;

import static br.com.alura.agenda.model.TipoTelefone.FIXO;

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

    private static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            String scriptSqlCriacaoNovaTabela = "CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefoneFixo` TEXT, " +
                    "`email` TEXT," +
                    " `momentoDeCadastro` INTEGER, " +
                    "`telefoneCelular` TEXT)";

            //step1 - cria a nova tabela na nova estrutura
            database.execSQL(scriptSqlCriacaoNovaTabela);

            //step2 - copia os dados para a nova tabela
            database.execSQL("INSERT INTO Aluno_novo (id,nome , telefoneFixo,email, momentoDeCadastro) " +
                    "SELECT id, nome, telefone, email, momentoDeCadastro FROM Aluno");
            //step3 - deleta a tabela antiga
            database.execSQL("DROP TABLE Aluno");
            //step4 - renomeia a nova tabela
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");

        }
    };
    private static final Migration MIGRATION_5_6 = new Migration(5,6) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            //crio a tabela nova
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `email` TEXT, `momentoDeCadastro` INTEGER)");
            //insiro as informacoes da tabela antiga na tabela nova
            database.execSQL("INSERT INTO Aluno_novo (id,nome,email, momentoDeCadastro) " +
                    "SELECT id, nome, email, momentoDeCadastro FROM Aluno");
            //crio a tabela de telefone
            database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `numero` TEXT, `alunoId` INTEGER NOT NULL, `tipo` TEXT)");
            //populo a tabela de telefone com os dados que estão na tabela antiga Aluno
            database.execSQL("INSERT INTO Telefone (numero, alunoId) " +
                    "SELECT telefoneFixo, id FROM Aluno");
            //pega todos os registros de telefone e seta todos com o tipo telefone Fixo
            database.execSQL("UPDATE Telefone SET tipo = ? ",new TipoTelefone[]{FIXO});
            //step3 - deleta a tabela antiga
            database.execSQL("DROP TABLE Aluno");
            //step4 - renomeia a nova tabela
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");

        }
    };


    public static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6};

}
