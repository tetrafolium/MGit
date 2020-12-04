package me.sheimi.sgit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import me.sheimi.android.activities.SheimiFragmentActivity.OnBackClickListener;
import me.sheimi.sgit.R;
import me.sheimi.sgit.activities.CommitDiffActivity;
import me.sheimi.sgit.database.models.Repo;
import me.sheimi.sgit.repo.tasks.repo.StatusTask;
import me.sheimi.sgit.repo.tasks.repo.StatusTask.GetStatusCallback;

/**
 * Created by sheimi on 8/5/13.
 */
public class StatusFragment extends RepoDetailFragment {

  private Repo mRepo;
  private ProgressBar mLoadding;
  private TextView mStatus;
  private Button mUnstagedDiff;
  private Button mStagedDiff;

  public static StatusFragment newInstance(final Repo mRepo) {
    StatusFragment fragment = new StatusFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(Repo.TAG, mRepo);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public View onCreateView(final LayoutInflater inflater,
                           final ViewGroup container,
                           final Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_status, container, false);
    getRawActivity().setStatusFragment(this);

    Bundle bundle = getArguments();
    mRepo = (Repo)bundle.getSerializable(Repo.TAG);
    if (mRepo == null && savedInstanceState != null) {
      mRepo = (Repo)savedInstanceState.getSerializable(Repo.TAG);
    }
    if (mRepo == null) {
      return v;
    }
    mLoadding = (ProgressBar)v.findViewById(R.id.loading);
    mStatus = (TextView)v.findViewById(R.id.status);
    mStagedDiff = (Button)v.findViewById(R.id.button_staged_diff);
    mUnstagedDiff = (Button)v.findViewById(R.id.button_unstaged_diff);
    mStagedDiff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        showDiff("HEAD", "dircache");
      }
    });
    mUnstagedDiff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        showDiff("dircache", "filetree");
      }
    });
    reset();
    return v;
  }

  private void showDiff(final String oldCommit, final String newCommit) {
    Intent intent = new Intent(getRawActivity(), CommitDiffActivity.class);
    intent.putExtra(CommitDiffActivity.OLD_COMMIT, oldCommit);
    intent.putExtra(CommitDiffActivity.NEW_COMMIT, newCommit);
    intent.putExtra(CommitDiffActivity.SHOW_DESCRIPTION, false);
    intent.putExtra(Repo.TAG, mRepo);
    getRawActivity().startActivity(intent);
  }

  @Override
  public void reset() {
    if (mLoadding == null || mStatus == null)
      return;
    mLoadding.setVisibility(View.VISIBLE);
    mStatus.setVisibility(View.GONE);
    StatusTask task = new StatusTask(mRepo, new GetStatusCallback() {
      @Override
      public void postStatus(final String result) {
        mStatus.setText(result);
        mLoadding.setVisibility(View.GONE);
        mStatus.setVisibility(View.VISIBLE);
      }
    });
    task.executeTask();
  }

  @Override
  public OnBackClickListener getOnBackClickListener() {
    return null;
  }
}
