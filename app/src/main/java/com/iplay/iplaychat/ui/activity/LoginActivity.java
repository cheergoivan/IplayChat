package com.iplay.iplaychat.ui.activity;

import android.content.Context;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iplay.iplaychat.IplayChatApplication;
import com.iplay.iplaychat.R;
import com.iplay.iplaychat.entity.user.SimplifiedUserInfoDO;
import com.iplay.iplaychat.service.user.UserService;
import com.iplay.iplaychat.service.xmpp.DefaultIncomingChatMessageListener;
import com.iplay.iplaychat.service.xmpp.XMPPClient;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * A setAuthenticatedUser screen that offers setAuthenticatedUser via email/password.
 */
public class LoginActivity extends BaseActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "a","b"
    };
    /**
     * Keep track of the setAuthenticatedUser task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;

    private EditText mPasswordView;

    private ProgressWheel wheel = null;

    private UserService userService;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
        }
    }

    @Override
    public void initParams(Bundle savedInstanceState) {
        userService = new UserService();
        if(userService.hasAuthenticatedUser()){
            startActivity(ChatListActivity.class);
            finish();
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        log(Thread.currentThread().getName());
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        wheel = (ProgressWheel)findViewById(R.id.progress_wheel);

    }

    @Override
    public void setListeners() {
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }


    /**
     * Attempts to sign in or register the account specified by the setAuthenticatedUser form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual setAuthenticatedUser attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the setAuthenticatedUser attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user setAuthenticatedUser attempt.
           // showProgress(true);
            wheel.setVisibility(View.VISIBLE);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    /**
     * Represents an asynchronous setAuthenticatedUser/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String password;

        UserLoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
           // XMPPClient.initializeXMPPConnection();
            boolean authenticated = XMPPClient.login(username, password);
            log("setAuthenticatedUser result:"+authenticated);
            if(authenticated) {
                SimplifiedUserInfoDO user = new SimplifiedUserInfoDO();
                user.setUsername(username);
                userService.setAuthenticatedUser(user);
                XMPPClient.addIncomingChatMessageListener(new DefaultIncomingChatMessageListener());
                IplayChatApplication.initializeDatabase();
            }
            return authenticated;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            wheel.setVisibility(View.GONE);
            mAuthTask = null;
            if (success) {
                startActivity(ChatListActivity.class);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }
    }
}

