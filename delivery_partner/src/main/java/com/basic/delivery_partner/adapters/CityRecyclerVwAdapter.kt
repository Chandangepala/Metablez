package com.basic.delivery_partner.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.basic.delivery_partner.R
import com.basic.delivery_partner.clickListeners.citySelectListener
import com.basic.delivery_partner.models.CityModel

class CityRecyclerVwAdapter(private val arrCity: ArrayList<CityModel>, private val citySelectListener:citySelectListener):
    RecyclerView.Adapter<CityRecyclerVwAdapter.CityViewHolder>() {

    private var lastSelectedPosition = -1

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtCityName = itemView.findViewById<TextView>(R.id.txt_city)
        val radioBtnState = itemView.findViewById<RadioButton>(R.id.city_radio_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_card, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentCity = arrCity[position]

        holder.txtCityName.text = currentCity.cityName
        holder.radioBtnState.setOnClickListener(View.OnClickListener {
            lastSelectedPosition =  position;
            notifyDataSetChanged()

            Log.d("RCVW_CITY",currentCity.cityName)
            citySelectListener.selectCity(position, arrCity.get(position))
        })
        holder.radioBtnState.isChecked = lastSelectedPosition == position

    }

    override fun getItemCount() = arrCity.size

}