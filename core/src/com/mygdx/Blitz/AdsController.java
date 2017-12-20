package com.mygdx.Blitz;

/**
 * Created by Aman on 1/14/2017.
 */

public interface AdsController {
    public boolean isWifiConnected();
    public void showInterstitialAd (Runnable then);
}
