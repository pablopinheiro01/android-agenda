package br.com.alura.agenda.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.alura.agenda.asynktask.BuscaAlunoTask;
import br.com.alura.agenda.asynktask.RemoveAlunoTask;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final AlunoDAO dao;
    private final Context context;
    private final ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        dao = AgendaDatabase.getInstance(context).getAlunoDAO();
    }


    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog
                .Builder(this.context)
                .setTitle("Removendo Aluno")
                .setMessage("Tem certeza que quer remover o Aluno ?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    //este objeto foi criado pela equipe do android para garantir que conseguiremos pegar as informações contidas no menu
                    AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    //recupera o aluno escolhido atravez do adapter e do objeto com mais dados do menu onde eu cliquei
                    Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
                    remove(alunoEscolhido);
                })
                .setNegativeButton("Não",null)
                .show();
    }


    public void atualizaAlunos() {
        new BuscaAlunoTask(dao, adapter).execute();
    }


    public void remove(Aluno alunoEscolhido) {
        new RemoveAlunoTask(dao, adapter, alunoEscolhido).execute();
    }


    public void configuraAdaptaer(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
