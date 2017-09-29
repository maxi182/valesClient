package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.presenter.SettingsPresenter;
import com.mcba.comandaclient.presenter.SettingsPresenterImpl;

/**
 * Created by mac on 25/09/2017.
 */

public class SettingsActivity extends AppCompatActivity implements SettingsView, View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTxtHint;
    private TextView mBtnUpdate;
    private SettingsPresenter mPresenter;


    public static Intent getNewIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mTxtHint = (TextView) findViewById(R.id.txt_hint);
        mBtnUpdate = (TextView) findViewById(R.id.txt_btn_update);
        mBtnUpdate.setOnClickListener(this);


        mPresenter = new SettingsPresenterImpl(this);
        mPresenter.attachView();


        setupToolbar();
        displayTitle();

    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void displayTitle() {

        mTxtHint.setText(getResources().getString(R.string.settings));
        mTxtHint.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTxtHint.setTypeface(Typeface.DEFAULT_BOLD);
        mTxtHint.setTextSize(20);

    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_btn_update:

                mPresenter.onUpdatePress();

                break;
            default:
        }
    }


    @Override
    public void updateProducts() {


    }
}
