package com.basic.delivery_partner.clickListeners
import com.basic.delivery_partner.models.CityModel

interface citySelectListener {
    fun selectCity(position: Int, arrCity: CityModel?)
}