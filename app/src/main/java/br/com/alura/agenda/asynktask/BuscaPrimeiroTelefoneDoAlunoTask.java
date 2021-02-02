package br.com.alura.agenda.asynktask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.alura.agenda.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaPrimeiroTelefoneDoAlunoTask extends AsyncTask<Void, Void, Telefone> {

    private final TelefoneDAO dao;
    private final int id;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneDoAlunoTask(TelefoneDAO dao,
                                            int id,
                                            PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.id = id;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.buscaPrimeiroTelefoneDoAluno(id);
    }

    @Override
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);//executa a interface para quem implementar apos a execucao e retorno do Background
    }

    public interface PrimeiroTelefoneEncontradoListener{
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
