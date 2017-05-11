package br.edu.usj.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by rafael on 03/05/17.
 */

public class NovaViagemActivity extends Activity {

    private int dia, mes, ano;
    private Button dataSaida, dataChegada;

    private DatePickerDialog.OnDateSetListener listenerSaida =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataSaida.setText(dia+"/"+(mes+1)+"/"+ano);
                }
            };
    private DatePickerDialog.OnDateSetListener listenerChegada =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataChegada.setText(dia+"/"+(mes+1)+"/"+ano);
                }
            };



    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_novaviagem);

        Calendar cal = Calendar.getInstance();
        ano = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);

        dataSaida = (Button) findViewById(R.id.iddatasaida);
        dataSaida.setText(dia+"/"+(mes+1)+"/"+ano);
        dataChegada = (Button) findViewById(R.id.iddatachegada);
        dataChegada.setText(dia+"/"+(mes+1)+"/"+ano);
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.iddatasaida == id){
            return new DatePickerDialog(this, listenerSaida,
                    ano, mes, dia);
        }
        if(R.id.iddatachegada == id){
            return new DatePickerDialog(this, listenerChegada,
                    ano, mes, dia);
        }
        return null;
    }

}
