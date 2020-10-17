package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Moeda;

public class ItemMoedaAdapter extends RecyclerView.Adapter<ItemMoedaAdapter.MoedaViewHolder> {

    private List<Moeda> moedas;
    private static RecyclerItemClickListener clickListener;

    public ItemMoedaAdapter(@NonNull  List<Moeda> moedas){
        this.moedas = moedas;
    }

    @NonNull
    @Override
    public ItemMoedaAdapter.MoedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        MoedaViewHolder contatoViewHolder = new MoedaViewHolder(view);
        return contatoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMoedaAdapter.MoedaViewHolder holder, int position) {
        holder.textViewMoedaSigla.setText(moedas.get(position).getSigla());
        holder.textViewMoedaNome.setText(moedas.get(position).getNome());
        holder.textViewMoedaTraducao.setText(moedas.get(position).getTraducao());
        holder.textViewMoedaCotacao.setText(moedas.get(position).getCotacao().toString());
        holder.textViewMoedaDataCadastramento.setText(moedas.get(position).getDateToString());
    }

    @Override
    public int getItemCount() {
        return moedas.size();
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemMoedaAdapter.clickListener = clickListener;
    }

    public class MoedaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewMoedaSigla;
        public TextView textViewMoedaNome;
        public TextView textViewMoedaTraducao;
        public TextView textViewMoedaCotacao;
        public TextView textViewMoedaDataCadastramento;

        public MoedaViewHolder(@NonNull View itemView){
            super(itemView);
            textViewMoedaSigla = itemView.findViewById(R.id.textview_moeda_sigla);
            textViewMoedaNome = itemView.findViewById(R.id.textview_moeda_nome);
            textViewMoedaTraducao = itemView.findViewById(R.id.textview_moeda_traducao);
            textViewMoedaCotacao = itemView.findViewById(R.id.textview_moeda_cotacao);
            textViewMoedaDataCadastramento = itemView.findViewById(R.id.textview_moeda_data_cadastramento);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }
}
