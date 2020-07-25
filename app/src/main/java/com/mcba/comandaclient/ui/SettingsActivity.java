package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.presenter.SettingsPresenter;
import com.mcba.comandaclient.presenter.SettingsPresenterImpl;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;
import com.mcba.comandaclient.utils.Utils;

/**
 * Created by mac on 25/09/2017.
 */

public class SettingsActivity extends AppCompatActivity implements SettingsView, View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTxtHint;
    private TextView mBtnUpdate;
    private TextView mLastUpdate;
    private LinearLayout mAddItemLinearLayout;
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
        mLastUpdate = (TextView) findViewById(R.id.txt_last_update);
        mAddItemLinearLayout = (LinearLayout) findViewById(R.id.linear_add_item);
        mBtnUpdate.setOnClickListener(this);
        mAddItemLinearLayout.setOnClickListener(this);

        mPresenter = new SettingsPresenterImpl(this);
        mPresenter.attachView();

        String lastupdate = StorageProvider.getPreferencesString(Constants.LASTUPDATE);
        mLastUpdate.setText(getResources().getString(R.string.last_update) + " " + lastupdate);

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
            case R.id.linear_add_item:
                startActivity(AddProviderActivity.getNewIntent(this));
                break;
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void updateProducts(boolean isSuccess) {


        StorageProvider.savePreferences(Constants.LASTUPDATE, Utils.getCurrentDate(Constants.TIMEDATEFORMAT));

        mLastUpdate.setText(isSuccess ? getResources().getString(R.string.last_update) + " " + Utils.getCurrentDate(Constants.TIMEDATEFORMAT) : "-");

    }
}
