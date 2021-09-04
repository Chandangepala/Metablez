package com.basic.delivery_partner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.basic.delivery_partner.R
import com.basic.delivery_partner.fragments.DepositFragment
import com.basic.delivery_partner.fragments.FeedFragment
import com.basic.delivery_partner.fragments.MoreFragment
import com.basic.delivery_partner.fragments.PayoutFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.ChipNavigationBar.OnItemSelectedListener

class HomeActivity : AppCompatActivity(), OnItemSelectedListener {
    lateinit var bottomNavig: ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
    }

    //To initialize all the views
    fun initUI(){
        bottomNavig = findViewById(R.id.bottom_navig_home);
        bottomNavig.setOnItemSelectedListener(this)
        bottomNavig.setItemSelected(R.id.feed)
        loadFragment(FeedFragment.newInstance("X", "Y"), true)
    }

    //To listen item clicks on bottom navigation bar
    override fun onItemSelected(id: Int) {
        when(id){
            R.id.feed ->
                loadFragment(FeedFragment.newInstance("X", "Y"), false);
            R.id.payout ->
                loadFragment(PayoutFragment.newInstance("X", "Y"), false)
            R.id.deposit ->
                loadFragment(DepositFragment.newInstance("X", "Y"),false);
            R.id.more ->
                loadFragment(MoreFragment.newInstance("X", "Y"), false);
        }
    }

    //To load a fragment using fragment manager and fragment transaction
    fun loadFragment(fragment: Fragment, addReplace: Boolean){
        var fm: FragmentManager = supportFragmentManager
        var ft: FragmentTransaction = fm.beginTransaction()
        if(addReplace){
            ft.add(R.id.frag_container, fragment)
        }else{
            ft.replace(R.id.frag_container, fragment)
        }
        ft.commit()
    }
}