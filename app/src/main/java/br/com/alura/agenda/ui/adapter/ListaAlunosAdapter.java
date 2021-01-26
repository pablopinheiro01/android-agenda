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
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.activity.ListaAlunosActivity;

public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos = new ArrayList<>();
    private Context context;

    public ListaAlunosAdapter(Context context){
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
        //cria a view com a base no layout
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_aluno, parent
                        //indico que o inflate nao realizara o comportamento padrao
                        , false);

        Aluno alunoDevolvido = alunos.get(position);

        TextView nome = viewCriada.findViewById(R.id.item_aluno_nome);
        nome.setText(alunoDevolvido.getNome());

        TextView telefone = viewCriada.findViewById(R.id.item_aluno_email);
        telefone.setText(alunoDevolvido.getTelefone());

        return viewCriada;
    }

    public void clear() {
        //limpeza do dataset
        alunos.clear();
    }

    public void addAll(List<Aluno> todos) {
        this.alunos.addAll(todos);
    }

    public void remove(Aluno alunoEscolhido) {
        this.alunos.remove(alunoEscolhido);
    }
}
