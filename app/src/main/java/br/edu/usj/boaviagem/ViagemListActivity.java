package br.edu.usj.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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
    private AlertDialog menu;
    private AlertDialog caixaConfirmacao;
    private int viagemSelecionada;

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {
                    viagemSelecionada = position;
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
                                        NovaViagemActivity.class));
                            break;
                        case 1:
                            startActivity(
                                    new Intent(getApplicationContext(),
                                            NovoGastoActivity.class));
                            break;
                        case 2:
                            startActivity(
                                    new Intent(getApplicationContext(),
                                            GastoListActivity.class));
                            break;
                        case 3:
                            caixaConfirmacao.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            viagens.remove(viagemSelecionada);
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

        this.menu = criarAlertDialog();
        this.caixaConfirmacao = criarConfirmacaoDialog();
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

    private AlertDialog criarAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
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
}
