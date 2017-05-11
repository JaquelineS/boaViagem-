package br.edu.usj.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rafael on 12/04/17.
 */

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
    }

    public void selecionarOpcao(View view) {
        switch (view.getId()){
            case R.id.idnova_viagem:
                startActivity(new Intent(
                        this, NovaViagemActivity.class));
                break;
            case R.id.idnovo_gasto:
                startActivity(new Intent(
                        this, NovoGastoActivity.class));
                break;
            case R.id.idminhas_viagens:
                startActivity(new Intent(
                        this, ViagemListActivity.class));
                break;
        }
    }
}
