package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.presenter.MainListPresenter;
import com.mcba.comandaclient.ui.fragment.MainListFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mac on 25/05/2017.
 */

public class MainActivity extends AppCompatActivity implements MainListFragment.MainListFragmentCallbacks {

    private static final String STACK_KEY = "stack";
    private MainListPresenter mPresenter;
    private Toolbar mToolbar;


    public static Intent getNewIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        openMainListFragment();

    }

    private void openMainListFragment() {

        changeFragment(MainListFragment.newInstance(), true, false);

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onGoToSelectProduct() {

    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
         }
    }
    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (animate) {
            ft.setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_right, R.anim.slide_left);
        }
        ft.replace(R.id.container_body, fragment);

        if ((addToBackStack)) {
            ft.addToBackStack(STACK_KEY);
        }
        ft.commit();
    }

}
