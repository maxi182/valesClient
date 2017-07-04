package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.presenter.CantPricePresenerImpl;
import com.mcba.comandaclient.presenter.CantPricePresenter;
import com.mcba.comandaclient.presenter.ProductListPresenter;
import com.mcba.comandaclient.presenter.ProductListPresenterImpl;
import com.mcba.comandaclient.ui.CantPriceView;
import com.mcba.comandaclient.ui.ProductsListView;

import java.util.List;

import io.realm.RealmList;


/**
 * Created by mac on 26/06/2017.
 */

public class CantPriceSelectionFragment extends BaseNavigationFragment<CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks> implements CantPriceView, View.OnClickListener, View.OnLongClickListener {

    public static final String PRODUCT_ID = "productId";
    public static final String PROVIDER_ID = "providerId";

    private TextInputLayout mCantTextInputLayout;
    private AppCompatEditText mCantEditText;
    private AppCompatEditText mPriceEditText;
    private ImageButton mImageButtonAdd;
    private ImageButton mImageButtonMinus;
    private ImageButton mImageButtonAddPrice;
    private ImageButton mImageButtonMinusPrice;
    private TextView mProductDesc;


    private LinearLayout mLinear1;
    private LinearLayout mLinear5;
    private LinearLayout mLinear25;
    private LinearLayout mLinear50;
    private LinearLayout mLinear100;

    private CantPricePresenter mCantPricePresenter;


    public static CantPriceSelectionFragment newInstance(int productId, int providerId, int typeId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putInt(PROVIDER_ID, providerId);
        CantPriceSelectionFragment fragment = new CantPriceSelectionFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cant_price_fragment;
    }


    @Override
    protected void setViewReferences() {
        mCantEditText = (AppCompatEditText) findViewById(R.id.cant_edit_text);
        mPriceEditText = (AppCompatEditText) findViewById(R.id.price_edit_text);
        mImageButtonAdd = (ImageButton) findViewById(R.id.img_btn_add);
        mImageButtonMinus = (ImageButton) findViewById(R.id.img_btn_minus);
        mLinear1 = (LinearLayout) findViewById(R.id.linear_1);
        mLinear5 = (LinearLayout) findViewById(R.id.linear_5);
        mLinear25 = (LinearLayout) findViewById(R.id.linear_25);
        mLinear50 = (LinearLayout) findViewById(R.id.linear_50);
        mLinear100 = (LinearLayout) findViewById(R.id.linear_100);


    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mCantPricePresenter = new CantPricePresenerImpl(this);
        mCantPricePresenter.attachView();

        mCantPricePresenter.getProducts();
        mImageButtonAdd.setOnClickListener(this);
        mImageButtonMinus.setOnLongClickListener(this);
        mImageButtonMinus.setOnClickListener(this);
        mLinear1.setOnClickListener(this);
        mLinear5.setOnClickListener(this);
        mLinear25.setOnClickListener(this);
        mLinear50.setOnClickListener(this);
        mLinear100.setOnClickListener(this);

        validateNotZero();
        mCantEditText.setText("1");
        mPriceEditText.setText("100");
        mLinear100.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

    }

    private void validateNotZero() {

        mCantEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().equals("") || charSequence.toString().substring(0, 1).equals("0")) {
                    mCantEditText.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void setBackgroundColor(int resourceId) {

        restoreBackgroundColors();
        LinearLayout button = (LinearLayout) findViewById(resourceId);

        button.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

    }

    private void restoreBackgroundColors() {
        mLinear1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear25.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear50.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear100.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
    }

    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

        Toast.makeText(getActivity(), providers.get(0).providers.get(0).name, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showTypesResponse(List<ProductType> types) {

    }

    @Override
    public void updateQtyText(int value) {

        mCantEditText.setText(String.valueOf(value));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_btn_add:
                if (mCantEditText.getText().length() > 0) {
                    mCantPricePresenter.setQtyText(Integer.valueOf(mCantEditText.getText().toString()), true);
                } else {
                    mCantEditText.setText("1");
                }
                break;
            case R.id.img_btn_minus:
                if (mCantEditText.getText().length() > 0) {
                    mCantPricePresenter.setQtyText(Integer.valueOf(mCantEditText.getText().toString()), false);
                }
                break;
            case R.id.linear_1:
                setBackgroundColor(v.getId());

                break;
            case R.id.linear_5:
                setBackgroundColor(v.getId());

                break;
            case R.id.linear_25:
                setBackgroundColor(v.getId());

                break;
            case R.id.linear_50:
                setBackgroundColor(v.getId());

                break;
            case R.id.linear_100:
                setBackgroundColor(v.getId());

                break;

            case R.id.img_btn_add_price:

                break;

            case R.id.img_btn_minus_price:

                break;
            default:
        }
    }

    @Override
    public void getProviderName() {

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_minus:
                mCantEditText.setText("1");
                break;

            default:
        }
        return true;
    }

    public interface CantPriceSelectionFragmentallbacks {
        void onGoToMainList(int providerId, int productId);
    }


    @Override
    public CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks getDummyCallbacks() {
        return new CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks() {
            @Override
            public void onGoToMainList(int providerId, int productId) {

            }
        };
    }
}
