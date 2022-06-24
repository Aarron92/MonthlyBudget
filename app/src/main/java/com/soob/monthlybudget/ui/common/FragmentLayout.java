package com.soob.monthlybudget.ui.common;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;

import com.soob.monthlybudget.R;

import java.util.Map;

public interface FragmentLayout
{
    default int getLayout()
    {
        return R.layout.fragment_data_screen;
    }

    default int getRecyclerView()
    {
        return R.id.fragment_recycler_view;
    }

    default int getAddButton()
    {
        return R.id.fragment_add_button;
    }

    int getContentView();

    int getTitleResource();

    int getAddTitleText();

    int getViewTitleText();

    int getEditTitleText();

    int getEditAndSaveButton();

    int getCancelButton();

    int getDeleteButton();

    int getAddButtonText();

    int getEditButtonText();

    int getSaveButtonText();

    int getCancelButtonText();

    int getAccountDropdown();

    Map<String, View> getEditableFields(Dialog dialog);
}
