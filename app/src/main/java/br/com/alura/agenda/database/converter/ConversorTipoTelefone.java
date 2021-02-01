package br.com.alura.agenda.database.converter;

import androidx.room.TypeConverter;
import br.com.alura.agenda.model.TipoTelefone;

public class ConversorTipoTelefone {

    @TypeConverter
    public String toString(TipoTelefone tipo){
            return tipo.name();
    }

    @TypeConverter
    public TipoTelefone toTipoTelefone(String valor){
        if( !valor.equals("") || valor != null){
            return TipoTelefone.valueOf(valor);
        }else{
            return TipoTelefone.FIXO;
        }
    }

}
