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

import br.edu.usj.boaviagem.entity.Viagem;
import br.edu.usj.boaviagem.util.DatabaseHelper;

/**
 * Created by jaqueline on 26/06/2017.
 */

public class ViagemDAO implements IViagemDAO {

    private SQLiteOpenHelper db;

    public static final String NOME_TABELA = "viagem";

    public ViagemDAO(Context context) {
        db = new DatabaseHelper(context);
    }

    public static String criarTabela() {
        return String.format("CREATE TABLE %s (" +
                "id INTEGER PRIMARY KEY," +
                "destino TEXT," +
                "tipo_viagem TEXT," +
                "data_saida DATE," +
                "data_chegada DATE," +
                "orcamento REAL," +
                "quantidade_pessoas INTEGER);", NOME_TABELA);
    }

    @Override
    public boolean salvar(Viagem viagem) {
        ContentValues values = new ContentValues();
        values.put("destino", viagem.getDestino());
        values.put("tipo_viagem", viagem.getTipoViagem());
        values.put("data_saida", DatabaseHelper.DATE_FORMAT.format(viagem.getDataSaida()));
        values.put("data_chegada", DatabaseHelper.DATE_FORMAT.format(viagem.getDataChegada()));
        values.put("orcamento", viagem.getOrcamento().doubleValue());
        values.put("quantidade_pessoas", viagem.getQuantidadePessoas());

        SQLiteDatabase banco = db.getWritableDatabase();
        long resultado = banco.insert(NOME_TABELA, null, values);
        return resultado > 0;
    }

    @Override
    public boolean excluir(Integer id) {
        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado = banco.delete(NOME_TABELA, "id = ?", new String[]{id.toString()});
        return resultado > 0;
    }

    @Override
    public boolean atualizar(Viagem viagem) {
        ContentValues values = new ContentValues();
        values.put("destino", viagem.getDestino());
        values.put("tipo_viagem", viagem.getTipoViagem());
        values.put("data_saida", DatabaseHelper.DATE_FORMAT.format(viagem.getDataSaida()));
        values.put("data_chegada", DatabaseHelper.DATE_FORMAT.format(viagem.getDataChegada()));
        values.put("orcamento", viagem.getOrcamento().doubleValue());
        values.put("quantidade_pessoas", viagem.getQuantidadePessoas());

        String id = String.valueOf(viagem.getId());
        SQLiteDatabase banco = db.getWritableDatabase();
        int resultado = banco.update(NOME_TABELA, values, "id = ?", new String[]{id});
        return resultado > 0;
    }

    @Override
    public List<Viagem> listar() {
        List<Viagem> retorno = new ArrayList<>();

        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = banco.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Viagem p = new Viagem();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setDestino(cursor.getString(cursor.getColumnIndex("destino")));
                p.setTipoViagem(cursor.getString(cursor.getColumnIndex("tipo_viagem")));
                p.setDataSaida(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data_saida"))));
                p.setDataChegada(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data_chegada"))));
                p.setOrcamento(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("orcamento"))));
                p.setQuantidadePessoas(cursor.getInt(cursor.getColumnIndex("quantidade_pessoas")));

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
    public Viagem buscarPorId(Integer id) {
        SQLiteDatabase banco = db.getReadableDatabase();
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE id = ?";

        String idString = String.valueOf(id);

        Cursor cursor = banco.rawQuery(sql, new String[]{idString});
        try {
            if (cursor.moveToNext()) {
                Viagem p = new Viagem();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setDestino(cursor.getString(cursor.getColumnIndex("destino")));
                p.setTipoViagem(cursor.getString(cursor.getColumnIndex("tipo_viagem")));
                p.setDataSaida(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data_saida"))));
                p.setDataChegada(DatabaseHelper.DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex("data_chegada"))));
                p.setOrcamento(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("orcamento"))));
                p.setQuantidadePessoas(cursor.getInt(cursor.getColumnIndex("quantidade_pessoas")));

                return p;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }
}

