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
import com.basic.delivery_partner.clickListeners.shiftSelectListener
import com.basic.delivery_partner.models.ShiftModel

class ShiftRecyclerVwAdapter(private val arrShifts: ArrayList<ShiftModel>, private val shiftSelectListener: shiftSelectListener):
    RecyclerView.Adapter<ShiftRecyclerVwAdapter.ShiftViewHolder>() {

    private var lastSelectedPosition = -1

    class ShiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtShiftTitle = itemView.findViewById<TextView>(R.id.txt_shift_title_card)
        val radioBtnState = itemView.findViewById<RadioButton>(R.id.shift_radio_btn)
        val txtShiftDiscrip: TextView = itemView.findViewById<TextView>(R.id.txt_shit_description_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shifts_card, parent, false)
        return ShiftViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentLocality = arrShifts[position]

        holder.txtShiftTitle.text = currentLocality.shiftTitle
        holder.txtShiftDiscrip.text = currentLocality.shiftDescription
        holder.radioBtnState.setOnClickListener(View.OnClickListener {
            lastSelectedPosition =  position;
            notifyDataSetChanged()

            Log.d("RCVW_CITY",currentLocality.shiftTitle)
            shiftSelectListener.selectShift(arrShifts[position])
        })
        holder.radioBtnState.isChecked = lastSelectedPosition == position

    }

    override fun getItemCount() = arrShifts.size

}