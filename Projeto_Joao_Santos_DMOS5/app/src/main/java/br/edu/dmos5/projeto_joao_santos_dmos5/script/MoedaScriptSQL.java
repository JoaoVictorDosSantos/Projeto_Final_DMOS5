package br.edu.dmos5.projeto_joao_santos_dmos5.script;

public class MoedaScriptSQL {

    public static final String TABLE_MOEDA = "moeda";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SIGLA = "sigla";
    public static final String COLUMN_COTACAO = "cotacao";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TRADUCAO = "traducao";
    public static final String COLUMN_DATA_CADASTRAMENTO = "data_cadastramento";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_MOEDA + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_SIGLA + " TEXT NOT NULL,"
                    + COLUMN_NOME + " TEXT NOT NULL,"
                    + COLUMN_TRADUCAO + " TEXT NOT NULL,"
                    + COLUMN_COTACAO + " TEXT NOT NULL, "
                    + COLUMN_DATA_CADASTRAMENTO + " TEXT NOT NULL);";
}
