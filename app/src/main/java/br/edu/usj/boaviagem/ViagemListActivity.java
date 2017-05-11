package br.edu.usj.boaviagem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafael on 10/05/17.
 */

public class ViagemListActivity extends ListActivity {

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>
                                                adapterView,
                                        View view, int position,
                                        long id) {
                    TextView textView = (TextView) view;
                    String mensagem = "Viagem selecionada: "+
                            textView.getText();
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

        setListAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        listarViagens()
                )
        );
        ListView listView = getListView();
        listView.setOnItemClickListener(listener);
    }

    private List<String> listarViagens(){
        return Arrays.asList("São José","Palhoça","Biguaçu",
                "Floripa");
    }
}
