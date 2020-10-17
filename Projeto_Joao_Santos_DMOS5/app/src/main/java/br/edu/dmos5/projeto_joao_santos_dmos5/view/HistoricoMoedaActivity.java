package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.dao.MoedaDAO;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Moeda;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.ConstanteUtil;

public class HistoricoMoedaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewhistorico;
    private ItemHistoricoMoedaAdapter itemHistoricoMoedaAdapter;

    private TextView textViewSigla;
    private TextView textViewNome;
    private TextView textViewTraducao;
    private ConstraintLayout historicoConstraintLayout;

    private String sigla;
    private MoedaDAO moedaDAO;
    private List<Moeda> moedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_moeda);

        historicoConstraintLayout = findViewById(R.id.constraint_historico);
        recyclerViewhistorico = findViewById(R.id.recylerview_historico);
        textViewSigla = findViewById(R.id.textView_historico_sigla);
        textViewNome = findViewById(R.id.textView_historico_nome);
        textViewTraducao = findViewById(R.id.textView_historico_traducao);

        moedaDAO = new MoedaDAO(getApplicationContext());
        extrairDados();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemHistoricoMoedaAdapter = new ItemHistoricoMoedaAdapter(moedas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewhistorico.setLayoutManager(layoutManager);
        recyclerViewhistorico.setAdapter(itemHistoricoMoedaAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void extrairDados(){
        Intent intent = getIntent();
        Bundle embrulho = intent.getExtras();

        if(embrulho != null){
            sigla = embrulho.getString(ConstanteUtil.KEY_HISTORICO);
            textViewSigla.setText(sigla);
            moedas = moedaDAO.buscaPorSigla(sigla);
            exibeDados();
            if(moedas != null){
                historicoConstraintLayout.setVisibility(View.VISIBLE);
            }else{
                moedas = new ArrayList<>();
                historicoConstraintLayout.setVisibility(View.GONE);
            }
        }
    }

    private void exibeDados(){
        String nome = "Nome";
        String traducao = " Traducao";

        if(moedas != null && moedas.size() > 0){
            nome = moedas.get(0).getNome();
            traducao = moedas.get(0).getTraducao();
        }

        textViewNome.setText(nome);
        textViewTraducao.setText(traducao);
    }
}
