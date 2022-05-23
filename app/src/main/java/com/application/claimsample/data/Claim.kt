package com.application.claimsample.data

data class Claim(
    val Claimtype: ClaimType,
    val Claimtypedetail: ArrayList<ClaimTypeDetail>
)
