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

public class ItemHistoricoMoedaAdapter extends RecyclerView.Adapter<ItemHistoricoMoedaAdapter.MoedaViewHolder> {

    private List<Moeda> moedas;
    private static RecyclerItemClickListener clickListener;

    public ItemHistoricoMoedaAdapter(@NonNull List<Moeda> moedas){
        this.moedas = moedas;
    }

    @NonNull
    @Override
    public ItemHistoricoMoedaAdapter.MoedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico_recyclerview, parent, false);
        ItemHistoricoMoedaAdapter.MoedaViewHolder contatoViewHolder = new ItemHistoricoMoedaAdapter.MoedaViewHolder(view);
        return contatoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHistoricoMoedaAdapter.MoedaViewHolder holder, int position) {
        holder.textViewMoedaCotacao.setText(moedas.get(position).getCotacao().toString());
        holder.textViewMoedaDataCadastramento.setText(moedas.get(position).getDateToString());
    }

    @Override
    public int getItemCount() {
        return moedas.size();
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemHistoricoMoedaAdapter.clickListener = clickListener;
    }

    public class MoedaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewMoedaCotacao;
        public TextView textViewMoedaDataCadastramento;

        public MoedaViewHolder(@NonNull View itemView){
            super(itemView);
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
