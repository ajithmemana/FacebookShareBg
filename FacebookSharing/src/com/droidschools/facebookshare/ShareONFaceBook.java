package com.droidschools.facebookshare;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class ShareONFaceBook {
	private Facebook facebook;
	
	//test apps appid
	private static final String APP_ID = "395360250550683";
	private static final String[] PERMISSIONS = new String[] { "publish_stream" };
	Context context;
	Bundle bundle;
	private FacebookResponseListener listener;
//Constructot
	public ShareONFaceBook(Context context, FacebookResponseListener listener, String action) {
		this.context = context;
		this.listener = listener;
		facebook = new Facebook(APP_ID);
	}
// Invoked on any facebook response. Not used at present
	public interface FacebookResponseListener {
		public void onFaceBookResponse();
	}
// Called from activity
	public void postToWall( Bundle bundle) {
		this.bundle = bundle;
		if (!facebook.isSessionValid()) {
			loginAndUpdateWall();
		} else {
			updateWall();
		}
	}
// If not logged in first login and call update from loginlistener
	public void loginAndUpdateWall() {
		facebook.authorize((Activity) context, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
	}

//Post the bundle to wall
	public void updateWall() {

		try {
			facebook.request("me");
			String response = facebook.request("me/feed", bundle, "POST");
			Log.d("Tests", "got response: " + response);
			if (response == null || response.equals("") || response.equals("false")) {
				showToast("Blank response.");
			} else {
				listener.onFaceBookResponse();
				showToast("Message posted to your facebook wall!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//Invoked on Login
	class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {

			Thread t = new Thread() {
				public void run() {

					updateWall();
				}

			};
			t.start();

		}

		public void onFacebookError(FacebookError error) {
			showToast("Authentication with Facebook failed!");
			listener.onFaceBookResponse();

		}

		public void onError(DialogError error) {
			showToast("Authentication with Facebook failed!");
			listener.onFaceBookResponse();
		}

		public void onCancel() {
			showToast("Authentication with Facebook cancelled!");
		}

	}
// Util
	private void showToast(final String message) {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
