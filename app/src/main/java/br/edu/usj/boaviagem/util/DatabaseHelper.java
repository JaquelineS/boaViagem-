package br.edu.usj.boaviagem.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;

import br.edu.usj.boaviagem.dao.GastoDAO;
import br.edu.usj.boaviagem.dao.ViagemDAO;

/**
 * Created by jaqueline on 26/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DADOS = "BancoBoaViagem";
    private static int version = 1;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(ViagemDAO.criarTabela());
            db.execSQL(GastoDAO.criarTabela());
        } catch (Exception e) {
            System.out.println("Tabelas ja criadas");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }



    }
