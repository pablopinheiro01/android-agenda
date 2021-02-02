package br.com.alura.agenda.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {

        @Query("SELECT t.* FROM Telefone t " +
                "JOIN Aluno a " +
                "ON t.alunoId = a.id " +
                "WHERE t.alunoId = :alunoId " +
                "LIMIT 1")
        Telefone buscaPrimeiroTelefoneDoAlunoComJoin( int alunoId );

        @Query("SELECT * FROM Telefone " +
                " WHERE alunoId = :alunoId " +
                " LIMIT 1 ")
        Telefone buscaPrimeiroTelefoneDoAluno( int alunoId );

        @Insert
//        void salva(Telefone fixo, Telefone celular);
        void salva(Telefone... telefones); // a lib suporte var args

        @Query("SELECT * FROM Telefone WHERE alunoId = :id")
        List<Telefone> buscaTodosTelefonesDoAluno(int id);

        @Insert(onConflict = OnConflictStrategy.REPLACE)//caso a atualizacao encontre um conflito eu posso resolver com update ou se nao existir a gente insere
        void atualiza(Telefone... telefones);
}
