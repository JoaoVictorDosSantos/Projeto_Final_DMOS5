package br.edu.dmos5.projeto_joao_santos_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.projeto_joao_santos_dmos5.R;
import br.edu.dmos5.projeto_joao_santos_dmos5.model.Traducao;

public class ItemMoedasAdapter extends RecyclerView.Adapter<ItemMoedasAdapter.MoedaViewHolder> {

    private List<Traducao> moedas;
    private static RecyclerItemClickListener clickListener;

    public ItemMoedasAdapter(@NonNull List<Traducao> moedas){
        this.moedas = moedas;
    }

    @NonNull
    @Override
    public ItemMoedasAdapter.MoedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moedas_recyclerview, parent, false);
        ItemMoedasAdapter.MoedaViewHolder contatoViewHolder = new ItemMoedasAdapter.MoedaViewHolder(view);
        return contatoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMoedasAdapter.MoedaViewHolder holder, int position) {
        holder.textViewMoedaSigla.setText(moedas.get(position).getChave());
        holder.textViewMoedaNome.setText(moedas.get(position).getValor());
        holder.textViewMoedaTraducao.setText(moedas.get(position).getTraducao());
    }

    @Override
    public int getItemCount() {
        return moedas.size();
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemMoedasAdapter.clickListener = clickListener;
    }

    public class MoedaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewMoedaSigla;
        public TextView textViewMoedaNome;
        public TextView textViewMoedaTraducao;

        public MoedaViewHolder(@NonNull View itemView){
            super(itemView);
            textViewMoedaSigla = itemView.findViewById(R.id.textview_moeda_sigla);
            textViewMoedaNome = itemView.findViewById(R.id.textview_moeda_nome);
            textViewMoedaTraducao = itemView.findViewById(R.id.textview_moeda_traducao);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }
}
