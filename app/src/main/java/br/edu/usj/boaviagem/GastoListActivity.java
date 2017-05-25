package br.edu.usj.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafael on 10/05/17.
 */

public class GastoListActivity extends ListActivity {

    private List<Map<String, Object>> gastos;
    private AlertDialog menu;
    private AlertDialog caixaConfirmacao;
    private int gastoSelecionado;

    private String dataAnterior = "";

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {
                    gastoSelecionado = position;
                    menu.show();
                }
            };

    private DialogInterface.OnClickListener listenerMenu =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface,
                                    int item) {
                    switch (item){
                        case 0:
                            startActivity(
                                    new Intent(getApplicationContext(),
                                            NovoGastoActivity.class));
                            break;
                        case 1:
                            caixaConfirmacao.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            gastos.remove(gastoSelecionado);
                            getListView().invalidateViews();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            caixaConfirmacao.dismiss();
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gastos = listarGastos();
        String[] de = {"data", "descricao", "valor", "categoria"};
        int[] para = {R.id.iddata, R.id.iddescricao,
            R.id.idvalor, R.id.idcategoria};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                gastos,
                R.layout.layout_lista_gasto,
                de,
                para);

        adapter.setViewBinder(new GastoViewBinder());
        setListAdapter(adapter);
        ListView listView = getListView();
        listView.setOnItemClickListener(listener);

        this.menu = criarAlertDialog();
        this.caixaConfirmacao = criarConfirmacaoDialog();
    }

    private List<Map<String, Object>> listarGastos(){

        List<Map<String, Object>> gastos =
                new ArrayList<Map<String, Object>>();

        Map<String, Object> item =
                new HashMap<String, Object>();
        item.put("data", "24/05/2017");
        item.put("descricao", "Lanche");
        item.put("valor", "R$ 20,00");
        item.put("categoria", R.color.categoria_alimentacao);
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("data", "31/05/2017");
        item.put("descricao", "Hotel");
        item.put("valor", "R$ 200,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        return gastos;
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.remover)
        };
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, listenerMenu);

        return builder.create();
    }

    private AlertDialog criarConfirmacaoDialog(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao);
        builder.setPositiveButton(R.string.sim, listenerMenu);
        builder.setNegativeButton(R.string.nao, listenerMenu);
        return builder.create();
    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data,
                                    String texto) {

            if(view.getId() == R.id.iddata){
                if(!dataAnterior.equals(data)){
                    TextView textView = (TextView) view;
                    textView.setText(texto);
                    dataAnterior = texto;
                    view.setVisibility(View.VISIBLE);
                }
                else{
                    view.setVisibility(View.GONE);
                }
                return true;
            }

            if(view.getId() == R.id.idcategoria){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }

            return false;
        }
    }
}
