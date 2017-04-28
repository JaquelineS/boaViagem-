package br.edu.usj.boaviagem;

import android.app.Activity;
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
        TextView textView = (TextView) view;
        String opcao = "Opção: "+textView.getText().toString();
        Toast.makeText(this, opcao, Toast.LENGTH_LONG).
                show();
    }
}
