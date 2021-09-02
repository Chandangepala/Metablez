package com.basic.metablez.fragments;
import android.content.Intent;
import android.os.Bundle;
       
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.activities.DeliveryOptionsActivity;
import com.basic.metablez.adpaters.CartRecyclVwAdapter;
import com.basic.metablez.clickListeners.CartProductListener;
import com.basic.metablez.models.ProductModel;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements View.OnClickListener, CartProductListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    LinearLayout linlayAddButton;
    RelativeLayout relLayCheckout;
    TextView plusTxt, minusText, centerTxt, txtCheckOut, txtNoOfItems, txtTotalCost;
    RecyclerView recycVwCartProduct;
    ArrayList<ProductModel> arrProductCart = new ArrayList<>();
    CartRecyclVwAdapter cartAdapter;
    ProductModel productCart;
    int multiplier = 1;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        initMain();

        //fixedData();

        setupRecyclerview();

        return view;
    }


    public void initMain(){
      /*  linlayAddButton = view.findViewById(R.id.linlay_add_button);
        centerTxt = view.findViewById(R.id.add_button_center_txt);
        plusTxt = view.findViewById(R.id.plus_txt);
        minusText = view.findViewById(R.id.minus_sign_txt);*/
        recycVwCartProduct = view.findViewById(R.id.recycler_view_cart);
        txtCheckOut = view.findViewById(R.id.txt_checkout);
        txtNoOfItems = view.findViewById(R.id.txt_no_of_items);
        txtTotalCost = view.findViewById(R.id.txt_total_cost);
        relLayCheckout = view.findViewById(R.id.relative_lay_checkout);

        //To get product data from intent parcelable
        getIntentProduct();

        //To hide or unhide checkout relative layout.
        hideUnhideChekout();

        txtCheckOut.setOnClickListener(this);
    }

    //To setup recycler view
    public void setupRecyclerview(){
        recycVwCartProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartRecyclVwAdapter(getContext(), arrProductCart, this, multiplier);
        recycVwCartProduct.setAdapter(cartAdapter);
    }

    public void addProductsAlso(String name, String price, String weight, int img){
        ProductModel productModel = new ProductModel(name, weight, 100, price, 0
                    ,"",1,multiplier);
        arrProductCart.add(productModel);
    }

    public void fixedData(){
        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250" ,"500gms" , R.drawable.farm_chicken_img);
        /*addProductsAlso("Chiken Curry Cut","RS 400", "500gms",R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", "1Kg" ,R.drawable.chicken_a);

        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250" ,"500gms" , R.drawable.farm_chicken_img);
        addProductsAlso("Chiken Curry Cut","RS 400", "500gms",R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", "1Kg" ,R.drawable.chicken_a);

        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250" ,"500gms" , R.drawable.farm_chicken_img);
        addProductsAlso("Chiken Curry Cut","RS 400", "500gms",R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", "1Kg" ,R.drawable.chicken_a);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_checkout:
            startDeliveryOptActivity();
            break;
        }
    }

    //To call deliveryOptActivity intent...
    public void startDeliveryOptActivity(){
        Intent iDelOpt = new Intent(getContext(), DeliveryOptionsActivity.class);
        iDelOpt.putExtra("arrProductCart", arrProductCart);
        startActivity(iDelOpt);
    }

    //To get product data from intent parcelable
    public void getIntentProduct(){

        if(getActivity().getIntent() != null){
            multiplier = getActivity().getIntent().getIntExtra("multiplier", 1);
            arrProductCart = (ArrayList<ProductModel>) getActivity().getIntent().getSerializableExtra("arrCart");
            Log.d("arrayCart", "" + arrProductCart);

            //Log.d("pmMultiplier", ""+ productCart.multiplier);

            if(arrProductCart != null){
                //arrProductCart.add(productCart);

                String items = multiplier + " item(s)";
                txtNoOfItems.setText(items);

                String cost = "Total: RS " + multiplier*arrProductCart.get(0).productPriceDigit;
                txtTotalCost.setText(cost);

            }

        }


    }

    //To hide or unhide checkout relative layout
    public void hideUnhideChekout(){
        if(arrProductCart != null){
            if(arrProductCart.size() == 0)
                relLayCheckout.setVisibility(View.GONE);
        }

    }

    //To update total values in the cart checkout layout
    public void updateCartTotal(ProductModel productModel, int multiplier){
        if(productCart != null){

            String items = multiplier + " item(s)";
            txtNoOfItems.setText(items);

            String cost = "Total: RS " + multiplier*productModel.productPriceDigit;
            txtTotalCost.setText(cost);

        }
    }

    @Override
    public void deleteProduct(int position) {
        arrProductCart.remove(position);
        cartAdapter.notifyItemRemoved(position);
        hideUnhideChekout(); //hide or unhide checkout relative layout
    }

    @Override
    public void addProduct(ProductModel productModel, int position) {
        multiplier++;

        updateCartTotal(productModel, multiplier); //To update checkout bar total

        arrProductCart.get(position).multiplier = multiplier;

        cartAdapter.notifyItemChanged(position);
        //Toast.makeText(getContext(), ""+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void subtractProduct(ProductModel productModel, int position) {
        multiplier--;

        updateCartTotal(productModel, multiplier); //To update checkout bar total

        arrProductCart.get(position).multiplier = multiplier;

        cartAdapter.notifyItemChanged(position);
    }
}