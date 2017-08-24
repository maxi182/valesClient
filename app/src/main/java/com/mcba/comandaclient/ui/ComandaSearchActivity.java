package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.mcba.comandaclient.R;
import com.mcba.comandaclient.presenter.ComandaSearchPresenter;

import br.com.mauker.materialsearchview.MaterialSearchView;

/**
 * Created by mac on 24/07/2017.
 */

public class ComandaSearchActivity extends MainSearchActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerview;
    private TextView mSearchHint;

    private ComandaSearchPresenter mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comanda);

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_comandas);
        mSearchHint = (TextView) findViewById(R.id.txt_hint);
        mSearchHint.setText("Nº Comanda");


        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
     //   mPresenter.fetchComandas();

    }

    public static Intent getNewIntent(Context context) {
        return new Intent(context, ComandaSearchActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void searchByTerm(String searchTerm) {

    }

    @Override
    public void setSearchHint(MaterialSearchView searchView) {

        searchView.setHint("N comanda");

    }

    @Override
    public void getProgressSearch(ProgressBar progressSearch) {

    }

    @Override
    public void setOnSearchviewClose() {

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        if (mToolbar != null) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_description
            );
            mToolbar.setNavigationIcon(drawable);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open activity client
                }
            });

            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }


}
