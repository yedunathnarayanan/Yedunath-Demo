package com.demo.yedunath_demo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.yedunath_demo.R
import com.demo.yedunath_demo.domain.model.HoldingDomain
import com.demo.yedunath_demo.utils.Constants
import com.demo.yedunath_demo.utils.UIUtils
import com.demo.yedunath_demo.utils.UIUtils.setPnlColor

class HoldingsAdapter : ListAdapter<HoldingDomain, HoldingsAdapter.HoldingViewHolder>(HoldingDiffCallback()) {
    
    class HoldingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symbol: TextView = itemView.findViewById(R.id.tvStockName)
        val quantity: TextView = itemView.findViewById(R.id.tvNetQty)
        val ltp: TextView = itemView.findViewById(R.id.tvLtp)
        val pnl: TextView = itemView.findViewById(R.id.tvPnl)
        val tag: TextView = itemView.findViewById(R.id.tvTag)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holding, parent, false)
        return HoldingViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        val holding = getItem(position)
        
        holder.symbol.text = holding.symbol
        holder.quantity.text = holding.quantity.toString()
        holder.ltp.text = UIUtils.getLtpText(holder.itemView.context, holding.ltp)
        
        val pnlValue = (holding.ltp -holding.avgPrice) * holding.quantity
        holder.pnl.text = UIUtils.getPnlText(holder.itemView.context, pnlValue)
        holder.pnl.setPnlColor(holder.itemView.context, pnlValue)
    }
    
    fun updateHoldings(newHoldings: List<HoldingDomain>) {
        submitList(newHoldings)
    }
}

class HoldingDiffCallback : DiffUtil.ItemCallback<HoldingDomain>() {
    override fun areItemsTheSame(oldItem: HoldingDomain, newItem: HoldingDomain): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: HoldingDomain, newItem: HoldingDomain): Boolean {
        return oldItem == newItem
    }
}
