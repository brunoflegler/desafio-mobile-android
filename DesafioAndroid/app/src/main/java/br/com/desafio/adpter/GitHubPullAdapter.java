package br.com.desafio.adpter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.desafio.activity.R;
import br.com.desafio.entity.GitHubPull;
import br.com.desafio.entity.Item;

public class GitHubPullAdapter  extends RecyclerView.Adapter<GitHubPullAdapter.ViewHolder>{

    private List<GitHubPull> pulls;

    public GitHubPullAdapter(List<GitHubPull> pulls) {
        this.pulls = pulls;
    }

    public void addListAll(List<Item> lista) {
        pulls.addAll(pulls);
    }

    public List<GitHubPull> getPulls(){
        return pulls;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloPull;
        public TextView descricaoPull;
        public ImageView fotoAutorPull;
        public TextView usernamePull;
        public TextView datePull;


        public ViewHolder(View view) {
            super(view);
            tituloPull = (TextView) view.findViewById(R.id.titulo_pull);
            descricaoPull = (TextView) view.findViewById(R.id.descricao_pull);
            fotoAutorPull = (ImageView) view.findViewById(R.id.foto_autor_pull);
            usernamePull = (TextView) view.findViewById(R.id.username_pull);
            datePull = (TextView) view.findViewById(R.id.date_pull);
        }

    }


    @Override
    public GitHubPullAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repositorio, parent, false);

        GitHubPullAdapter.ViewHolder viewHolder = new GitHubPullAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GitHubPullAdapter.ViewHolder holder, int position) {

        GitHubPullAdapter.ViewHolder viewHolder = (GitHubPullAdapter.ViewHolder) holder;
        GitHubPull pull = pulls.get(position);




        holder.tituloPull.setText(pull.getTitle());
        holder.descricaoPull.setText(pull.getBody());
        holder.usernamePull.setText(pull.getUser().getLogin());
        holder.datePull.setText("Criado em " + pull.getCreatedAt_format());
        Picasso.with(holder.fotoAutorPull.getContext()).load(pull.getUser().getAvatarUrl()).error(android.R.drawable.sym_contact_card).placeholder(android.R.drawable.sym_contact_card).into(holder.fotoAutorPull);

    }

    @Override
    public int getItemCount() {
        return pulls.size();
    }

    public GitHubPull getItem(int id){
        return pulls.get(id);
    }
}
