package com.mygdx.Blitz.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.mygdx.Blitz.AdsController;
import com.mygdx.Blitz.PlayServices;
import com.mygdx.Blitz.R;


public class AndroidLauncher extends AndroidApplication implements AdsController, PlayServices {//, ActionResolver, GameHelper.GameHelperListener {
	private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-9538941258621682/7553754652";
	InterstitialAd interstitialAd;

	private GameHelper gameHelper;
	private final static int requestCode = 1;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		setupAds();
		initialize(new com.mygdx.Blitz.MyBlitzGame(this, this), config);
	}

	public void setupAds() {
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
		//AdRequest request = new AdRequest.Builder().build();
		//interstitialAd.loadAd(request);
		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.addTestDevice("EA618BA8004960F81F37514AFF60F0FF").build();
		interstitialAd.loadAd(ad);
	}

	@Override
	public void showInterstitialAd (final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (then != null) {
					interstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder();
							//AdRequest ad = builder.build();
							AdRequest ad = builder.addTestDevice("EA618BA8004960F81F37514AFF60F0FF").build();
							interstitialAd.loadAd(ad);
						}
					});
				}
				interstitialAd.show();
			}
		});
	}

	@Override
	public boolean isWifiConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo next = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (ni != null && (ni.isConnected() || next.isConnected()) );
	}
//--------------- FROM LAST IMPLEMENT (PlayServices) -----------------------

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		String str = "https://play.google.com/store/apps/details?id=com.mygdx.Blitz";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievementOne()
	{
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_one));
	}

	@Override
	public void unlockAchievementTwo()
	{
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_two));
	}

	@Override
	public void unlockAchievementThree()
	{
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_three));
	}

	@Override
	public void unlockAchievementFour()
	{
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_four));
	}

	@Override
	public void unlockAchievementFive()
	{
		Games.Achievements.unlock(gameHelper.getApiClient(),
				getString(R.string.achievement_five));
	}

	@Override
	public void submitScore(int highScore)
	{
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highest), highScore);
		}
	}

	@Override
	public void showAchievement()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
}

	@Override
	public void showScore()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highest)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

}
