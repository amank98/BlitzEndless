package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;

import io.socket.client.IO;
import io.socket.client.Socket;
import sun.rmi.runtime.Log;


public class MyBlitzGame extends Game {
	public static AdsController adsController;
	public static PlayServices PS;

	public MyBlitzGame(AdsController adsController, PlayServices PS){
		this.adsController = adsController;
		this.PS = PS;
	}

	public static AdsController getAds() {
		return adsController;
	}

	public static PlayServices getPS() {
		return PS;
	}

	@Override
	public void create () {
		setScreen(new com.mygdx.Blitz.StartScreen(this));
	}
}
