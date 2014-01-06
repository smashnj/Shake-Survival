/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
***/
package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import android.content.Context;
import android.media.MediaPlayer;

public class ShakeSurvivalMusic {
	   private static MediaPlayer mp = null;
	   private static MediaPlayer mp2 = null;
	   private static MediaPlayer mp3 = null;
	   private static MediaPlayer mp4 = null;
	   private static MediaPlayer mp5 = null;
	   private static MediaPlayer mp6 = null;
	   private static MediaPlayer mp7 = null;
	   
	   /** Stop old song and start new one */
	   
	   public static void play(Context context, int resource) {
	      stop(context);      	
	      // Start music only if not disabled in preferences
	       if (Prefs.getMusic(context)) {
	         int volume = Prefs.getVolume(context);
	         mp = MediaPlayer.create(context, resource);
	         mp.setLooping(true);
//	         mp.setVolume(volume/100, volume/100);
	         mp.start();
	       }
	   }
	   
	   public static void play_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		      mp2 = MediaPlayer.create(context, resource);
		      mp2.start();
		   }
	   }
	   
	   public static void play_countdown_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		   	  mp3 = MediaPlayer.create(context, resource);
		      mp3.start();
		   }
	   }
	   
	   public static void play_tick_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		      mp6 = MediaPlayer.create(context, resource);
		      mp6.start();
		   }
	   }
	   
	   public static void play_bonus_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		      mp4 = MediaPlayer.create(context, resource);
		      mp4.start();
		   }
	   }
	   
	   public static void play_bomb_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		      mp5 = MediaPlayer.create(context, resource);
		      mp5.start();
		   }
	   }
	   
	   public static void play_wall_crash_effect(Context context, int resource) {
		   if (Prefs.getMusic(context)){
		      mp7 = MediaPlayer.create(context, resource);
		      mp7.start();
		   }
	   }
	   
	   /** Stop the music */
	   public static void stop(Context context) { 
	      if (mp != null) {
	         mp.stop();
	         // After release(), the object is no longer available.
	         mp.release();
	         mp = null;
	      }
	   }
	   
	   
	   public static void stop_tick(Context context) { 
		      if (mp6 != null) {
		         mp6.stop();
		         // After release(), the object is no longer available.
		         mp6.release();
		         mp6 = null;
		      }
	   }
	}
