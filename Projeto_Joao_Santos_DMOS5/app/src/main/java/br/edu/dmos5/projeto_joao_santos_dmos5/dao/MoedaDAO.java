package br.edu.dmos5.projeto_joao_santos_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.dmos5.projeto_joao_santos_dmos5.model.Moeda;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.MoedaApi;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Traducao;
import br.edu.dmos5.projeto_joao_santos_dmos5.script.MoedaScriptSQL;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.DataUtil;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.MoedaTraducaoUtil;

public class MoedaDAO {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqlLiteHelper;

    public MoedaDAO(Context context){
        sqlLiteHelper = new SQLiteHelper(context);
    }

    public void add(MoedaApi moedaApi){
        if (moedaApi == null || moedaApi.getRates() == null) throw new NullPointerException();
        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try{

            String agora = DataUtil.getDataAgora();
            for ( Map.Entry<String, BigDecimal> rate: moedaApi.getRates().entrySet()) {
                Traducao moedaTraducao = MoedaTraducaoUtil.getValorTraducaoPorChave(rate.getKey());
                ContentValues valores = new ContentValues();

                valores.put(MoedaScriptSQL.COLUMN_SIGLA, rate.getKey());
                valores.put(MoedaScriptSQL.COLUMN_COTACAO, rate.getValue().toString());
                valores.put(MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO, agora);

                if(moedaTraducao != null){
                    valores.put(MoedaScriptSQL.COLUMN_NOME,  moedaTraducao.getValor());
                    valores.put(MoedaScriptSQL.COLUMN_TRADUCAO,  moedaTraducao.getTraducao());
                }else{
                    valores.put(MoedaScriptSQL.COLUMN_NOME,  "");
                    valores.put(MoedaScriptSQL.COLUMN_TRADUCAO,  "");
                }

                if(sqLiteDatabase.insert(MoedaScriptSQL.TABLE_MOEDA, null, valores) == -1){
                    throw new SQLException("Erro ao adicionar moeda");
                }
            }
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

    public String[] buscaTodasDataCadastramento(){
        List<String> datas = new ArrayList<>();
        Cursor cursor;
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String colunas[] = new String[]{
                MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO
        };
        String sortOrder = MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO + " ASC";
        cursor = sqLiteDatabase.query(
                true,
                MoedaScriptSQL.TABLE_MOEDA,
                colunas,
                null,
                null,
                null,
                null,
                sortOrder,
                null
        );
        while (cursor.moveToNext()){
            datas.add( cursor.getString(0));
        }
        cursor.close();
        sqLiteDatabase.close();
        return (String[]) datas.toArray(new String[datas.size()]);
    }

    public List<Moeda> buscaPorDataCadastramento(String data){
        if (data == null) throw new NullPointerException();

        List<Moeda> moedas = new ArrayList<>();
        Moeda moeda;
        Cursor cursor;
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String colunas[] = new String[]{
                MoedaScriptSQL.COLUMN_ID,
                MoedaScriptSQL.COLUMN_SIGLA,
                MoedaScriptSQL.COLUMN_NOME,
                MoedaScriptSQL.COLUMN_TRADUCAO,
                MoedaScriptSQL.COLUMN_COTACAO,
                MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO
        };

        String sortOrder = MoedaScriptSQL.COLUMN_SIGLA + " ASC";
        String where = MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO + " = ?";
        String[] argumentos = {data};

        cursor = sqLiteDatabase.query(
                MoedaScriptSQL.TABLE_MOEDA,
                colunas,
                where,
                argumentos,
                null,
                null,
                sortOrder
        );
        while (cursor.moveToNext()){
            try {
                moeda = new Moeda(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        new BigDecimal(cursor.getString(4)),
                        new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(5))
                );
                moedas.add(moeda);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return moedas;
    }

    public List<Moeda> buscaPorSigla(String sigla){

        List<Moeda> moedas = new ArrayList<>();
        Moeda moeda;
        Cursor cursor;
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String colunas[] = new String[]{
                MoedaScriptSQL.COLUMN_ID,
                MoedaScriptSQL.COLUMN_SIGLA,
                MoedaScriptSQL.COLUMN_NOME,
                MoedaScriptSQL.COLUMN_TRADUCAO,
                MoedaScriptSQL.COLUMN_COTACAO,
                MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO
        };

        String sortOrder = MoedaScriptSQL.COLUMN_DATA_CADASTRAMENTO + " ASC";
        String where = MoedaScriptSQL.COLUMN_SIGLA + " = ?";
        String[] argumentos = {sigla};

        cursor = sqLiteDatabase.query(
                MoedaScriptSQL.TABLE_MOEDA,
                colunas,
                where,
                argumentos,
                null,
                null,
                sortOrder
        );
        while (cursor.moveToNext()){
            try {
                moeda = new Moeda(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        new BigDecimal(cursor.getString(4)),
                        new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(5))
                );
                moedas.add(moeda);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return moedas;
    }
}
