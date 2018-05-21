package com.tresor.home.fragment

import com.tresor.common.model.viewmodel.SpendingModel

/**
 * Created by kris on 5/13/18. Tokopedia
 */
interface SearchInterface {

    fun onEmptySpending()

    fun renderSpending(spendingModelList: MutableList<SpendingModel>)

}