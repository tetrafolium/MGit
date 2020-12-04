package me.sheimi.sgit.repo.tasks.repo;

import android.content.Context;
import me.sheimi.android.utils.Profile;
import me.sheimi.sgit.R;
import me.sheimi.sgit.SGitApplication;
import me.sheimi.sgit.database.models.Repo;
import me.sheimi.sgit.exception.StopTaskException;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.StoredConfig;

public class CommitChangesTask extends RepoOpTask {

  private AsyncTaskPostCallback mCallback;
  private String mCommitMsg;
  private String mAuthorName;
  private String mAuthorEmail;
  private boolean mIsAmend;
  private boolean mStageAll;

  public CommitChangesTask(final Repo repo, final String commitMsg,
                           final boolean isAmend, final boolean stageAll,
                           final String authorName, final String authorEmail,
                           final AsyncTaskPostCallback callback) {
    super(repo);
    mCallback = callback;
    mCommitMsg = commitMsg;
    mIsAmend = isAmend;
    mStageAll = stageAll;
    mAuthorName = authorName;
    mAuthorEmail = authorEmail;
    setSuccessMsg(R.string.success_commit);
  }

  @Override
  protected Boolean doInBackground(final Void... params) {
    return commit();
  }

  protected void onPostExecute(final Boolean isSuccess) {
    super.onPostExecute(isSuccess);
    if (mCallback != null) {
      mCallback.onPostExecute(isSuccess);
    }
  }

  public boolean commit() {
    try {
      commit(mRepo, mStageAll, mIsAmend, mCommitMsg, mAuthorName, mAuthorEmail);
    } catch (StopTaskException e) {
      return false;
    } catch (GitAPIException e) {
      setException(e);
      return false;
    } catch (Throwable e) {
      setException(e);
      return false;
    }
    mRepo.updateLatestCommitInfo();
    return true;
  }

  public static void commit(final Repo repo, final boolean stageAll,
                            final boolean isAmend, final String msg,
                            final String authorName, final String authorEmail)
      throws Exception, NoHeadException, NoMessageException,
             UnmergedPathsException, ConcurrentRefUpdateException,
             WrongRepositoryStateException, GitAPIException, StopTaskException {
    Context context = SGitApplication.getContext();
    StoredConfig config = repo.getGit().getRepository().getConfig();
    String committerEmail = config.getString("user", null, "email");
    String committerName = config.getString("user", null, "name");

    if (committerName == null || committerName.equals("")) {
      committerName = Profile.getUsername(context);
    }
    if (committerEmail == null || committerEmail.equals("")) {
      committerEmail = Profile.getEmail(context);
    }
    if (committerName.isEmpty() || committerEmail.isEmpty()) {
      throw new Exception("Please set your name and email");
    }
    if (msg.isEmpty()) {
      throw new Exception("Please include a commit message");
    }
    CommitCommand cc = repo.getGit()
                           .commit()
                           .setCommitter(committerName, committerEmail)
                           .setAll(stageAll)
                           .setAmend(isAmend)
                           .setMessage(msg);
    if (authorName != null && authorEmail != null) {
      cc.setAuthor(authorName, authorEmail);
    }
    cc.call();
    repo.updateLatestCommitInfo();
  }
}
