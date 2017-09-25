package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;

/**
 * Created by mac on 29/08/2017.
 */

public class ComandaSearchDetailActivity extends AppCompatActivity implements MainListAdapter.AdapterCallbacks {
    public static final String ITEMS = "items";
    public static final String CANT_BULTOS = "cant_bultos";
    public static final String TOTAL = "total";
    public static final String SENIA = "senia";
    public static final String TIMESTAMP = "timestamp";
    public static final String COMANDAID = "comandaid";
    private Toolbar mToolbar;
    private TextView mTxtSenia;
    private TextView mTxtTotal;
    private TextView mTxtCantBultos;
    private TextView mTxtHint;
    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private ArrayList items;

    public static Intent getNewIntent(Context context, ArrayList<ComandaItem> items, int id, double cantBultos, double total, double senia, long timestamp) {

        Intent intent = new Intent(context, ComandaSearchDetailActivity.class);
        intent.putExtra(CANT_BULTOS, cantBultos);
        intent.putExtra(SENIA, senia);
        intent.putExtra(COMANDAID, id);
        intent.putExtra(TOTAL, total);
        intent.putExtra(TIMESTAMP, timestamp);
        intent.putParcelableArrayListExtra(ITEMS, items);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comanda_detail);
        mTxtHint = (TextView) findViewById(R.id.txt_hint);
        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_comandas);
        mTxtSenia = (TextView) findViewById(R.id.txt_total_senia);
        mTxtTotal = (TextView) findViewById(R.id.txt_total);
        mTxtCantBultos = (TextView) findViewById(R.id.txt_cant_bultos);

        setupToolbar();
        displayTitle();
        displayDataInCv();

        mAdapter = new MainListAdapter(this, this, true);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        items = getIntent().getIntegerArrayListExtra(ITEMS);

    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void displayDataInCv() {

        mTxtSenia.setText("$ " + String.valueOf(getIntent().getDoubleExtra(SENIA, -1)));
        mTxtTotal.setText("$ " + String.valueOf(getIntent().getDoubleExtra(TOTAL, -1)));
        mTxtCantBultos.setText(String.valueOf(getIntent().getDoubleExtra(CANT_BULTOS, -1)));
    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void displayTitle() {

        mTxtHint.setText(String.valueOf(String.format("%05d", getIntent().getIntExtra(COMANDAID, -1))));
        mTxtHint.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTxtHint.setTypeface(Typeface.DEFAULT_BOLD);
        mTxtHint.setTextSize(20);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    @Override
    public void onItemPress(ComandaItem comandaItem) {

    }
}
