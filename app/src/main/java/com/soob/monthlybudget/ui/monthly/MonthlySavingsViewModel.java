package com.soob.monthlybudget.ui.monthly;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthlySavingsViewModel extends ViewModel
{
    private final MutableLiveData<String> mText;

    public MonthlySavingsViewModel()
    {
        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is the Monthly Savings Fragment");
    }

    public LiveData<String> getText()
    {
        return this.mText;
    }
}