package br.com.alura.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private int contadorIds = 1;

    public void salva(Aluno aluno) {
        atualizaIds(aluno);
        aluno.setId(contadorIds);
        alunos.add(aluno);
    }

    private void atualizaIds(Aluno aluno) {
        contadorIds++;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void edita(Aluno aluno){
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        if(alunoEncontrado != null){
            int posicaoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoAluno, aluno);
        }

    }

    private Aluno buscaAlunoPeloId(Aluno aluno) {
        Aluno alunoEncontrado = null;
        for (Aluno a: alunos) {
            if (a.getId() == aluno.getId()) {
                alunoEncontrado = a;
            }
        }
        return alunoEncontrado;
    }

    public void remove(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
        if(alunoDevolvido != null){
            alunos.remove(alunoDevolvido);
        }
    }
}
