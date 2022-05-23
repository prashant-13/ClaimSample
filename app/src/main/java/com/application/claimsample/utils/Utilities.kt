package com.application.claimsample.utils

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.application.claimsample.R
import com.application.claimsample.data.ClaimFieldOption
import com.application.claimsample.ui.MainActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object Utilities {


    fun getJsonString(context: Context): String? {
        val jsonString: String
        //Get Data From Text Resource File Contains Json Data.
        val inputStream = context.resources.openRawResource(R.raw.claims_json)
        val byteArrayOutputStream = ByteArrayOutputStream()
        var ctr: Int
        try {
            ctr = inputStream.read()
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr)
                ctr = inputStream.read()
            }
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        jsonString = byteArrayOutputStream.toString()
        return jsonString
    }

    fun getTextViewLayoutParams(c: Context): ViewGroup.LayoutParams? {
        val lblParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lblParams.setMargins(
            0,
            c.resources.getDimensionPixelSize(R.dimen.textview_margin_top),
            0,
            0
        )
        return lblParams
    }


    fun getSpinnerLayoutParams(c: Context): ViewGroup.LayoutParams? {
        // ,
        // height
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, c
                .resources.getDimensionPixelSize(R.dimen.spinner_layout_height)
        ) // Width
    }

    fun getEditTextLayoutParams(c: Context): ViewGroup.LayoutParams? {
        // ,
        // height
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, c
                .resources.getDimensionPixelSize(R.dimen.edittextview_layout_height)
        ) // Width
    }

    fun getSpinnerOptions(claimfieldoption: ArrayList<ClaimFieldOption>): MutableList<String> {
        val nameList = mutableListOf<String>()

        for (i in 0..claimfieldoption.size - 1)
            nameList.add(claimfieldoption.get(i).name)

        return nameList
    }

    fun getCurrentDate(): String {
        return DateFormat.format("yyyy-MM-dd", Calendar.getInstance().time).toString()
    }

    fun hideKeyboard(context: Context, view: View) {
        try {
            val `in` = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}