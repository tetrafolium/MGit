package me.sheimi.android.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import me.sheimi.android.activities.SheimiFragmentActivity;
import me.sheimi.android.activities.SheimiFragmentActivity.OnPasswordEntered;

public class SheimiDialogFragment extends DialogFragment {

    @SuppressWarnings("NullableProblems") // It's safe to assume onAttach is called before other code.
    @NonNull
    private SheimiFragmentActivity mActivity;

    @Override
    public void onAttach(final @NonNull Context context) {
        super.onAttach(context);
        mActivity = (SheimiFragmentActivity) context;
    }

    @NonNull
    public SheimiFragmentActivity getRawActivity() {
        return mActivity;
    }

    public void showMessageDialog(final int title, final int msg, final int positiveBtn,
                                  final DialogInterface.OnClickListener positiveListener) {
        getRawActivity().showMessageDialog(title, msg, positiveBtn,
                                           positiveListener);
    }

    public void showMessageDialog(final int title, final String msg, final int positiveBtn,
                                  final DialogInterface.OnClickListener positiveListener) {
        getRawActivity().showMessageDialog(title, msg, positiveBtn,
                                           positiveListener);
    }

    public void showToastMessage(final int resId) {
        getRawActivity().showToastMessage(getString(resId));
    }

    public void showToastMessage(final String msg) {
        getRawActivity().showToastMessage(msg);
    }

    public void promptForPassword(final OnPasswordEntered onPasswordEntered,
                                  final int errorId) {
        getRawActivity().promptForPassword(onPasswordEntered, errorId);
    }

    public void promptForPassword(final OnPasswordEntered onPasswordEntered) {
        getRawActivity().promptForPassword(onPasswordEntered, null);
    }
}
