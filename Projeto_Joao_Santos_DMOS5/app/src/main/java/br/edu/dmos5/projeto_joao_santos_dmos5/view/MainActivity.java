package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.api.MoedaService;
import br.edu.dmos5.projeto_joao_santos_dmos5.dao.MoedaDAO;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.MoedaApi;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.ConstanteUtil;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.DataUtil;
import br.edu.dmos5.projeto_joao_santos_dmos5.util.MensagemUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_PERMISSION = 64;
    private static final String BASE_URL = "https://currencyapi.net/api/v1/";

    public static final int REQUESTCODE_MOEDAS = 99;
    public static final int REQUESTCODE_COTACOES = 98;

    private Retrofit retrofit;

    private Button btnAtualizarCotacao;
    private Button btnCotacaoRecente;
    private Button btnMoedas;

    private MoedaDAO moedaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAtualizarCotacao = findViewById(R.id.btn_atualizar_cotacao);
        btnCotacaoRecente = findViewById(R.id.btn_cotacao);
        btnMoedas = findViewById(R.id.btn_moedas);

        moedaDAO = new MoedaDAO(getApplicationContext());

        btnAtualizarCotacao.setOnClickListener(this);
        btnCotacaoRecente.setOnClickListener(this);
        btnMoedas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_atualizar_cotacao:
                if(verificaCadastramento()){
                    if(temPermissao()){
                        buscaMoeda();
                    }else{
                        solicitaPermissao();
                    }
                }else{
                    showSnackbar(MensagemUtil.ATUALIZACAO_JA_FEITA);
                }
                break;
            case R.id.btn_cotacao:
                abrirConsultaCotacao();
                break;
            case R.id.btn_moedas:
                abrirConsultaMoedas();
                break;
        }
    }

    private void abrirConsultaMoedas() {
        Intent in = new Intent(this, MoedasActivity.class);
        startActivityForResult(in, REQUESTCODE_MOEDAS);
    }

    private void abrirConsultaCotacao() {
        Intent in = new Intent(this, CotacoesActivity.class);
        startActivityForResult(in, REQUESTCODE_COTACOES);
    }

    public boolean verificaCadastramento(){
        try {
            return moedaDAO.buscaPorDataCadastramento(DataUtil.getDataAgora()).size() > 0 ? false : true;
        }catch (Exception e){
            showSnackbar(MensagemUtil.ERRO_CONSULTA);
            return false;
        }
    }

    private boolean temPermissao(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            final Activity activity = this;
            new AlertDialog.Builder(this)
                    .setMessage(R.string.explicacao_permissao)
                    .setPositiveButton(R.string.botao_permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface  dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.botao_nao_permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.INTERNET) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    buscaMoeda();
                }
            }
        }
    }

    private void buscaMoeda(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        MoedaService moedaService = retrofit.create(MoedaService.class);
        Call<MoedaApi> call = moedaService.buscaTodas(ConstanteUtil.KEY);

        call.enqueue(new Callback<MoedaApi>() {
            @Override
            public void onResponse(Call<MoedaApi> call, Response<MoedaApi> response) {
                if(response.isSuccessful()){
                    MoedaApi moedaApi = response.body();
                    if (moedaApi != null){
                        try {
                            moedaDAO.add(moedaApi);
                            showSnackbar(MensagemUtil.ATUALIZACAO_SUCESSO);
                        }catch (Exception e){
                            showSnackbar(e.getMessage());
                        }
                    }else{
                        showSnackbar(MensagemUtil.ATUALIZACAO_ERRO);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoedaApi> call, Throwable t) {
                showSnackbar(getString(R.string.erro_api));
            }
        });
    }

    private void showSnackbar(String mensagem){
        ConstraintLayout constraintLayout = findViewById(R.id.layout_main);
        MensagemUtil.exibirSnackbar(mensagem, constraintLayout);
    }
}
