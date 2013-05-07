package com.droidschools.facebookshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.droidschools.facebookshare.ShareONFaceBook.FacebookResponseListener;

public class MainActivity extends Activity implements FacebookResponseListener {
	Button facebookPost;
	ShareONFaceBook share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		facebookPost = (Button) findViewById(R.id.postbutton);
		facebookPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (share == null)
					share = new ShareONFaceBook(MainActivity.this, MainActivity.this, "");
				share.postToWall(createBundle());

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onFaceBookResponse() {
 // Invoked on any facebook response. Not used at present
	}

	private Bundle createBundle() {
		Bundle shareBundle = new Bundle();
		shareBundle.putString("message", "This is the share message");
		shareBundle.putString("caption", "captione here");
		shareBundle.putString("link", "https://developers.facebook.com/android/");
		shareBundle.putString("picture", "https://fbcdn-dragon-a.akamaihd.net/hphotos-ak-prn1/851580_187622688054722_2099964565_n.png");
		shareBundle.putString("description", "This is description text");
		return shareBundle;
	}

}
