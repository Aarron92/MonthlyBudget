package com.soob.monthlybudget.ui.monthly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.soob.monthlybudget.databinding.FragmentMonthlySavingsBinding;

public class MonthlySavingsFragment extends Fragment
{
    private FragmentMonthlySavingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        MonthlySavingsViewModel outgoingViewModel = new ViewModelProvider(this).get(MonthlySavingsViewModel.class);

        this.binding = FragmentMonthlySavingsBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        final TextView textView = this.binding.fragmentMonthlySavingsText;
        outgoingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.binding = null;
    }
}