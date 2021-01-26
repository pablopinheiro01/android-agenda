package br.com.alura.agenda.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

import static br.com.alura.agenda.ui.activity.ConstantesActivitys.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private String TITULO_APPBAR = "Lista de Alunos";
    private AlunoDAO dao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        configuraNovoFabAluno();
        configuraLista();
        dao.salva(new Aluno("alex", "11 98565-8799","teste@email.com"));
        dao.salva(new Aluno("joao", "11 98598-8799","jao@email.com"));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //inflo o menu e associo ao meu menu de contexto
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //qualquer item de menu de contexto que for clicado aciona esse metodo
    //todos acionam este metodo
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //este objeto foi criado pela equipe do android para garantir que conseguiremos pegar as informações contidas no menu
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //recupera o aluno escolhido atravez do adapter e do objeto com mais dados do menu onde eu cliquei
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        remove(alunoEscolhido);

        return super.onContextItemSelected(item);
    }

    private void configuraNovoFabAluno() {
        FloatingActionButton botaoNovoAluno =findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaAlunos();
    }

    private void atualizaAlunos() {
        //reconfigura o adapter no onResume
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);

        configuraAdaptaer(listaDeAlunos);

        configuraListenerDeCliquePorItem(listaDeAlunos);

        //seta um menu para cada elemento filho
        registerForContextMenu(listaDeAlunos);
    }

  /* comportamento removido devido o tratamento criado no menu de contexto
    private void configuraListenerDeClickLongoPorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) parent.getItemAtPosition(position);
                remove(alunoEscolhido);
                //return true nao passa o evento para outras entidades que tratam o mesmo evento.
                return false; //passa o evento para o proxima entidade tratar que seria o menu de contexto "remover"
            }
        });
    }*/

    private void remove(Aluno alunoEscolhido) {
        dao.remove(alunoEscolhido);
        //chamando somente o adapter para remocao
        adapter.remove(alunoEscolhido);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //refatoracao para a passagem do objeto a partir do adapterView que conhece a View garantindo que eu nao passe a view errada
                Aluno alunoEscolhido = (Aluno) parent.getItemAtPosition(position);
                Log.i("idAluno", String.valueOf(alunoEscolhido.getId()));
                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }

    private void configuraAdaptaer(ListView listaDeAlunos) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
        listaDeAlunos.setAdapter(adapter);
    }
}
