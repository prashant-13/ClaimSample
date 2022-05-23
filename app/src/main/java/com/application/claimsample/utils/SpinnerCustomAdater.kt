package com.application.claimsample.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.application.claimsample.R

class SpinnerCustomAdater(
    private val mContext: Context,
    resource: Int,
    private val mNameList: List<String>
) :
    ArrayAdapter<String?>(mContext, resource) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.spinner_custom_layout, parent, false)
        }
        val name = convertView.findViewById<TextView>(R.id.tv_address_name)
        val addressName = mNameList[position]
        name.text = addressName
        name.isSingleLine = false
        return convertView
    }

    override fun getCount(): Int {
        return mNameList.size
    }

    override fun getItem(position: Int): String? {
        return mNameList[position]
    }
}
