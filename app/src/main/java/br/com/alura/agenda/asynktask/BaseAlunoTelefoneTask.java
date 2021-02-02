package br.com.alura.agenda.asynktask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

public abstract class BaseAlunoTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    protected BaseAlunoTelefoneTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    protected void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }

}
