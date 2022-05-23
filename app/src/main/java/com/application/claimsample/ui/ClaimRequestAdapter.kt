package com.application.claimsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.claimsample.R
import com.application.claimsample.data.ClaimRequest
import com.application.claimsample.databinding.RecyclerviewClaimDetailsBinding

class ClaimRequestAdapter(
    private val list: List<ClaimRequest>
) : RecyclerView.Adapter<ClaimRequestAdapter.ClaimRequestViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ClaimRequestViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_claim_details,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ClaimRequestViewHolder, position: Int) {
        holder.claimDetailsBinding.claimRequest = list[position]
        holder.claimDetailsBinding.tvViewMore.setOnClickListener {
        }
    }


    inner class ClaimRequestViewHolder(
        val claimDetailsBinding: RecyclerviewClaimDetailsBinding
    ) : RecyclerView.ViewHolder(claimDetailsBinding.root)

}