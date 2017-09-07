package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.ComandaItem;

import java.util.ArrayList;

/**
 * Created by mac on 29/08/2017.
 */

public class ComandaSearchDetailActivity extends AppCompatActivity {
    public static final String ITEMS = "items";
    public static final String CANT_BULTOS = "cant_bultos";
    public static final String TOTAL = "total";
    public static final String SENIA = "senia";
    public static final String TIMESTAMP = "timestamp";
    public static final String COMANDAID = "comandaid";
    private Toolbar mToolbar;
    private TextView mTxtHint;

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
        setContentView(R.layout.activity_search_comanda);
        mTxtHint = (TextView) findViewById(R.id.txt_hint);
        setupToolbar();
        displayTitle();

        ArrayList items = getIntent().getIntegerArrayListExtra(ITEMS);

    }


    public Toolbar getToolbar() {
        return mToolbar;
    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        if (mToolbar != null) {

            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void displayTitle() {

        mTxtHint.setText(String.valueOf(getIntent().getIntExtra(COMANDAID, -1)));
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

}
