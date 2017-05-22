package br.edu.usj.boaviagem;

import android.app.ListActivity;
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

    private String dataAnterior = "";

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position,
                                        long id) {
                    Map<String, Object> mapa = gastos.get(position);
                    String descricao = (String) mapa.get("descricao");

                    String mensagem = "Gasto selecionado "+descricao;

                    Toast.makeText(getApplicationContext(), mensagem,
                            Toast.LENGTH_SHORT).show();
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

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Object data, String texto) {

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
