package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.dao.MoedaDAO;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Moeda;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.ConstanteUtil;

public class CotacoesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUESTCODE_HISTORICO_MOEDA = 99;
    public static final int HISTORICO_ITEM_MOEDA = 98;

    private RecyclerView recyclerViewCotacoes;
    private ItemMoedaAdapter itemMoedaAdapter;
    private Spinner spinnerDatas;
    private Button buttonPesquisar;

    private List<Moeda> moedas;
    private MoedaDAO moedaDAO;
    private String[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacoes);

        recyclerViewCotacoes = findViewById(R.id.recylerview_cotacoes);
        spinnerDatas = findViewById(R.id.spinner_nome);
        buttonPesquisar = findViewById(R.id.button_pesquisar);

        moedaDAO = new MoedaDAO(getApplicationContext());
        buscaDados();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datas);
        spinnerDatas.setAdapter(arrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemMoedaAdapter = new ItemMoedaAdapter(moedas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCotacoes.setLayoutManager(layoutManager);
        recyclerViewCotacoes.setAdapter(itemMoedaAdapter);

        buttonPesquisar.setOnClickListener(this);

        itemMoedaAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putString(ConstanteUtil.KEY_HISTORICO, moedas.get(position).getSigla());
                Intent intent = new Intent(getApplicationContext(), HistoricoMoedaActivity.class);
                intent.putExtras(args);
                startActivityForResult(intent, HISTORICO_ITEM_MOEDA);
            }
        });
    }

    private void buscaDados(){
        datas = moedaDAO.buscaTodasDataCadastramento();
        if(datas != null && datas.length > 0){
            moedas = moedaDAO.buscaPorDataCadastramento(datas[0]);
        }else{
            moedas = new ArrayList<>();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_pesquisar:
                pesquisar();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_HISTORICO_MOEDA:
                if (resultCode == RESULT_OK) {
                    atualizaListView();
                }
                break;
        }
    }

    private void atualizaListView(){
        moedas.clear();
        buscaDados();
        recyclerViewCotacoes.getAdapter().notifyDataSetChanged();
    }

    private void pesquisar(){
        String dataSelecionada = spinnerDatas.getSelectedItem().toString();
        moedas.clear();
        moedas.addAll(moedaDAO.buscaPorDataCadastramento(dataSelecionada));
        recyclerViewCotacoes.getAdapter().notifyDataSetChanged();
    }
}
