package com.application.claimsample.data

data class ClaimRequest(
    val claimType: ClaimType,
    val claimDetailList: ArrayList<ClaimDetail>,
    val expense: String,
    val claimDate: String
) {
}