package br.edu.usj.boaviagem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafael on 10/05/17.
 */

public class ViagemListActivity extends ListActivity {

    private List<Map<String, Object>> viagens;

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {

                    Map<String, Object> mapa =
                            viagens.get(position);

                    String destino = (String) mapa.get("destino");
                    String mensagem = "Viagem selecionada "+destino;
                    Toast.makeText(getApplicationContext(),
                            mensagem,
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),
                            GastoListActivity.class));
                }
            };

    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viagens = listarViagens();

        String[] de = {"imagem", "destino", "data", "total" };
        int[] para = {R.id.idtipoviagem, R.id.iddestino,
            R.id.iddata, R.id.idvalor};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                viagens,
                R.layout.layout_lista_viagem,
                de,
                para);

        setListAdapter(adapter);
        ListView listView = getListView();
        listView.setOnItemClickListener(listener);
    }

    private List<Map<String, Object>> listarViagens(){

        List<Map<String, Object>> viagens
                = new ArrayList<Map<String, Object>>();

        Map<String, Object> item =
                new HashMap<String, Object>();
        item.put("imagem", R.drawable.negocios);
        item.put("destino", "São José");
        item.put("data", "17/05/2017 até o dia 24/05/2017");
        item.put("total", "Gasto total R$ 300,00");
        viagens.add(item);

        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Palhoça");
        item.put("data", "24/05/2017 até o dia 31/05/2017");
        item.put("total", "Gasto total R$ 150,00");
        viagens.add(item);

        return viagens;
    }
}
