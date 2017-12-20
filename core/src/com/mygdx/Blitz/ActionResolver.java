package com.mygdx.Blitz;

/**
 * Allows the use of native andriod code in LibGdx
 * so that I can use the Google Play Service code
 * Created by Aman on 3/25/2017.
 */

public interface ActionResolver {
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score, String id);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();
}
