package me.sheimi.android.utils;

/**
 * Exception in SecurePrefs processing.
 */

public class SecurePrefsException extends Exception {

  public SecurePrefsException(final String s) { super(s); }

  public SecurePrefsException(final Exception e) { super(e); }
}
