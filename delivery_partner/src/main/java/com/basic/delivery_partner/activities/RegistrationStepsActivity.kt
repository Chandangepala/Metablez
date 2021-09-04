package com.basic.delivery_partner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.VerticalStepView
import com.basic.delivery_partner.R
import com.basic.delivery_partner.adapters.ShiftRecyclerVwAdapter
import com.basic.delivery_partner.classes.VerticalStepViewFrowardFragment
import com.basic.delivery_partner.clickListeners.shiftSelectListener
import com.basic.delivery_partner.models.ShiftModel

class RegistrationStepsActivity : AppCompatActivity(), shiftSelectListener, View.OnClickListener {

    lateinit var verticalStepView: VerticalStepView
    val arrSteps: ArrayList<String> = ArrayList()
    lateinit var shiftLayout: LinearLayout
    lateinit var reyclcerVwShifts: RecyclerView
    val arrShifts: ArrayList<ShiftModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_steps)

        initUI()

        fixedSteps()

        //To initialize and step vertical step view
        setupVerticalStepsView()

        fixedShifts()
    }

    //To initialize all the views
    private fun initUI(){
        verticalStepView = findViewById(R.id.vertical_step_view)
        reyclcerVwShifts = findViewById(R.id.recycler_vw_shift)
        shiftLayout = findViewById(R.id.shift_layout)
        val btnCancelShift: Button = findViewById(R.id.btn_cancel_shift_select)
        val btnProccedShift: Button = findViewById(R.id.btn_procced_shift_select)

        btnCancelShift.setOnClickListener(this)
        btnProccedShift.setOnClickListener(this)
    }

    //To add elements in the arraylist arrSteps
    private fun fixedSteps(){
        arrSteps.add("Shift of work")
        arrSteps.add("Submit personal details")
        arrSteps.add("Click profile photo")
        arrSteps.add("Add T-Shirt size")
        arrSteps.add("Pay onboarding fees")

        setupRecyclerVw()
    }

    //To initialize and step vertical step view
    private fun setupVerticalStepsView(){
        verticalStepView.setStepsViewIndicatorComplectingPosition(arrSteps.size - 4)
            .reverseDraw(false)
            .setStepViewTexts(arrSteps)
            .setLinePaddingProportion(.85f)
            .setStepsViewIndicatorCompletedLineColor(getColor(R.color.red))
            .setStepsViewIndicatorUnCompletedLineColor(getColor(R.color.liteGray))
            .setStepViewComplectedTextColor(getColor(R.color.red))//设置StepsView text完成线的颜色
            .setStepViewUnComplectedTextColor(getColor(R.color.liteGray))//设置StepsView text未完成线的颜色
            .setStepsViewIndicatorCompleteIcon(getDrawable( R.drawable.ic_baseline_check_circle_24))//设置StepsViewIndicator CompleteIcon
            .setStepsViewIndicatorDefaultIcon(getDrawable( R.drawable.ic_twotone_default))//设置StepsViewIndicator DefaultIcon
            .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention));//设置StepsViewIndicator AttentionIcon


       /* val mVerticalStepViewFragment = VerticalStepViewFrowardFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, mVerticalStepViewFragment)
            .commit()*/


        verticalStepView.setOnClickListener {
           // Toast.makeText(applicationContext, "Clicked it" + it.id, Toast.LENGTH_SHORT).show()
            shiftLayout.visibility = View.VISIBLE

        }
    }

    //To add shifts in the array list
    fun addShifts(title: String, descrip: String){
        var shiftModel: ShiftModel = ShiftModel(title, descrip)
        arrShifts.add(shiftModel)
    }

    fun fixedShifts(){
        addShifts("Part time - 6 hrs daily", "6 pm to 12 pm")
        addShifts("Part time - Weekends", "Only friday, saturday, sunday")
        addShifts("Full time - 10 hrs daily", "Earn more")
    }

    private fun setupRecyclerVw(){
        reyclcerVwShifts.layoutManager = LinearLayoutManager(this)
        val shiftsAdapter = ShiftRecyclerVwAdapter(arrShifts, this)
        reyclcerVwShifts.adapter = shiftsAdapter
    }

    override fun selectShift(shiftModel: ShiftModel) {
        Log.d("Shifts", shiftModel.shiftTitle)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_cancel_shift_select ->{
                shiftLayout.visibility = View.GONE
            }

            R.id.btn_procced_shift_select ->{
                shiftLayout.visibility = View.GONE
            }
        }
    }
}