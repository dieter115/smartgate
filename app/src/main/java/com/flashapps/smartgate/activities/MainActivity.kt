package com.flashapps.smartgate.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.flashapps.smartgate.R
import com.flashapps.smartgate.fragments.CameraFragment
import com.flashapps.smartgate.models.Credential
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    val credentials : List<Credential> by lazy {
        realm.where<Credential>().findAll()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPagerManagement()
    }


    private fun setUpViewPagerManagement() {
        credentialPager.tag = "marketplace"
        val pagerAdapter = Pager(supportFragmentManager, credentials.size)
        credentialPager.adapter=pagerAdapter
        /*credentialPager.onPageChange { position -> }*/
    }

    inner class Pager(fm: FragmentManager, private val mTabCount: Int) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val currentCredential = credentials.get(position)
            return CameraFragment.newInstance(currentCredential.gate_id, "")
        }

        override fun getCount(): Int {
            return mTabCount;
        }

    }

}
