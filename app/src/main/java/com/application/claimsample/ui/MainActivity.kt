package com.application.claimsample.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.claimsample.R
import com.application.claimsample.data.*
import com.application.claimsample.utils.Utilities
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var allViewInstance = ArrayList<View>()
    val claimTypeList = mutableListOf<String>()
    lateinit var selectedClaim: Claim
    val claimRequestList = mutableListOf<ClaimRequest>()
    val list = MutableLiveData<List<ClaimRequest>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        initListener()
    }

    private fun initListener() {
        btAddClaim.setOnClickListener {
            validateData(selectedClaim.Claimtypedetail)
        }

        list.observe(this, Observer { list ->
            rvClaims.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = ClaimRequestAdapter(list)
            }
        })
    }


    private fun initUi() {
        val responseJson = Utilities.getJsonString(this)
        val response = Gson().fromJson(responseJson.toString(), Response::class.java)

        val claims = response?.Claims
        if (claims!!.size > 0) {
            for (claim in claims) {
                claimTypeList.add(claim.Claimtype.name)
            }
            spClaimType.adapter =
                ArrayAdapter<String>(this, R.layout.dropdown_textview, claimTypeList)
            spClaimType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedClaim = claims[position]
                    initClaimUi(selectedClaim.Claimtypedetail)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        tvDate.text = Utilities.getCurrentDate()
    }

    private fun initClaimUi(claimTypeList: ArrayList<ClaimTypeDetail>) {
        layoutClaim.removeAllViews()
        allViewInstance.clear()
        edExpense.setText("")
        for (claimTypeDetail in claimTypeList) {
            when {
                claimTypeDetail.Claimfield.type.equals("DropDown") -> createSpinnerOption(
                    claimTypeDetail.Claimfield
                )
                claimTypeDetail.Claimfield.type.equals("SingleLineTextAllCaps") -> createSingleLineText(
                    claimTypeDetail.Claimfield,
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                )
                claimTypeDetail.Claimfield.type.equals("SingleLineText") -> createSingleLineText(
                    claimTypeDetail.Claimfield,
                    InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                )
                claimTypeDetail.Claimfield.type.equals("SingleLineTextNumeric") -> createSingleLineText(
                    claimTypeDetail.Claimfield,
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                )
            }
        }
    }

    private fun createSingleLineText(
        claimfield: ClaimField,
        typeTextFlagCapCharacters: Int
    ) {
        val tvLableValue = EditText(this)
        tvLableValue.inputType = typeTextFlagCapCharacters
        tvLableValue.textSize = 15f
        tvLableValue.setTextColor(resources.getColor(R.color.black))
        tvLableValue.layoutParams = Utilities.getEditTextLayoutParams(this)
        tvLableValue.hint = claimfield.label
        tvLableValue.tag = claimfield
        allViewInstance.add(tvLableValue)
        layoutClaim.addView(tvLableValue)
    }

    private fun createSpinnerOption(claimfield: ClaimField) {
        val tvLable = TextView(this)
        tvLable.textSize = 14f
        tvLable.layoutParams = Utilities.getTextViewLayoutParams(this)
        if (claimfield.required.equals("1"))
            tvLable.text = "${claimfield.label}*"
        else
            tvLable.text = claimfield.label
        layoutClaim.addView(tvLable)

        val SpinnerOptions = Utilities.getSpinnerOptions(claimfield.Claimfieldoption)
        var spinnerArrayAdapter = ArrayAdapter(this, R.layout.dropdown_textview, SpinnerOptions)
        val spinner = Spinner(this)
        spinner.adapter = spinnerArrayAdapter
        spinner.layoutParams = Utilities.getSpinnerLayoutParams(this)
        spinner.setBackgroundResource(R.drawable.edit_text_bottom_line_grey)
        allViewInstance.add(spinner)
        layoutClaim.addView(spinner)
    }


    private fun validateData(claimTypeDetailList: ArrayList<ClaimTypeDetail>) {

        var isValid = true
        val claimDetailsList = mutableListOf<ClaimDetail>()
        for (i in 0 until claimTypeDetailList.size) {
            val claimTypeDetail = claimTypeDetailList.get(i)
            var inputText: String = ""
            if (claimTypeDetail.Claimfield.type.equals("DropDown")) {
                val spinner = allViewInstance[i] as Spinner
                if (spinner.selectedItem != null) {
                    inputText = spinner.selectedItem.toString()
                } else {
                    if (claimTypeDetail.Claimfield.required.equals("1")) {
                        val builder = AlertDialog.Builder(this)
                            .setMessage("Please select ${claimTypeDetail.Claimfield.label}")
                            .setPositiveButton("OK") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                        builder.show()
                        isValid = false
                        break
                    }
                }
            } else if (claimTypeDetail.Claimfield.type.equals("SingleLineTextAllCaps")
                || claimTypeDetail.Claimfield.type.equals("SingleLineText")
                || claimTypeDetail.Claimfield.type.equals("SingleLineTextNumeric")
            ) {
                val editText = allViewInstance[i] as EditText
                if (claimTypeDetail.Claimfield.required.equals("1") && editText.text.isNullOrEmpty()) {
                    val builder = AlertDialog.Builder(this)
                        .setMessage("${claimTypeDetail.Claimfield.label} can not be left blank")
                        .setPositiveButton("OK") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                    builder.show()
                    isValid = false
                    break
                }
                if (!editText.text.isNullOrEmpty()) {
                    inputText = editText.text.toString()
                }
            }

            if (inputText.isNullOrEmpty()) {
                val claimDetail = ClaimDetail(
                    claimTypeDetail.claimtype_id,
                    claimTypeDetail.claimfield_id,
                    claimTypeDetail.id,
                    claimTypeDetail.Claimfield.type,
                    claimTypeDetail.Claimfield.label,
                    inputText
                )
                claimDetailsList.add(claimDetail)
            }

        }

        if (isValid) {
            claimRequestList.add(
                ClaimRequest(
                    selectedClaim.Claimtype,
                    claimDetailsList as ArrayList<ClaimDetail>,
                    edExpense.text.toString(),
                    tvDate.text.toString()
                )
            )
            list.value = claimRequestList
            Utilities.hideKeyboard(this, nestedScrollview)
            if (spClaimType.selectedItemPosition == 0)
                initClaimUi(selectedClaim.Claimtypedetail)
            else
                spClaimType.setSelection(0)
        }

    }
}