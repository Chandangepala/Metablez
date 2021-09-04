package com.basic.delivery_partner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basic.delivery_partner.R
import com.basic.delivery_partner.adapters.CityRecyclerVwAdapter
import com.basic.delivery_partner.adapters.LocalityRecyclerVwAdapter
import com.basic.delivery_partner.clickListeners.citySelectListener
import com.basic.delivery_partner.clickListeners.localitySelectListener
import com.basic.delivery_partner.models.CityModel
import com.basic.delivery_partner.models.LocalityModel

class VehicleLocalityActivity : AppCompatActivity(), View.OnClickListener, citySelectListener, localitySelectListener {

    private lateinit var bikeCard: LinearLayout
    private lateinit var bicycleCard: LinearLayout
    private lateinit var eBicycleCard: LinearLayout
    private lateinit var btnProcced: Button
    private lateinit var recyclerVwCity: RecyclerView
    private var arrCity: ArrayList<CityModel> = ArrayList()
    private lateinit var selectCityLayout: LinearLayout
    private lateinit var txtSelectedCity: TextView
    private lateinit var selectedCity: String
    private lateinit var txtLocality: TextView
    private lateinit var recyclerVwLocality: RecyclerView;
    private var arrLocality: ArrayList<LocalityModel> = ArrayList();
    private lateinit var selectedLocation: String
    private lateinit var selectLocalityLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_locality)

        //initialize all the views
        initUI()

        addCity("Jaipur")
        addCity("Jodhpur")
        addCity("Mumbai")
        addCity("Delhi")
        addCity("Hyderabad")
        addCity("Kolkata")

        setupRecyclerVw()
    }

    //To initial all the views
    private fun initUI(){
        bikeCard = findViewById(R.id.bike_card)
        bicycleCard = findViewById(R.id.bicycle_card)
        eBicycleCard = findViewById(R.id.e_bicycle_card)
        btnProcced = findViewById(R.id.btn_procced)
        recyclerVwCity = findViewById(R.id.recycler_vw_cities)
        recyclerVwLocality = findViewById(R.id.recycler_vw_locality)
        txtSelectedCity = findViewById<TextView>(R.id.txt_select_city)
        txtLocality = findViewById(R.id.txt_select_locality)
        selectCityLayout = findViewById(R.id.select_city_layout)
        selectLocalityLayout = findViewById(R.id.select_locality_layout)
        val imgVClose = findViewById<ImageView>(R.id.btn_close_city_select)
        val btnCancel = findViewById<Button>(R.id.btn_cancel_city_select)
        val btnProccedSelectCity = findViewById<Button>(R.id.btn_procced_city_select)
        val imgCloseLocality = findViewById<ImageView>(R.id.btn_close_locality_select)
        val btnCancelLocality = findViewById<Button>(R.id.btn_cancel_locality_select)
        val btnProccedLocality = findViewById<Button>(R.id.btn_procced_locality_select)

        bicycleCard.setOnClickListener(this)
        eBicycleCard.setOnClickListener(this)
        btnProcced.setOnClickListener(this)
        imgVClose.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        btnProccedSelectCity.setOnClickListener(this)
        txtSelectedCity.setOnClickListener(this)
        txtLocality.setOnClickListener(this)
        imgCloseLocality.setOnClickListener(this)
        btnCancelLocality.setOnClickListener(this)
        btnProccedLocality.setOnClickListener(this)
    }

    override fun onClick(p: View?) {
        when(p?.id){
            R.id.bike_card ->{
                bikeCard.setBackgroundResource(R.drawable.veh_backg_red)
            }
            R.id.bicycle_card ->{
                bicycleCard.setBackgroundResource(R.drawable.veh_backg_red)
            }
            R.id.e_bicycle_card ->{
                eBicycleCard.setBackgroundResource(R.drawable.veh_backg_red)
            }
            R.id.btn_procced -> {
                startRegistrationStepsActivity() //calling intent
            }
            R.id.txt_select_city ->{
                selectCityLayout.visibility = View.VISIBLE
            }
            R.id.btn_close_city_select -> {
                selectCityLayout.visibility = View.GONE
            }
            R.id.btn_cancel_city_select -> {
                selectCityLayout.visibility = View.GONE
            }
            R.id.btn_procced_city_select -> {
                selectCityLayout.visibility = View.GONE
                txtSelectedCity.text = selectedCity;
            }
            R.id.txt_select_locality -> {
                //Toast.makeText(applicationContext,"Locality Clicked" ,Toast.LENGTH_SHORT).show()
                getLocality(selectedCity)
                setupRecyclerVwLocality()
                selectLocalityLayout.visibility = View.VISIBLE
            }
            R.id.btn_close_locality_select -> {
                selectLocalityLayout.visibility = View.GONE
            }
            R.id.btn_cancel_locality_select -> {
                selectLocalityLayout.visibility = View.GONE
            }
            R.id.btn_procced_locality_select -> {
                txtLocality.text = selectedLocation
                selectLocalityLayout.visibility = View.GONE
            }
        }
    }

    //To setup and populate recyclerview with arraylist data
    private fun setupRecyclerVw(){
        recyclerVwCity.layoutManager = LinearLayoutManager(this)
        var cityAdapter = CityRecyclerVwAdapter(arrCity, this)
        recyclerVwCity.adapter = cityAdapter
    }

    //To setup recycler view of localities and populate with data
    private fun setupRecyclerVwLocality(){
        recyclerVwLocality.layoutManager = LinearLayoutManager(this)
        var localityAdapter = LocalityRecyclerVwAdapter(arrLocality, this)
        recyclerVwLocality.adapter = localityAdapter
    }

    //To add city in the recycler view
    private fun addCity(city : String){
        var cityModel = CityModel(city);
        cityModel.cityName = city
        arrCity.add(cityModel)
    }

    override fun selectCity(position: Int, cityModel: CityModel?) {
        if (cityModel != null) {
            selectedCity = cityModel.cityName
        }
    }

    //To add locality to the arrLocality
    private fun addLocality(locality: String, coveredAreas: String){
       val localityModel: LocalityModel = LocalityModel(locality, coveredAreas)
       arrLocality.add(localityModel)
    }

    //To add fixed locality values in the arraylist arrLocality
    private fun getLocality(selectedCity: String){
        //Toast.makeText(applicationContext, selectedCity, Toast.LENGTH_SHORT).show();
        if (selectedCity != null){
            when(selectedCity){
                "Jaipur" -> {
                    arrLocality.clear()
                    addLocality("North Jaipur", "alpha, beta, gamma, fie")
                    addLocality("South Jaipur", "alpha, beta, gamma, fie")
                    addLocality("East Jaipur", "alpha, beta, gamma, fie")
                    addLocality("West Jaipur", "alpha, beta, gamma, fie")
                }
                "Jodhpur" -> {
                    arrLocality.clear()
                    addLocality("North Jodhpur", "alpha, beta, gamma, fie")
                    addLocality("South Jodhpur", "alpha, beta, gamma, fie")
                    addLocality("East Jodhpur", "alpha, beta, gamma, fie")
                    addLocality("West Jodhpur", "alpha, beta, gamma, fie")
                }

                "Mumbai" -> {
                    arrLocality.clear()
                    addLocality("North Mumbai", "alpha, beta, gamma, fie")
                    addLocality("South Mumbai", "alpha, beta, gamma, fie")
                    addLocality("East Mumbai", "alpha, beta, gamma, fie")
                    addLocality("West Mumbai", "alpha, beta, gamma, fie")
                }

                "Delhi" -> {
                    arrLocality.clear()
                    addLocality("North Delhi", "alpha, beta, gamma, fie")
                    addLocality("South Delhi", "alpha, beta, gamma, fie")
                    addLocality("East Delhi", "alpha, beta, gamma, fie")
                    addLocality("West Delhi", "alpha, beta, gamma, fie")
                }
                "Kolkata" -> {
                    arrLocality.clear()
                    addLocality("North Kolkata", "alpha, beta, gamma, fie")
                    addLocality("South Kolkata", "alpha, beta, gamma, fie")
                    addLocality("East Kolkata", "alpha, beta, gamma, fie")
                    addLocality("West Kolkata", "alpha, beta, gamma, fie")
                }

                "Hyderabad" -> {
                    arrLocality.clear()
                    addLocality("North Hyderabad", "alpha, beta, gamma, fie")
                    addLocality("South Hyderabad", "alpha, beta, gamma, fie")
                    addLocality("East Hyderabad", "alpha, beta, gamma, fie")
                    addLocality("West Hyderabad", "alpha, beta, gamma, fie")
                }else -> {
                Toast.makeText(applicationContext, "Select a city first", Toast.LENGTH_SHORT).show()
            }
            }
        }else{
            Toast.makeText(applicationContext, "Select a city first", Toast.LENGTH_SHORT).show()
        }

    }

    //To listen locality items click and then change locality textView
    override fun selectLocality(localityModel: LocalityModel) {
        selectedLocation = localityModel.locality
    }

    //To call RegistrationStepsActivity intent
    private fun startRegistrationStepsActivity(){
        val iSteps = Intent(applicationContext, RegistrationStepsActivity:: class.java)
        startActivity(iSteps)
    }
}