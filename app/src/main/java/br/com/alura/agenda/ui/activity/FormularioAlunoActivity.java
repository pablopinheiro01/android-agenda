package br.com.alura.agenda.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Novo Aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITULO_APPBAR);
        inicializacaoDosCampos();
        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno alunoCriado = criarAluno();
                salva(alunoCriado);
            }
        });

    }

    private void salva(Aluno alunoCriado) {
        dao.salva(alunoCriado);
        finish(); //encerra a pilha das activits
    }

    private Aluno criarAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        return new Aluno(nome,telefone,email);
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);

    }
}