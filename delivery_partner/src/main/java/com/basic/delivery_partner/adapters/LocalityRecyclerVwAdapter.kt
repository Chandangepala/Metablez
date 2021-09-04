package com.basic.delivery_partner.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basic.delivery_partner.R
import com.basic.delivery_partner.clickListeners.localitySelectListener
import com.basic.delivery_partner.models.LocalityModel

class LocalityRecyclerVwAdapter(private val arrlocality: ArrayList<LocalityModel>, private val localitySelectListener: localitySelectListener):
    RecyclerView.Adapter<LocalityRecyclerVwAdapter.LocalViewHolder>() {

    private var lastSelectedPosition = -1

    class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtLocalityName = itemView.findViewById<TextView>(R.id.txt_locality_card)
        val radioBtnState = itemView.findViewById<RadioButton>(R.id.locality_radio_btn)
        val txtAreaCovered = itemView.findViewById<TextView>(R.id.txt_all_covered_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.locality_card, parent, false)
        return LocalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentLocality = arrlocality[position]

        holder.txtLocalityName.text = currentLocality.locality
        holder.txtAreaCovered.text = currentLocality.areaCovered
        holder.radioBtnState.setOnClickListener(View.OnClickListener {
            lastSelectedPosition =  position;
            notifyDataSetChanged()

            Log.d("RCVW_CITY",currentLocality.locality)
            localitySelectListener.selectLocality(arrlocality[position])
        })
        holder.radioBtnState.isChecked = lastSelectedPosition == position

    }

    override fun getItemCount() = arrlocality.size

}