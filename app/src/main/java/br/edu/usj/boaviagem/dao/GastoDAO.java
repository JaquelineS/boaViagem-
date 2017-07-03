package br.edu.usj.boaviagem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.usj.boaviagem.entity.Gasto;
import br.edu.usj.boaviagem.entity.Viagem;
import br.edu.usj.boaviagem.util.DatabaseHelper;

/**
 * Created by jaqueline on 26/06/2017.
 */

public class GastoDAO implements IGastoDAO {

    private SQLiteOpenHelper db;

    public static final String NOME_TABELA = "viagem";

    public GastoDAO(Context context){
        db = new DatabaseHelper(context);
    }

    public static String criarTabela(){
        return String.format("CREATE TABLE %s (" +
                "id INTEGER PRIMARY KEY," +
                "categoria TEXT," +
                "valor REAL," +
                "data DATE," +
                "descricao TEXT," +
                "local TEXT," +
                "viagem_id INTEGER," +
                "FOREIGN KEY (viagem_id) REFERENCES viagem(id));",NOME_TABELA);
    }

    @Override
    public boolean salvar(Gasto gasto) {
        ContentValues values = new ContentValues();
        values.put("categoria", gasto.getCategoria());
        values.put("valor", gasto.getValor().doubleValue());
        values.put("data", DatabaseHelper.DATE_FORMAT.format(gasto.getData()));
        values.put("descricao", gasto.getDescricao());
        values.put("local", gasto.getLocal());
        values.put("viagem_id", gasto.getViagem().getId());

        SQLiteDatabase banco = db.getWritableDatabase();
        long resultado = banco.insert(NOME_TABELA, null, values);
        return resultado > 0;
    }


    @Override
    public boolean excluir(Integer id) {
        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado = banco.delete(NOME_TABELA, "id = ?",new String[]{id.toString()});
        return resultado > 0;
    }

    @Override
    public boolean atualizar(Gasto gasto) {
        ContentValues values = new ContentValues();
        values.put("categoria", gasto.getCategoria());
        values.put("valor", gasto.getValor().doubleValue());
        values.put("data", DatabaseHelper.DATE_FORMAT.format(gasto.getData()));
        values.put("descricao", gasto.getDescricao());
        values.put("local", gasto.getLocal());
        values.put("viagem_id", gasto.getViagem().getId());

        String id = String.valueOf(gasto.getId());

        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado = banco.update(NOME_TABELA, values, "id = ?", new String[]{id});
        return resultado > 0;
    }

    @Override
    public List<Gasto> listar() {
        List<Gasto> retorno = new ArrayList<>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = banco.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Gasto p = new Gasto();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                p.setValor(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("valor"))));
                p.setData(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data"))));
                p.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                p.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                p.setViagem(new Viagem(cursor.getInt(cursor.getColumnIndex("viagem_id"))));

                retorno.add(p);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return retorno;
    }


    @Override
    public Gasto buscarPorId(Integer id) {
        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE id = ?";

        String idString = String.valueOf(id);

        Cursor cursor = banco.rawQuery(sql, new String[]{idString});
        try {
            if (cursor.moveToNext()) {
                Gasto p = new Gasto();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                p.setValor(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("valor"))));
                p.setData(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data"))));
                p.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                p.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                p.setViagem(new Viagem(cursor.getInt(cursor.getColumnIndex("viagem_id"))));

                return p;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }
    @Override
    public List<Gasto> buscarPorViagemId(Integer id) {
        List<Gasto> retorno = new ArrayList<>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE viagem_id = ?";

        String idString = String.valueOf(id);

        Cursor cursor = banco.rawQuery(sql, new String[]{idString});
        try {
            while (cursor.moveToNext()) {
                Gasto p = new Gasto();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                p.setValor(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("valor"))));
                p.setData(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data"))));
                p.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                p.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                p.setViagem(new Viagem(cursor.getInt(cursor.getColumnIndex("viagem_id"))));

                retorno.add(p);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return retorno;
    }
}


