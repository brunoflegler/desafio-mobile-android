package br.com.desafio.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

public abstract class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    public boolean userScrolled = false;
    public Integer page = 1;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            userScrolled = true;

        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int pastVisiblesItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


        if (userScrolled
                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
            userScrolled = false;
            onLoadMore(++page);
        }
    }

    public abstract void onLoadMore(Integer page);

}
