package me.sheimi.sgit.repo.tasks.repo;

import me.sheimi.sgit.database.models.Repo;
import me.sheimi.sgit.exception.StopTaskException;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;

public class CheckoutTask extends RepoOpTask {

private AsyncTaskPostCallback mCallback;
private String mCommitName;
private String mBranch;

public CheckoutTask(final Repo repo, final String name, final String branch, final AsyncTaskPostCallback callback) {
	super(repo);
	mCallback = callback;
	mCommitName = name;
	mBranch = branch;
}

@Override
protected Boolean doInBackground(final Void... params) {
	return checkout(mCommitName, mBranch);
}

protected void onPostExecute(final Boolean isSuccess) {
	super.onPostExecute(isSuccess);
	if (mCallback != null) {
		mCallback.onPostExecute(isSuccess);
	}
}

public boolean checkout(final String name, final String newBranch) {
	try {
		if (name == null) {
			checkoutNewBranch(newBranch);
		} else {
			if (Repo.COMMIT_TYPE_REMOTE == Repo.getCommitType(name)) {
				checkoutFromRemote(name, newBranch == null || newBranch.equals("") ? Repo.getCommitName(name) : newBranch);
			} else if (newBranch == null || newBranch.equals("")) {
				checkoutFromLocal(name);
			} else {
				checkoutFromLocal(name, newBranch);
			}
		}
	} catch (StopTaskException e) {
		return false;
	} catch (GitAPIException e) {
		setException(mException);
		return false;
	} catch (JGitInternalException e) {
		setException(mException);
		return false;
	} catch (Throwable e) {
		setException(mException);
		return false;
	}
	mRepo.updateLatestCommitInfo();
	return true;
}

public void checkoutNewBranch(final String name) throws GitAPIException,
JGitInternalException, StopTaskException {
	mRepo.getGit().checkout().setName(name).setCreateBranch(true).call();
}

public void checkoutFromLocal(final String name) throws GitAPIException,
JGitInternalException, StopTaskException {
	mRepo.getGit().checkout().setName(name).call();
}

public void checkoutFromLocal(final String name, final String branch) throws GitAPIException,
JGitInternalException, StopTaskException {
	mRepo.getGit().checkout().setCreateBranch(true).setName(branch)
	.setStartPoint(name).call();
}

public void checkoutFromRemote(final String remoteBranchName, final String branchName)
throws GitAPIException, JGitInternalException, StopTaskException {
	mRepo.getGit().checkout().setCreateBranch(true).setName(branchName)
	.setStartPoint(remoteBranchName).call();
	mRepo.getGit()
	.branchCreate()
	.setUpstreamMode(
		CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
	.setStartPoint(remoteBranchName).setName(branchName)
	.setForce(true).call();
}
}
