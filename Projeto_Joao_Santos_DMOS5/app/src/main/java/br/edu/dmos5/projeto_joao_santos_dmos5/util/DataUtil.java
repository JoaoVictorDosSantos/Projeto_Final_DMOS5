package br.edu.dmos5.projeto_joao_santos_dmos5.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    public static String getDataAgora(){
        Date date = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(date);
    }

    public static String converteDateToString(Date date){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(date);
    }
}
