package me.sheimi.sgit.activities.delegate.actions;

import me.sheimi.android.activities.SheimiFragmentActivity;
import me.sheimi.sgit.R;
import me.sheimi.sgit.activities.RepoDetailActivity;
import me.sheimi.sgit.database.models.Repo;
import me.sheimi.sgit.repo.tasks.SheimiAsyncTask;
import me.sheimi.sgit.repo.tasks.repo.CheckoutTask;

/**
 * Created by liscju - piotr.listkiewicz@gmail.com on 2015-03-15.
 */
public class NewBranchAction extends RepoAction {
  public NewBranchAction(final Repo mRepo, final RepoDetailActivity mActivity) {
    super(mRepo, mActivity);
  }

  @Override
  public void execute() {
    mActivity.showEditTextDialog(
        R.string.dialog_create_branch_title, R.string.dialog_create_branch_hint,
        R.string.label_create,
        new SheimiFragmentActivity.OnEditTextDialogClicked() {
          @Override
          public void onClicked(final String branchName) {
            CheckoutTask checkoutTask =
                new CheckoutTask(mRepo, null, branchName,
                                 new ActivityResetPostCallback(branchName));
            checkoutTask.executeTask();
          }
        });
    mActivity.closeOperationDrawer();
  }

  private class ActivityResetPostCallback
      implements SheimiAsyncTask.AsyncTaskPostCallback {
    private final String mBranchName;
    public ActivityResetPostCallback(final String branchName) {
      mBranchName = branchName;
    }
    @Override
    public void onPostExecute(final Boolean isSuccess) {
      mActivity.reset(mBranchName);
    }
  }
}
