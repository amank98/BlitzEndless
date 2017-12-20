package com.mygdx.Blitz;

/**
 * Created by Aman on 4/5/2017.
 */

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievementOne();     //Finish instructions
    public void unlockAchievementTwo();     //Complete first game
    public void unlockAchievementThree();   //25 points
    public void unlockAchievementFour();    //Unlock first upgrade
    public void unlockAchievementFive();    //Unlock first color
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
