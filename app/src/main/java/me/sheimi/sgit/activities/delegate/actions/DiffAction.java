package me.sheimi.sgit.activities.delegate.actions;

import me.sheimi.sgit.activities.RepoDetailActivity;
import me.sheimi.sgit.database.models.Repo;

public class DiffAction extends RepoAction {

public DiffAction(final Repo repo, final RepoDetailActivity activity) {
	super(repo, activity);
}

@Override
public void execute() {
	mActivity.enterDiffActionMode();
	mActivity.closeOperationDrawer();
}
}
