package com.tresor.home.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.adapter.FilterAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.activity.addPaymentActivityIntent
import com.tresor.home.activity.editPaymentActivityIntent
import com.tresor.home.adapter.EmptyDailyListAdapter
import com.tresor.home.adapter.SpendingListAdapter
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.inteface.HomeActivityListener.*
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.presenter.TodaySpendingPresenter
import com.tresor.home.viewholder.EmptyDailyListViewHolder
import com.tresor.home.viewholder.SpendingListItemViewHolder

import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.fragment_list_financial_history.*

/**
 * Created by kris on 5/27/17. Tokopedia
 */

class TodaySpendingFragment :
        Fragment(),
        TodaySpendingInterface,
        SpendingListItemViewHolder.SpendingItemListener,
        EmptyDailyListViewHolder.EmptyDailyListListener,
        FilterAdapter.onFilterItemClicked {

    private val presenter = TodaySpendingPresenter(this)

    override fun onEmptySpending() {
        onItemEmpty()
    }

    override fun renderSpending(spendingModelList: MutableList<SpendingModel>) {
        list_financial_history.adapter = SpendingListAdapter(spendingModelList, this)
    }
    private val emptyAdapter = EmptyDailyListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list_financial_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAutoCompleteView()
        setSpendingList()
    }

    override fun onItemClicked(position: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(position, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun onAddFirstSpending() {
        startActivityForResult(activity.addPaymentActivityIntent(), ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(), ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun onItemEmpty() {
        list_financial_history.adapter = emptyAdapter
    }

    private fun setSpendingList() {
        list_financial_history.layoutManager = LinearLayoutManager(activity)
        presenter.fetchSpendingList()
    }

    private fun setAutoCompleteView() {
        val filterAdapter = FilterAdapter(this)
        setFilterResultView(filterAdapter)
        setAutoSuggestionEditText(filterAdapter)
    }

    private fun setFilterResultView(filterAdapter: FilterAdapter) {
        filter_recycler_view.adapter = filterAdapter
        filter_recycler_view.layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.HORIZONTAL
        )
    }

    private fun setAutoSuggestionEditText(filterAdapter: FilterAdapter) {
        val arrayAdapter = AutoCompleteSuggestionAdapter(activity)
        auto_complete_filter.setAdapter(arrayAdapter)
        setDropDownResultAction(filterAdapter, arrayAdapter)
    }

    private fun setDropDownResultAction(filterAdapter: FilterAdapter,
                                        arrayAdapter: AutoCompleteSuggestionAdapter) {
        val hashTagSuggestions = mutableListOf<String>()
        auto_complete_filter.initComponent(
                CompositeDisposable(),
                presenter.autoCompletePresenterListener(hashTagSuggestions, arrayAdapter))
        auto_complete_filter.setOnItemClickListener { _, _, position, _ ->
            filterItemClicked(hashTagSuggestions, filterAdapter, position)
        }
    }

    private fun onDataAdded(newData: SpendingModel) {
        presenter.addNewSpending(newData)
        /*spendingListAdapter
                .notifyItemInserted(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);
        spendingListAdapter
                .notifyItemRangeInserted(
                        FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER,
                        spendingList.size() + FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER
                );
        financialHistoryList.scrollToPosition(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);*/
    }

    private fun onDataEdited(alteredData: SpendingModelWrapper) {
        presenter.editNewSpending(alteredData.position, alteredData.spendingModel)
    }

    private fun filterItemClicked(autoCompleteHashTagList: List<String>,
                                  filterAdapter: FilterAdapter,
                                  position: Int) {
        filterAdapter.addNewHashTag(autoCompleteHashTagList[position])
        presenter.updateFilter(autoCompleteHashTagList)
        auto_complete_filter.setText("")
        auto_complete_filter.requestFocus()
    }

    override fun onFilterItemRemoved(hashTagList: List<String>) {
        presenter.updateFilter(hashTagList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == ADD_NEW_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataAdded(
                        (data!!.getParcelableExtra<Parcelable>(EXTRA_ADD_DATA_RESULT)
                                as SpendingModelWrapper
                                ).spendingModel)

            requestCode == EDIT_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataEdited(data!!.getParcelableExtra<Parcelable>(EXTRA_ADD_DATA_RESULT)
                        as SpendingModelWrapper)
        }
    }

    companion object {

        fun createFragment(): TodaySpendingFragment {
            return TodaySpendingFragment()
        }
    }
}