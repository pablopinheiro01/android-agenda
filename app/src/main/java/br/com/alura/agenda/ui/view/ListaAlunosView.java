package br.com.alura.agenda.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.activity.ListaAlunosActivity;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final AlunoDAO dao;
    private final Context context;
    private final ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        this.dao = new AlunoDAO();
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
        //reconfigura o adapter no onResume
        adapter.atualiza(dao.todos());
    }


    public void remove(Aluno alunoEscolhido) {
        dao.remove(alunoEscolhido);
        //chamando somente o adapter para remocao
        adapter.remove(alunoEscolhido);
    }


    public void configuraAdaptaer(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
