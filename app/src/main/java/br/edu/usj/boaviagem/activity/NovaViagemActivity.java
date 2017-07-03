package br.edu.usj.boaviagem.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.usj.boaviagem.R;
import br.edu.usj.boaviagem.dao.IViagemDAO;
import br.edu.usj.boaviagem.dao.ViagemDAO;
import br.edu.usj.boaviagem.entity.Viagem;

/**
 * Created by jaqueline on 03/05/17.
 */

public class NovaViagemActivity extends Activity {

    private int dia, mes, ano;
    private Button dataSaida, dataChegada;
    private Date chegada, saida;
    private IViagemDAO viagemDAO;
    public static final String ID_VIAGEM = "ID_VIAGEM";

    private DatePickerDialog.OnDateSetListener listenerSaida =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int i, int i1, int i2) {
                    ano = i;
                    mes = i1;
                    dia = i2;
                    dataSaida.setText(dia + "/" + (mes + 1) + "/" + ano);

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, ano);
                    cal.set(Calendar.MONTH, mes);
                    cal.set(Calendar.DAY_OF_MONTH, dia);

                    saida = cal.getTime();
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
                    dataChegada.setText(dia + "/" + (mes + 1) + "/" + ano);

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, ano);
                    cal.set(Calendar.MONTH, mes);
                    cal.set(Calendar.DAY_OF_MONTH, dia);

                    chegada = cal.getTime();
                }
            };


    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_novaviagem);

        this.viagemDAO = new ViagemDAO(getApplicationContext());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Intent intent = getIntent();
        int idViagem = intent.getIntExtra(ID_VIAGEM, 0);

        if (idViagem > 0) {
            Viagem viagem = this.viagemDAO.buscarPorId(idViagem);

            RadioGroup rgTipo = (RadioGroup) findViewById(R.id.idtipoviagem);
            int radioId = "lazer".equalsIgnoreCase(viagem.getTipoViagem()) ? 0 : 1;
            RadioButton btn = (RadioButton) rgTipo.getChildAt(radioId);
            btn.setSelected(true);

            ((EditText) findViewById(R.id.iddestino)).setText(viagem.getDestino());
            ((EditText) findViewById(R.id.idorcamento)).setText(viagem.getOrcamento().toString());
            ((EditText) findViewById(R.id.idqtdpessoas)).setText(viagem.getQuantidadePessoas()+"");

            dataSaida = (Button) findViewById(R.id.iddatasaida);
            dataSaida.setText(dateFormat.format(viagem.getDataSaida()));
            dataChegada = (Button) findViewById(R.id.iddatachegada);
            dataChegada.setText(dateFormat.format(viagem.getDataChegada()));

        } else {
            dataSaida = (Button) findViewById(R.id.iddatasaida);
            dataSaida.setText(dateFormat.format(new Date()));
            dataChegada = (Button) findViewById(R.id.iddatachegada);
            dataChegada.setText(dateFormat.format(new Date()));
        }
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (R.id.iddatasaida == id) {
            return new DatePickerDialog(this, listenerSaida,
                    ano, mes, dia);
        }
        if (R.id.iddatachegada == id) {
            return new DatePickerDialog(this, listenerChegada,
                    ano, mes, dia);
        }
        return null;
    }

    public void salvarViagem(View view) {
        RadioGroup rgTipo = (RadioGroup) findViewById(R.id.idtipoviagem);
        int id= rgTipo.getCheckedRadioButtonId();
        View radioButton = rgTipo.findViewById(id);
        int radioId = rgTipo.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) rgTipo.getChildAt(radioId);
        String selection = (String) btn.getText();

        Intent intent = getIntent();
        int idViagem = intent.getIntExtra(ID_VIAGEM, 0);

        Viagem viagem = new Viagem(Integer.valueOf(idViagem));
        viagem.setTipoViagem(selection);
        viagem.setDestino(((EditText) findViewById(R.id.iddestino)).getText().toString());
        viagem.setDataChegada(this.chegada != null ? this.chegada : new Date());
        viagem.setDataSaida(this.saida != null ? this.saida : new Date());
        viagem.setOrcamento(new BigDecimal(((EditText) findViewById(R.id.idorcamento)).getText().toString()));
        viagem.setQuantidadePessoas(Integer.parseInt(((EditText) findViewById(R.id.idqtdpessoas)).getText().toString()));

        if (viagem.getId() == 0 ? this.viagemDAO.salvar(viagem) : this.viagemDAO.atualizar(viagem)) {
            Toast t = Toast.makeText(this, "Viagem salva com sucesso", Toast.LENGTH_LONG);
            t.show();
        }
    }
}
