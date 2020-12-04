package me.sheimi.sgit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import me.sheimi.android.activities.SheimiFragmentActivity;
import me.sheimi.android.activities.SheimiFragmentActivity.OnBackClickListener;

/**
 * Created by sheimi on 8/7/13.
 */
public abstract class BaseFragment extends Fragment {

  public abstract OnBackClickListener getOnBackClickListener();

  private SheimiFragmentActivity mActivity;

  public abstract void reset();

  @Override
  public void onAttach(final Context context) {
    super.onAttach(context);
    mActivity = (SheimiFragmentActivity)context;
  }

  public SheimiFragmentActivity getRawActivity() { return mActivity; }

  public void
  showMessageDialog(final int title, final int msg, final int positiveBtn,
                    final DialogInterface.OnClickListener positiveListener) {
    getRawActivity().showMessageDialog(title, msg, positiveBtn,
                                       positiveListener);
  }

  public void
  showMessageDialog(final int title, final String msg, final int positiveBtn,
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

  // public abstract void search(String query);
}
