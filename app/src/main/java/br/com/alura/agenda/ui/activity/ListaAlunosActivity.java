package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.view.ListaAlunosView;

import static br.com.alura.agenda.ui.activity.ConstantesActivitys.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private String TITULO_APPBAR = "Lista de Alunos";
    private ListaAlunosView listaAlunosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaAlunosView = new ListaAlunosView(this);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        configuraNovoFabAluno();
        configuraLista();
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
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_lista_alunos_menu_remover) {
            listaAlunosView.confirmaRemocao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void configuraNovoFabAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
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
        listaAlunosView.atualizaAlunos();
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);

        listaAlunosView.configuraAdaptaer(listaDeAlunos);

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

}
