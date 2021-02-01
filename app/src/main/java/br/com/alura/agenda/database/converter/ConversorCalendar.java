package br.com.alura.agenda.database.converter;

import java.util.Calendar;

import androidx.room.TypeConverter;

public class ConversorCalendar {

    @TypeConverter
    public Long toLong(Calendar valor){
        return valor != null ?  valor.getTimeInMillis() :  null ;
    }

    @TypeConverter
    public Calendar toCalendar( Long valor){
        Calendar momentoAtual = Calendar.getInstance();
        if(valor != null){
            momentoAtual.setTimeInMillis(valor);
        }
        return momentoAtual;
    }

}
