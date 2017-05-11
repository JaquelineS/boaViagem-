package br.edu.usj.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafael on 10/05/17.
 */

public class GastoListActivity extends ListActivity {

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {
                    TextView textView = (TextView) view;
                    Toast.makeText(getApplicationContext(), "Gasto selecionado:" +
                    textView.getText(), Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                listarGastos()
        ));
        ListView listView = getListView();
        listView.setOnItemClickListener(listener);
    }

    private List<String> listarGastos(){
        return Arrays.asList("Sanduiche R$ 19,90",
                "Taxi Aeroporto - Hotel R$ 34,00",
                "Revista R$ 12,00");
    }
}
