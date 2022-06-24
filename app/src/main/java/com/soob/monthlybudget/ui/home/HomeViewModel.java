package com.soob.monthlybudget.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel
{
    private final MutableLiveData<String> mText;

    public HomeViewModel()
    {
        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is the Home Fragment");
    }

    public LiveData<String> getText()
    {
        return this.mText;
    }
}