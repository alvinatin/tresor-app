package com.tresor.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tresor.R;
import com.tresor.common.TresorActivity;
import com.tresor.common.model.viewmodel.SpendingModel;
import com.tresor.home.inteface.HomeActivityListener;
import com.tresor.home.inteface.NewDataAddedListener;
import com.tresor.statistic.dialog.TimePickerDialogFragment;



/**
 * Created by kris on 5/27/17. Tokopedia
 */
class HomeActivity : TresorActivity(), HomeActivityListener, NewDataAddedListener, TimePickerDialogFragment.DatePickerListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSelectedMenu(R.id.add_menu)
    }


    override fun onDataAdded(newData: SpendingModel) {
        setSelectedMenu(R.id.add_menu)
    }

    override fun onDateSelected(mode: Int, year: Int, month: Int, dayOfMonth: Int) {
        //setSelectedMenu(R.id.statistic_menu);
    }

}

