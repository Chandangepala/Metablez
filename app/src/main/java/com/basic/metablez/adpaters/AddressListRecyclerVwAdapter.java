package com.basic.metablez.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.clickListeners.AddressListeners;
import com.basic.metablez.models.AddressModel;

import java.util.ArrayList;

public class AddressListRecyclerVwAdapter extends RecyclerView.Adapter<AddressListRecyclerVwAdapter.AddressViewHolder> {

    Context context;
    ArrayList<AddressModel> arrAddressList;
    AddressListeners addressListeners;

    public AddressListRecyclerVwAdapter(Context context, ArrayList<AddressModel> arrAddressList, AddressListeners addressListeners) {
        this.context = context;
        this.arrAddressList = arrAddressList;
        this.addressListeners = addressListeners;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressViewHolder(LayoutInflater.from(context).inflate(R.layout.address_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.radioButtonAddress.setText(arrAddressList.get(position).fullName);
        holder.txtHouseNo.setText(arrAddressList.get(position).houseNo);

        String street = arrAddressList.get(position).street + ", "
                + arrAddressList.get(position).landmark;
        holder.txtStreet.setText(street);

        holder.txtCity.setText(arrAddressList.get(position).city);

        String mobile = "Mobile No: " + arrAddressList.get(position).mobileNo;
        holder.txtMobile.setText(mobile);

        holder.txtEdit.setOnClickListener(v -> {
            addressListeners.updateAddress(position, arrAddressList.get(position));
        });

        holder.txtDelete.setOnClickListener(v -> {
            addressListeners.deleteAddress(position);
        });
    }

    @Override
    public int getItemCount() {
        return arrAddressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
         RadioButton radioButtonAddress;
         TextView txtHouseNo, txtStreet, txtCity, txtMobile, txtEdit, txtDelete;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButtonAddress = itemView.findViewById(R.id.radio_address_button);
            txtHouseNo = itemView.findViewById(R.id.txt_house_no);
            txtStreet = itemView.findViewById(R.id.txt_street);
            txtCity = itemView.findViewById(R.id.txt_city);
            txtMobile = itemView.findViewById(R.id.txt_mobile_no);
            txtEdit = itemView.findViewById(R.id.txt_edit_address);
            txtDelete = itemView.findViewById(R.id.txt_delete_address);

        }
    }
}
