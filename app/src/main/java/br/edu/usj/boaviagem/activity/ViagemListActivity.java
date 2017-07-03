package br.edu.usj.boaviagem.activity;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.usj.boaviagem.R;
import br.edu.usj.boaviagem.dao.IViagemDAO;
import br.edu.usj.boaviagem.dao.ViagemDAO;
import br.edu.usj.boaviagem.entity.Viagem;

/**
 * Created by jaqueline on 10/05/17.
 */

public class ViagemListActivity extends ListActivity {

    private List<Map<String, Object>> viagens;
    private AlertDialog menu;
    private AlertDialog caixaConfirmacao;
    private int viagemSelecionada;
    private IViagemDAO viagemDAO;

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
                            Intent intent = new Intent(getApplicationContext(), NovaViagemActivity.class);
                            intent.putExtra(NovaViagemActivity.ID_VIAGEM, (Integer) viagens.get(viagemSelecionada).get("id"));
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intentGasto = new Intent(getApplicationContext(), NovoGastoActivity.class);
                            intentGasto.putExtra(NovoGastoActivity.ID_VIAGEM, (Integer) viagens.get(viagemSelecionada).get("id"));
                            startActivity(intentGasto);
                            break;
                        case 2:
                            Intent intentGastoList = new Intent(getApplicationContext(), GastoListActivity.class);
                            intentGastoList.putExtra(GastoListActivity.ID_VIAGEM, (Integer) viagens.get(viagemSelecionada).get("id"));
                            startActivity(intentGastoList);
                            break;
                        case 3:
                            caixaConfirmacao.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            if (viagemDAO.excluir((Integer) viagens.get(viagemSelecionada).get("id"))) {
                                viagens.remove(viagemSelecionada);
                            }
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
        viagemDAO = new ViagemDAO(getApplicationContext());
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

        List<Map<String, Object>> viagens = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (Viagem viagem : viagemDAO.listar()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", viagem.getId());
            item.put("imagem", "lazer".equalsIgnoreCase(viagem.getTipoViagem()) ? R.drawable.lazer : R.drawable.negocios);
            item.put("destino", viagem.getDestino());
            item.put("data", String.format("%s até o dia %s", dateFormat.format(viagem.getDataChegada()),dateFormat.format(viagem.getDataSaida())));
            item.put("total", String.format("Gasto total R$ %s", viagem.getOrcamento()));
            viagens.add(item);
        }

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
