package br.com.alura.agenda.dao;

import androidx.room.Dao;
import androidx.room.Query;
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


}
