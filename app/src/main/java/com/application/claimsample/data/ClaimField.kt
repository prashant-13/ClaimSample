package com.application.claimsample.data

data class ClaimField(
    val id: String,
    val name: String,
    val label: String,
    val type: String,
    val required: String,
    val isdependant: String,
    val created: String,
    val modified: String,
    val Claimfieldoption: ArrayList<ClaimFieldOption>
)