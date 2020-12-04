package me.sheimi.sgit.activities.delegate.actions;

import me.sheimi.sgit.activities.RepoDetailActivity;
import me.sheimi.sgit.database.models.Repo;

public abstract class RepoAction {

    protected Repo mRepo;
    protected RepoDetailActivity mActivity;

    public RepoAction(final Repo repo, final RepoDetailActivity activity) {
        mRepo = repo;
        mActivity = activity;
    }

    public abstract void execute();
}
