package com.mcba.comandaclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.ui.interfaces.ISearchCallbacks;

import java.util.ArrayList;

import br.com.mauker.materialsearchview.MaterialSearchView;

/**
 * Created by mac on 15/06/2017.
 */

public abstract class MainSearchActivity extends AppCompatActivity implements ISearchCallbacks {

    private static final String TAG = MainSearchActivity.class.getSimpleName();

    private static final int QUEUED_SEARCH_WAIT_NOTIFIER_DELAY = 400;

    private boolean mInitialSearchActivated;
    private Handler mNotifyHandler = new Handler();
    private QueuedSearchChangeNotifier mQueuedSearchChangeNotifier;
    private ProgressBar mSearchTermProgressBar;
    private MaterialSearchView mSearchView;
    private TextView mTxtHint;

    public abstract void searchByTerm(String searchTerm);

    public abstract void setSearchHint(MaterialSearchView searchView);

    public abstract void getProgressSearch(ProgressBar progressSearch);

    public abstract void setOnSearchviewClose();


    @Override
    public void onStop() {
        super.onStop();
        removeQueuedSearchNotifier();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_main_search, menu);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mTxtHint = (TextView) findViewById(R.id.txt_hint);
        mSearchView.setVoiceIcon(R.drawable.ic_vector_mic);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("WH", TAG + " > query submitted: " + query);
                handleSearchTermChange(query);
                mTxtHint.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("WH", TAG + " > query change: " + newText);
                if (mInitialSearchActivated || !TextUtils.isEmpty(newText)) {
                    mInitialSearchActivated = true;
                    handleSearchTermChange(newText);
                }
                return false;
            }
        });


        mSearchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                mTxtHint.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                mTxtHint.setVisibility(View.VISIBLE);
                setOnSearchviewClose();
            }
        });

        setSearchHint(mSearchView);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle toolbar item clicks here. It'll
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                // Open the search view on the menu item click.
                mSearchView.openSearch();

                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    private void handleSearchTermChange(String searchTerm) {
        removeQueuedSearchNotifier();
        mQueuedSearchChangeNotifier = new QueuedSearchChangeNotifier(searchTerm);
        mNotifyHandler.postDelayed(mQueuedSearchChangeNotifier, QUEUED_SEARCH_WAIT_NOTIFIER_DELAY);
    }

    private void removeQueuedSearchNotifier() {
        if (mQueuedSearchChangeNotifier != null) {
            mNotifyHandler.removeCallbacks(mQueuedSearchChangeNotifier);
        }
    }

    @Override
    public void onSearchRequestCompleted() {
        //mSearchTermProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSearchRequestFailed(Exception spiceException) {
        // mSearchTermProgressBar.setVisibility(View.GONE);
        //  getSpiceActivity().showErrorDialog(spiceException);
    }

    private class QueuedSearchChangeNotifier implements Runnable {

        private String mSearch;

        public QueuedSearchChangeNotifier(String search) {
            this.mSearch = search;
        }

        @Override
        public void run() {
            searchByTerm(mSearch);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false);
                    mTxtHint.setText(searchWrd);
                    mSearchView.closeSearch();
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
