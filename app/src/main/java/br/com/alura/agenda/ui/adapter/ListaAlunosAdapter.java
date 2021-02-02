package br.com.alura.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.TelefoneDAO;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;
    private final TelefoneDAO dao;

    public ListaAlunosAdapter(Context context){
        this.dao = AgendaDatabase.getInstance(context).getTelefoneDAO();
        this.context = context;
    }

    /*
        getCount() -> representa a quantidade de elementos do adapter;
        getItem() -> Retorna o elemento pela posição;
        getItemId() -> retornar o id do elemento pela posição;
        getView() -> cria a view para cada elemento.
     */
    @Override
    public int getCount() { //quantidade de elementos que um adapter vai ter
        return alunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCriada = criaView(parent);

        Aluno alunoDevolvido = alunos.get(position);

        vincula(viewCriada, alunoDevolvido);

        return viewCriada;
    }

    private void vincula(View viewCriada, Aluno alunoDevolvido) {
        TextView nome = viewCriada.findViewById(R.id.item_aluno_nome);
        nome.setText(alunoDevolvido.getNome());

        TextView telefone = viewCriada.findViewById(R.id.item_aluno_telefone);
//        Telefone primeiroTelefone = dao.buscaPrimeiroTelefoneDoAluno(alunoDevolvido.getId());
//        telefone.setText(primeiroTelefone.getNumero());
    }

    private View criaView(ViewGroup parent) {
        //cria a view com a base no layout
        return LayoutInflater.from(context)
                .inflate(R.layout.item_aluno, parent
                        //indico que o inflate nao realizara o comportamento padrao
                        , false);
    }

    public void remove(Aluno aluno) {
        //limpeza do dataset
        alunos.remove(aluno);
        notifyDataSetChanged(); // notifica o adapter
    }

    public void atualiza(List<Aluno> alunos){
        this.alunos.clear();
        this.alunos.addAll(alunos);
        notifyDataSetChanged();
    }

}
