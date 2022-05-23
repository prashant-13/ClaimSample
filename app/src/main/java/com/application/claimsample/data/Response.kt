package com.application.claimsample.data

data class Response(
    val Result: Boolean,
    val Reason: String,
    val Claims: ArrayList<Claim>
)
