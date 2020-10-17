package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Traducao;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.ConstanteUtil;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.MoedaTraducaoUtil;

public class MoedasActivity extends AppCompatActivity{

    public static final int REQUESTCODE_HISTORICO_MOEDA = 99;
    public static final int HISTORICO_ITEM_MOEDA = 98;

    private RecyclerView recyclerViewMoedas;
    private ItemMoedasAdapter itemMoedasAdapter;

    private ConstraintLayout MoedasConstraintLayout;
    private List<Traducao> moedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moedas);

        MoedasConstraintLayout = findViewById(R.id.constraint_moedas);
        recyclerViewMoedas = findViewById(R.id.recylerview_moedas);


        moedas = MoedaTraducaoUtil.traducoes;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemMoedasAdapter = new ItemMoedasAdapter(moedas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMoedas.setLayoutManager(layoutManager);
        recyclerViewMoedas.setAdapter(itemMoedasAdapter);

        itemMoedasAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putString(ConstanteUtil.KEY_HISTORICO, moedas.get(position).getChave());
                Intent intent = new Intent(getApplicationContext(), HistoricoMoedaActivity.class);
                intent.putExtras(args);
                startActivityForResult(intent, HISTORICO_ITEM_MOEDA);
            }
        });
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
        moedas.addAll( MoedaTraducaoUtil.traducoes);
        recyclerViewMoedas.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
