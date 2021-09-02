package com.basic.metablez.adpaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.basic.metablez.R
import java.util.*


class WalletViewPagerAdapter(var arrImages: ArrayList<Int>, var ctx: Context): PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun getCount() = arrImages.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view: View = layoutInflater.inflate(R.layout.wallet_view_pager_card,container, false)
        var eventImg: ImageView = view.findViewById(R.id.wallet_vp_img)
        eventImg.setImageResource(arrImages[position])

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView( `object`as View)
    }

}





