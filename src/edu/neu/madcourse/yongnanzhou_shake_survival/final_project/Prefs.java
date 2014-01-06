/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
***/
package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import edu.neu.madcourse.yongnanzhou_shake_survival.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Prefs extends PreferenceActivity {
	   // Option names and default values
	   private static final String OPT_MUSIC = "music";
	   private static final boolean OPT_MUSIC_DEF = true;

	   private static final String OPT_VIBRATION = "vibration";
	   private static final boolean OPT_VIBRATION_DEF = true;
	   
	   private static final String OPT_VOLUME = "volume";
	   private static final int OPT_VOLUME_DEF = 80;
	   private Button mBackButton = null;

	   @Override
	   protected void onPause(){
		   super.onPause();
	   }
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      addPreferencesFromResource(R.xml.preferences);
	      //setContentView(R.layout.shake_survival_preference);
	      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	      
	      ListView v = getListView();
	      final Intent i_Back = new Intent(this, ShakeSurvivalMain.class);
	      Button backButton = new Button(this);
	      backButton.setText("Shake Survival Menu");
	      backButton.setWidth(40);
	      backButton.setHeight(40);
	      backButton.setOnClickListener(new OnClickListener() {

	    	  public void onClick(View v) {
				// TODO Auto-generated method stub
	    		finish();
				startActivity(i_Back);
				} 
	    	});
	      
	      v.addFooterView(backButton);
	   }
	   /** Get the current value of the music option */
	   
	   public static boolean getMusic(Context context) {
	      return PreferenceManager.getDefaultSharedPreferences(context)
	            .getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
	   }   
	   
	   /** Get the current value of the vibration option */
	   
	   public static boolean getVibration(Context context) {
	      return PreferenceManager.getDefaultSharedPreferences(context)
	            .getBoolean(OPT_VIBRATION, OPT_VIBRATION_DEF);      
	   }   
	   
	   /** Get the current value of Game Volume */
	   
	   public static int getVolume(Context context) {
		   return PreferenceManager.getDefaultSharedPreferences(context)
				  .getInt(OPT_VOLUME, OPT_VOLUME_DEF);
	   }
	   
	   // back to Main 
	   @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				finish();
				Intent menu = new Intent(this, ShakeSurvivalMain.class);
				startActivity(menu);
		        return true;
		    } 
			return false;
		}
	   
	}
