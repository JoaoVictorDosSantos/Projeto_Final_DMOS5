package br.edu.dmos5.projeto_joao_santos_dmos5.util;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class MensagemUtil {

    public static final String ATUALIZACAO_SUCESSO = "Atualização realizada com sucesso!";
    public static final String ATUALIZACAO_ERRO = "Erro ao atualizar as cotações!";
    public static final String ERRO_CONSULTA = "Erro ao consultar o banco de dados!";
    public static final String ATUALIZACAO_JA_FEITA = "Atualização dos dados, já foi realizada com sucesso hoje!";

    public static void exibirSnackbar(String mensagem, ConstraintLayout constraintLayout) {
        Snackbar snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
