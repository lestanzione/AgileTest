package br.com.stanzione.agiletest.profile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.stanzione.agiletest.R;
import br.com.stanzione.agiletest.data.Repository;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repository> repositoryList;

    public RepositoryAdapter(List<Repository> repositoryList){
        this.repositoryList = repositoryList;
    }

    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repository, parent, false);
        return new RepositoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.ViewHolder holder, int position) {

        final Repository currentRepository = repositoryList.get(position);

        holder.repositoryNameTextView.setText(currentRepository.getName());
        holder.repositoryLanguageTextView.setText(currentRepository.getLanguage());

    }

    @Override
    public int getItemCount() {
        return (null != repositoryList ? repositoryList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repositoryNameTextView)
        TextView repositoryNameTextView;

        @BindView(R.id.repositoryLanguageTextView)
        TextView repositoryLanguageTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
