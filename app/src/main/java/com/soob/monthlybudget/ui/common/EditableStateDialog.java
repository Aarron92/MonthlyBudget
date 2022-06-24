package com.soob.monthlybudget.ui.common;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Dialog that maintains a state whether it's in edit mode or not
 */
public class EditableStateDialog extends Dialog
{
    /**
     * Whether dialog is being used to view or edit
     */
    private boolean isEditState;

    public EditableStateDialog(@NonNull Context context, boolean isEditState)
    {
        super(context);
        this.isEditState = isEditState;
    }

    public boolean isEditState()
    {
        return this.isEditState;
    }

    public void setIsEditState(boolean editState)
    {
        this.isEditState = editState;
    }
}
