package me.sheimi.sgit.activities.delegate.actions;

import android.content.DialogInterface;
import me.sheimi.sgit.R;
import me.sheimi.sgit.activities.RepoDetailActivity;
import me.sheimi.sgit.database.models.Repo;

public class DeleteAction extends RepoAction {

  public DeleteAction(final Repo repo, final RepoDetailActivity activity) {
    super(repo, activity);
  }

  @Override
  public void execute() {
    mActivity.showMessageDialog(
        R.string.dialog_delete_repo_title, R.string.dialog_delete_repo_msg,
        R.string.label_delete, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(final DialogInterface dialogInterface,
                              final int i) {
            mRepo.deleteRepo();
            mActivity.finish();
          }
        });
    mActivity.closeOperationDrawer();
  }
}
