package br.com.alura.agenda.ui;

import android.app.Application;

import androidx.room.Room;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dao.RoomAlunoDAO;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;

//Atende o projeto todo, nao pode ser inserido nada demorado dentro de application pois pode atrazar o inicio da aplicação
public class AgendaApplication extends Application {

    //realiza a criacao somente uma unica vez
    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AgendaDatabase database = Room
                .databaseBuilder(this, AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries()
                .build();
        RoomAlunoDAO dao = database.getRoomAlunoDAO();
        for (int i = 0; i < 2; i++) {
            dao.salva(new Aluno("Alex " + i, "11 98565-87" + i + i + 1, "teste@email.com"));
            dao.salva(new Aluno("João " + i, "11 98598-879" + i, "jao@email.com"));
        }
    }
}
