package com.mcba.comandaclient.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.ui.fragment.CantPriceSelectionFragment;
import com.mcba.comandaclient.ui.fragment.EntryFragment;
import com.mcba.comandaclient.ui.fragment.MainListFragment;
import com.mcba.comandaclient.ui.fragment.ProductSelectionFragment;
import com.mcba.comandaclient.ui.fragment.ProductTypeSelectionFragment;
import com.mcba.comandaclient.ui.fragment.ProviderSelectionFragment;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;
import com.mcba.comandaclient.utils.Utils;

import br.com.mauker.materialsearchview.MaterialSearchView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mac on 25/05/2017.
 */

public class MainActivity extends MainSearchActivity implements MainListFragment.MainListFragmentCallbacks, ProductSelectionFragment.ProductSelectionFragmentCallbacks, ProviderSelectionFragment.ProviderSelectionFragmentCallbacks, ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks, EntryFragment.EntryFragmentCallbacks, CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks {

    private static final String STACK_KEY = "stack";
    private static final String ENTRY_FRAGMENT = "entry_fragment";
    private static final String MAIN_LIST_FRAGMENT = "main_list_fragment";
    private static final String MAIN_LIST_FROM_ENTRY_FRAGMENT = "main_list_from_entry_fragment";
    private static final String PRODUCT_LIST_FRAGMENT = "product_list_fragment";
    private static final String PROVIDER_LIST_FRAGMENT = "provider_list_fragment";
    private static final String TYPE_LIST_FRAGMENT = "type_list_fragment";
    private static final String CANT_PRICE_FRAGMENT = "cant_price_fragment";


    private FloatingActionsMenu menuMultipleActions;

    private Toolbar mToolbar;
    private TextView mCurrentDate;


    public static Intent getNewIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void searchByTerm(String searchTerm) {

    }

    @Override
    public void setSearchHint(MaterialSearchView searchView) {

    }

    @Override
    public void getProgressSearch(ProgressBar progressSearch) {

    }

    @Override
    public void setOnSearchviewClose() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setDate();
        menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        setFABButtons(this);

        StorageProvider.getPreferencesString(Constants.RESTORE_FRAGMENT_TAG);
        //handleFragment();

        openEntryFragment();

        //handlele fist fragment

    }

    private void handleFragment() {

        String tag = StorageProvider.getPreferencesString(Constants.RESTORE_FRAGMENT_TAG);

        if (tag == null) {
            openEntryFragment();
        } else {

            switch (tag) {
                case ENTRY_FRAGMENT:
                    openEntryFragment();
                    break;
                case MAIN_LIST_FROM_ENTRY_FRAGMENT:
                    onGoToMainListFromEntryFragment(0);
                    break;
                case PRODUCT_LIST_FRAGMENT:
                    onGoToSelectProduct(0);
                    break;
                case PROVIDER_LIST_FRAGMENT:
                    onGoToSelectProvider(10, 0);
                    break;
                case TYPE_LIST_FRAGMENT:
                    onGoToSelectProductType(1, 10, 0);
                    break;

            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //  StorageProvider.deletePreferences(Constants.RESTORE_FRAGMENT_TAG);
    }

    @Override
    public void onStop() {
        super.onStop();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        StorageProvider.savePreferences(Constants.RESTORE_FRAGMENT_TAG, fragment.getTag());

    }

    /**
     * FABS Creation and adding to view.
     */
    private void setFABButtons(Activity activity) {

        menuMultipleActions = (FloatingActionsMenu) activity.findViewById(R.id.multiple_actions);


        final FloatingActionButton actionCalendar = (FloatingActionButton) activity.findViewById(R.id.action_delete);
        actionCalendar.setIcon(R.drawable.ic_vector_close);
        actionCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuMultipleActions.collapse();
                //showFacebookEvents();

            }
        });

    }


    private void setDate() {

        mCurrentDate = (TextView) findViewById(R.id.current_date);

        mCurrentDate.setText(Utils.getCurrentDate("dd/MM/yyyy"));
    }

    private void openEntryFragment() {

        changeFragment(EntryFragment.newInstance(), false, false, ENTRY_FRAGMENT);

    }

    @Override
    public void onGoToMainListFromEntryFragment(int nextComandaId) {

        changeFragment(MainListFragment.newInstance(nextComandaId), false, false, MAIN_LIST_FROM_ENTRY_FRAGMENT);

    }

    @Override
    public void onGoToMainList(int providerId, int productId, int typeId, double price, int cant, int currentComandaId, int lastItemId, double packagePrice, ItemFullName itemFullName) {
        changeFragment(MainListFragment.newInstance(productId, providerId, typeId, price, cant, currentComandaId, lastItemId, packagePrice, itemFullName), false, false, MAIN_LIST_FRAGMENT);

    }

    @Override
    public void onGoToSelectProduct(int currentComandaId) {

        changeFragment(ProductSelectionFragment.newInstance(currentComandaId), true, false, PRODUCT_LIST_FRAGMENT);


    }

    @Override
    public void onGoToSelectProvider(int productId, int currentComandaId) {

        changeFragment(ProviderSelectionFragment.newInstance(productId, currentComandaId), true, false, PROVIDER_LIST_FRAGMENT);

    }

    @Override
    public void onGoToSelectProductType(int providerId, int productId, int mCurrentComandaId) {

        changeFragment(ProductTypeSelectionFragment.newInstance(productId, providerId, mCurrentComandaId), true, false, TYPE_LIST_FRAGMENT);


    }

    @Override
    public void onGoToSetPriceAndQty(int providerId, int productId, int productTypeId, int mCurrentComandaId) {

        changeFragment(CantPriceSelectionFragment.newInstance(productId, providerId, productTypeId, mCurrentComandaId), true, false, CANT_PRICE_FRAGMENT);

    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_vector_person);
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


    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (animate) {
            ft.setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_right, R.anim.slide_left);
        }
        ft.replace(R.id.container_body, fragment, tag);

        if ((addToBackStack)) {
            ft.addToBackStack(STACK_KEY);
        }
        ft.commit();
    }


    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(MAIN_LIST_FRAGMENT);

        if (currentFragment != null && currentFragment.isVisible()) {
            super.onBackPressed();
        } else {
            return;
        }
    }


}
