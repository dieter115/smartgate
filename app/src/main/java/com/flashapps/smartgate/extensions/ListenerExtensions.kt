package com.flashapps.smartgate.extensions

import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


/**
 * Created by dietervaesen on 25/01/18.
 */

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun ViewPager.onPageChange(pageChange: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            pageChange(position)
        }
    })

}


fun SearchView.onQueryChange(queryChange: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String): Boolean {
            //niks doen voorlopig
            return true;
        }

        override fun onQueryTextChange(query: String): Boolean {
            queryChange(query)
            return true
        }

    })
}

