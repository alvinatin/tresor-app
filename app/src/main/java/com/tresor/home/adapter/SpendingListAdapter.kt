package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.viewholder.ItemAdapterViewHolder
import com.tresor.home.viewholder.SpendingAdapterDataHelper
import com.tresor.home.viewholder.SpendingListHeaderViewHolder

/**
 * Created by kris on 5/2/18. Tokopedia
 */
class SpendingListAdapter(private val spendingModels: MutableList<SpendingModel>,
                          private val listener:
                          SpendingItemListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        ItemAdapterViewHolder.HeaderListener,
        ItemAdapterViewHolder.ItemAdapterListener {

    private val spendingAdapterDataHelper = SpendingAdapterDataHelper(spendingModels)
    private val headerLayoutId = R.layout.today_header_list_adapter
    private val bodyLayoutId = R.layout.financial_list_adapter


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is SpendingListHeaderViewHolder -> holder.bindHeader(spendingModels)
            else -> (holder as ItemAdapterViewHolder).bind(
                    spendingAdapterDataHelper.listOfItems[position] as SpendingModel,
                    position,
                    this
            )
        }
    }

    override fun recalculateTotalAmount() {
        notifyItemChanged(0)
        when (spendingAdapterDataHelper.totalSpendingListSize()) {
            0 -> listener.onItemEmpty()
        }
    }

    override fun getItemCount(): Int {
        return spendingAdapterDataHelper.totalSize()
    }

    override fun getItemViewType(position: Int): Int {
        return spendingAdapterDataHelper.setViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeByPosition: Int)
            : RecyclerView.ViewHolder {
        return when (viewTypeByPosition) {
            headerLayoutId -> SpendingListHeaderViewHolder(
                    inflateItemView(parent, headerLayoutId),
                    listener)
            else -> ItemAdapterViewHolder(inflateItemView(parent, bodyLayoutId))
        }
    }

    override fun onEditProduct(adapterPosition: Int, spendingModel: SpendingModel) {
        listener.onItemClicked(adapterPosition, spendingModel)
    }

    override fun onDelete(adapterPosition: Int, spendingModel: SpendingModel) {
        listener.onRemoveButtonClicked(adapterPosition, spendingModel)
    }

    fun addNewData(spendingModel: SpendingModel) {
        spendingAdapterDataHelper.addItem(this, spendingModel)
        recalculateTotalAmount()
    }

    fun editData(adapterIndex: Int, spendingModel: SpendingModel) {
        spendingAdapterDataHelper.editItem(this, adapterIndex, spendingModel)
        recalculateTotalAmount()
    }

    fun removeData (adapterIndex: Int, spendingModel: SpendingModel) {
        spendingAdapterDataHelper.removeItem(this, adapterIndex, spendingModel)
        recalculateTotalAmount()
    }

    fun addNewPageData(additionalSpendingModels: MutableList<SpendingModel>) {
        //spendingModels.addAll(additionalSpendingModels)
    }

    private fun inflateItemView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    interface SpendingItemListener {
        fun onItemClicked(adapterPosition: Int, spendingModel: SpendingModel)
        fun onRemoveButtonClicked(adapterPosition: Int, spendingModel: SpendingModel)
        fun onItemEmpty()
        fun onHeaderClicked()
        fun loadMoreItem(listSize: Int)
    }
}