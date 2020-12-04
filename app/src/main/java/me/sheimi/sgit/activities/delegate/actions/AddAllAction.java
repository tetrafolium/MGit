package me.sheimi.sgit.activities.delegate.actions;

import me.sheimi.sgit.activities.RepoDetailActivity;
import me.sheimi.sgit.database.models.Repo;
import me.sheimi.sgit.repo.tasks.repo.AddToStageTask;

public class AddAllAction extends RepoAction {

public AddAllAction(final Repo repo, final RepoDetailActivity activity) {
	super(repo, activity);
}

@Override
public void execute() {
	AddToStageTask addTask = new AddToStageTask(mRepo, ".");
	addTask.executeTask();
	mActivity.closeOperationDrawer();
}

}
