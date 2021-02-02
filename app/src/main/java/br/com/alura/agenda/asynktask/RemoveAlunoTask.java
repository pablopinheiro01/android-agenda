package br.com.alura.agenda.asynktask;

import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {

    private AlunoDAO dao;
    private ListaAlunosAdapter adapter;
    private Aluno aluno;

    public RemoveAlunoTask(AlunoDAO dao, ListaAlunosAdapter adapter, Aluno aluno) {
        this.dao = dao;
        this.adapter = adapter;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //chamando somente o adapter para remocao
        //executa dentro da UI THread apos atualizacao na base
        adapter.remove(aluno);
    }
}
