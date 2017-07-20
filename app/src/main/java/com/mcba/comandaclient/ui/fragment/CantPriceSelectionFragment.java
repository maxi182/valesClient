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
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.presenter.CantPricePresenerImpl;
import com.mcba.comandaclient.presenter.CantPricePresenter;
import com.mcba.comandaclient.ui.CantPriceView;

import java.util.List;

import io.realm.RealmList;


/**
 * Created by mac on 26/06/2017.
 */

public class CantPriceSelectionFragment extends BaseNavigationFragment<CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks> implements CantPriceView, View.OnClickListener, View.OnLongClickListener {

    public static final String PRODUCT_ID = "productId";
    public static final String PROVIDER_ID = "providerId";
    public static final String COMANDA_ID = "comandaId";
    public static final String TYPE_ID = "type_id";
    public static final String INITIAL_QTY = "1";
    public static final String INITIAL_PRICE = "100";


    private LinearLayout mLinearContainer;
    private TextInputLayout mCantTextInputLayout;
    private AppCompatEditText mCantEditText;
    private AppCompatEditText mPriceEditText;
    private ImageButton mImageButtonAdd;
    private ImageButton mImageButtonMinus;
    private ImageButton mImageButtonAddPrice;
    private ImageButton mImageButtonMinusPrice;
    private TextView mProductName;
    private TextView mProviderName;
    private TextView mProductTypeName;
    private TextView mConfirmBtn;

    private LinearLayout mLinear1;
    private LinearLayout mLinear5;
    private LinearLayout mLinear25;
    private LinearLayout mLinear50;
    private LinearLayout mLinear100;

    private int mProductId;
    private int mProviderId;
    private int mTypeId;
    private int mLastItemId;
    private int mCurrentComandaId;
    private int mSelectedResourceId;
    private double mPackagePrice;
    private ItemFullName mItemFullName;

    private CantPricePresenter mCantPricePresenter;


    public static CantPriceSelectionFragment newInstance(int productId, int providerId, int typeId, int currentComandaId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putInt(PROVIDER_ID, providerId);
        args.putInt(TYPE_ID, typeId);
        args.putInt(COMANDA_ID, currentComandaId);

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
        mImageButtonAddPrice = (ImageButton) findViewById(R.id.img_btn_add_price);
        mImageButtonMinusPrice = (ImageButton) findViewById(R.id.img_btn_minus_price);

        mLinearContainer = (LinearLayout) findViewById(R.id.linear_container);

        mProductName = (TextView) findViewById(R.id.txt_productName);
        mProviderName = (TextView) findViewById(R.id.txt_providerName);
        mProductTypeName = (TextView) findViewById(R.id.txt_productTypeName);
        mConfirmBtn = (TextView) findViewById(R.id.btn_green_item_confirm);


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

        mProductId = getArguments().getInt(PRODUCT_ID);
        mProviderId = getArguments().getInt(PROVIDER_ID);
        mTypeId = getArguments().getInt(TYPE_ID);
        mCurrentComandaId = getArguments().getInt(COMANDA_ID);

        mCantPricePresenter.getLastItemId();
        mImageButtonAdd.setOnClickListener(this);
        mImageButtonMinus.setOnLongClickListener(this);
        mImageButtonMinus.setOnClickListener(this);
        mImageButtonAddPrice.setOnClickListener(this);
        mImageButtonMinusPrice.setOnClickListener(this);
        mLinear1.setOnClickListener(this);
        mLinear5.setOnClickListener(this);
        mLinear25.setOnClickListener(this);
        mLinear50.setOnClickListener(this);
        mLinear100.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);


        mCantPricePresenter.getItemNameById(mProductId, mProviderId, mTypeId);

        validateNotZero();
        mCantEditText.setText(INITIAL_QTY);
        mPriceEditText.setText(INITIAL_PRICE);
        mSelectedResourceId = R.id.linear_100;
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
                    mCantEditText.setText(INITIAL_QTY);
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
        mSelectedResourceId = resourceId;

    }

    private void restoreBackgroundColors() {
        mLinear1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear25.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear50.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mLinear100.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
    }

    private void handleBtnPrice(boolean add) {
        switch (mSelectedResourceId) {
            case R.id.linear_1:

                mCantPricePresenter.setPriceText(Double.valueOf(mPriceEditText.getText().toString()), 1, add ? true : false);

                break;
            case R.id.linear_5:

                mCantPricePresenter.setPriceText(Double.valueOf(mPriceEditText.getText().toString()), 5, add ? true : false);

                break;
            case R.id.linear_25:

                mCantPricePresenter.setPriceText(Double.valueOf(mPriceEditText.getText().toString()), 25, add ? true : false);

                break;
            case R.id.linear_50:

                mCantPricePresenter.setPriceText(Double.valueOf(mPriceEditText.getText().toString()), 50, add ? true : false);

                break;
            case R.id.linear_100:

                mCantPricePresenter.setPriceText(Double.valueOf(mPriceEditText.getText().toString()), 100, add ? true : false);

                break;
        }

    }

    @Override
    public void showProductName(ItemFullName name) {
        if (name != null) {
            mProductName.setText(name.productName != null ? name.productName : "");
            mProviderName.setText(name.providerName != null ? name.providerName : "");
            mProductTypeName.setText(name.productTypeName != null ? name.productTypeName : "");
        }
        mItemFullName = name;
        mCantPricePresenter.getPackaging(mProviderId, mProductId, mTypeId);
    }

    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

    }

    @Override
    public void showPackagingResponse(boolean isFree, double value) {

        Toast.makeText(getActivity(), String.valueOf(value), Toast.LENGTH_SHORT).show();
        mPackagePrice = value;
        mLinearContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLastItemId(int id) {

        mLastItemId = id;
        // Toast.makeText(getActivity(), String.valueOf(id).toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showTypesResponse(List<ProductType> types) {

    }

    @Override
    public void updateQtyText(int value) {

        mCantEditText.setText(String.valueOf(value));

    }

    @Override
    public void updatePriceText(double value) {

        mPriceEditText.setText(String.valueOf(value));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_btn_add:
                if (mCantEditText.getText().length() > 0) {
                    mCantPricePresenter.setQtyText(Integer.valueOf(mCantEditText.getText().toString()), true);
                } else {
                    mCantEditText.setText(INITIAL_QTY);
                }
                break;
            case R.id.img_btn_minus:
                if (mCantEditText.getText().length() > 0) {
                    mCantPricePresenter.setQtyText(Integer.valueOf(mCantEditText.getText().toString()), false);
                }
                break;

            case R.id.btn_green_item_confirm:
                mCallbacks.onGoToMainList(mProviderId, mProductId, mTypeId, Double.valueOf(mPriceEditText.getText().toString()), Integer.valueOf(mCantEditText.getText().toString()), mCurrentComandaId, mLastItemId, mPackagePrice, mItemFullName);
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
                handleBtnPrice(true);

                break;

            case R.id.img_btn_minus_price:
                handleBtnPrice(false);

                break;
            default:
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_minus:
                mCantEditText.setText(INITIAL_QTY);
                break;

            default:
        }
        return true;
    }

    public interface CantPriceSelectionFragmentallbacks {
        void onGoToMainList(int providerId, int productId, int typeId, double price, int cant, int currentComandaId, int lastItemId, double packagePrice, ItemFullName mItemFullName);
    }


    @Override
    public CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks getDummyCallbacks() {
        return new CantPriceSelectionFragment.CantPriceSelectionFragmentallbacks() {
            @Override
            public void onGoToMainList(int providerId, int productId, int typeId, double price, int cant, int currentComandaId, int lastItemId, double packagePrice, ItemFullName mItemFullName) {

            }
        };
    }
}
