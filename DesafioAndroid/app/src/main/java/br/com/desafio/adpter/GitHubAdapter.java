package br.com.desafio.adpter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.desafio.activity.R;
import br.com.desafio.entity.GitHub;
import br.com.desafio.entity.Item;

public class GitHubAdapter extends RecyclerView.Adapter<GitHubAdapter.ViewHolder>{

    private List<Item> itens;

    public GitHubAdapter(List<Item> itens) {
        this.itens = itens;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void addListAll(List<Item> lista) {
         itens.addAll(lista);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeRepositorio;
        public TextView descricaoRepositorio;
        public TextView stars;
        public TextView forks;
        public ImageView fotoAutor;
        public TextView username;


        public ViewHolder(View view) {
            super(view);
            nomeRepositorio = (TextView) view.findViewById(R.id.nome_repositorio);
            descricaoRepositorio = (TextView) view.findViewById(R.id.descricao_repositorio);
            stars = (TextView) view.findViewById(R.id.star_count);
            forks = (TextView) view.findViewById(R.id.fork_count);
            fotoAutor = (ImageView) view.findViewById(R.id.foto_autor);
            username = (TextView) view.findViewById(R.id.username);
        }

    }

    @Override
    public GitHubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        
        ViewHolder viewHolder = (ViewHolder) holder;

        Item item = itens.get(position);

        holder.nomeRepositorio.setText(item.getName());
        holder.descricaoRepositorio.setText(item.getDescription());
        holder.forks.setText(item.getForksCount().toString());
        holder.stars.setText(item.getStargazersCount().toString());
        holder.username.setText(item.getOwner().getLogin());
        Picasso.with(holder.fotoAutor.getContext()).load(item.getOwner().getAvatarUrl()).error(android.R.drawable.sym_contact_card).placeholder(android.R.drawable.sym_contact_card).into(holder.fotoAutor);

    }


    @Override
    public int getItemCount() {
        return itens.size();
    }

    public Item getItem(int id){
        return itens.get(id);
    }


}
