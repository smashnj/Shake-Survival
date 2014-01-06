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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeSurvivalMain extends Activity implements OnClickListener {
	private static final String TAG = "ShakeSurvival"; 
	public static final String SHAKE_SURVIVAL_MENU = "shake_survival_menu";
	public static final String IF_FIRST_TIME_COME = "if_first_time_come";
	
	protected static Boolean survival_resume_flag = false;
	protected static Boolean speed_resume_flag = false;
	ShakeSurvivalMusic music = new ShakeSurvivalMusic();
	Boolean record_flag = false;
	Boolean about_flag = false;
	Boolean options_flag = false;
	Boolean turtorial_flag = false;
	protected static Boolean if_first_time_come = true;
	
	private SharedPreferences survivalResult;
	private SharedPreferences speedResult;
	String survival_date = "";
	String speed_date = "";
	
   /** Called when the activity is first created. */
   @SuppressLint("NewApi")
@Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.survival_main);
      
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      music.play(this, R.raw.game_menu);
      
      Typeface title_font = Typeface.createFromAsset(getAssets(), "Capture_it.ttf");  
      Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");  
      
      // using custom font
      TextView survival_main_title_view = (TextView) findViewById(R.id.survival_main_title);
      survival_main_title_view.setTypeface(title_font);  
      
      View survivalResumeButton = findViewById(R.id.survival_survival_resume_button);
      survivalResumeButton.setOnClickListener(this);
      View speedResumeButton = findViewById(R.id.survival_speed_resume_button);
      speedResumeButton.setOnClickListener(this);
      
      ((TextView) survivalResumeButton).setTypeface(button_font);  
      ((TextView) speedResumeButton).setTypeface(button_font);  

      survivalResumeButton.getBackground().setAlpha(200);
      speedResumeButton.getBackground().setAlpha(200);
      
      View survivalNewButton = findViewById(R.id.survival_survival_new_button);
      survivalNewButton.setOnClickListener(this);
      View speedNewButton = findViewById(R.id.survival_speed_new_button);
      speedNewButton.setOnClickListener(this);
      
      ((TextView) survivalNewButton).setTypeface(button_font); 
      ((TextView) speedNewButton).setTypeface(button_font);  
      
      survivalNewButton.getBackground().setAlpha(200);
      speedNewButton.getBackground().setAlpha(200);
      
      View rankingButton = findViewById(R.id.survival_highest_button);
      rankingButton.setOnClickListener(this);
      View optionsButton = findViewById(R.id.survival_options_button);
      optionsButton.setOnClickListener(this);
      ((TextView) rankingButton).setTypeface(button_font); 
      ((TextView) optionsButton).setTypeface(button_font); 
      
      rankingButton.getBackground().setAlpha(200);
      speedNewButton.getBackground().setAlpha(200);
      
      View aboutButton = findViewById(R.id.survival_about_button);
      aboutButton.setOnClickListener(this);
      View acknowledgementsButton = findViewById(R.id.survival_acknowledgements_button);
      acknowledgementsButton.setOnClickListener(this);
      ((TextView) aboutButton).setTypeface(button_font); 
      ((TextView) acknowledgementsButton).setTypeface(button_font); 
      
      aboutButton.getBackground().setAlpha(200);
      acknowledgementsButton.getBackground().setAlpha(200);
      
      View exitButton = findViewById(R.id.survival_exit_button);
      exitButton.setOnClickListener(this);
      ((TextView) exitButton).setTypeface(button_font); 
      
      exitButton.getBackground().setAlpha(200);
      
      View tutorialButton = findViewById(R.id.survival_turtorial_button);
      tutorialButton.setOnClickListener(this);
      ((TextView) tutorialButton).setTypeface(button_font); 
      
      tutorialButton.getBackground().setAlpha(200);
      
      SharedPreferences getSavedData;
      SharedPreferences getSavedData2;
      getSavedData = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
      survival_resume_flag = getSavedData.getBoolean(SurvivalModeGame.SURVIVAL_RESUME_FLAG, false);
      getSavedData2 = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
      speed_resume_flag = getSavedData2.getBoolean(TimeModeGame.SPEED_RESUME_FLAG, false);
      
      if (survival_resume_flag){ 
    	  survivalResumeButton.setVisibility(View.VISIBLE);
      }
      else {
    	  survivalResumeButton.setVisibility(View.GONE);
      }
      
      if (speed_resume_flag){ 
    	  speedResumeButton.setVisibility(View.VISIBLE);
      }
      else {
    	  speedResumeButton.setVisibility(View.GONE);
      }
      
      /*SharedPreferences savedTutorialData;
      savedTutorialData = getSharedPreferences(ShakeSurvivalTutorial.SHAKE_SURVIVAL_TUTORIAL, 0);
      if_first_time_come = savedTutorialData.getBoolean(ShakeSurvivalTutorial.IF_FIRST_TIME_COME, true);*/
      
      if(if_first_time_come){
    	 // SharedPreferences.Editor editor = savedTutorialData.edit();
		  //editor.putBoolean(IF_FIRST_TIME_COME, false);
		  //editor.commit();
    	  if_first_time_come = false;
    	  finish();
    	  Intent i11 = new Intent(this, ShakeSurvivalTutorial.class);
          startActivity(i11);
      }
      
      // hide the Highest Record if there is no records 
      survivalResult = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_RECORD, 0);
	  speedResult = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_RECORD, 0);
	  survival_date = survivalResult.getString(SurvivalModeGame.DATE_TIME, "");
	  speed_date = speedResult.getString(TimeModeGame.DATE_TIME, "");
	  if(survival_date.equals("") && speed_date.equals("")){
		  rankingButton.setVisibility(View.GONE);
	  }
	  else{
		  rankingButton.setVisibility(View.VISIBLE);
	  }
   }

   @Override
   protected void onResume() {
      super.onResume();
      //ShakeSurvivalMusic.play(this, R.raw.boggle_bg_music);
      
      SharedPreferences getSavedData;
      SharedPreferences getSavedData2;
      getSavedData = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
      survival_resume_flag = getSavedData.getBoolean(SurvivalModeGame.SURVIVAL_RESUME_FLAG, false);
      getSavedData2 = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
      speed_resume_flag = getSavedData2.getBoolean(TimeModeGame.SPEED_RESUME_FLAG, false);
      
      View survivalResumeButton = findViewById(R.id.survival_survival_resume_button);
      View speedResumeButton = findViewById(R.id.survival_speed_resume_button);
      if (survival_resume_flag){ 
    	  survivalResumeButton.setVisibility(View.VISIBLE);
      }
      else {
    	  survivalResumeButton.setVisibility(View.GONE);
      }
      
      if (speed_resume_flag){ 
    	  speedResumeButton.setVisibility(View.VISIBLE);
      }
      else {
    	  speedResumeButton.setVisibility(View.GONE);
      }
      music.play(this, R.raw.game_menu);
      
      if(if_first_time_come){
    	  if_first_time_come = false;
    	  finish();
    	  Intent i11 = new Intent(this, ShakeSurvivalTutorial.class);
          startActivity(i11);
      }
   }

   @Override
   protected void onPause() {
      super.onPause();
      // do not stop music in about and record, options
      if(!about_flag && !record_flag && !options_flag && !turtorial_flag){
    	  music.stop(this);  
      }
      //ShakeSurvivalMusic.stop(this);
   }

   protected String[] wordsArray = new String[100];
   
   public void onClick(View v) {
      switch (v.getId()) {
      case R.id.survival_survival_resume_button:
    	  finish();
    	  startGame_Survival(SurvivalModeGame.CONTINUE);
          break;
      case R.id.survival_speed_resume_button:
    	  finish();
          startGame_Speed(TimeModeGame.CONTINUE);
          break;
      case R.id.survival_survival_new_button:
    	  finish();
    	  Intent i3 = new Intent(this, SurvivalModeGame.class);
          startActivity(i3);
          break;
      case R.id.survival_speed_new_button:
    	  finish();
    	  Intent i4 = new Intent(this, TimeModeGame.class);
          startActivity(i4);
          break;
      case R.id.survival_highest_button:
    	  record_flag = true;
    	  finish();
    	  Intent i5 = new Intent(this, HighestRecord.class);
    	  startActivity(i5);
          break;
      case R.id.survival_options_button:
    	  options_flag = true;
    	  finish();
    	  Intent i6 = new Intent(this, Prefs.class);
    	  startActivity(i6);
         break;//
      case R.id.survival_about_button:
    	  about_flag = true;
    	  finish();
          Intent i2 = new Intent(this, ShakeSurvivalAbout.class);
          startActivity(i2);
          break;
      case R.id.survival_turtorial_button:
    	  turtorial_flag = true;
    	  finish();
    	  Intent i11 = new Intent(this, ShakeSurvivalTutorial.class);
          startActivity(i11);
          break;
      case R.id.survival_acknowledgements_button:
    	  //finish();
          //Intent i2 = new Intent(this, BoggleAcknowledgements.class);
          //startActivity(i2);
          break;
      case R.id.survival_exit_button:
    	  finish();
          break;
      }
   }
   
   private Toast toast; 
   public void toastShow(String message) { 
	   if (toast == null) { 
		   toast = Toast.makeText(this, message, Toast.LENGTH_LONG); 
	   } 
	   else { 
		   		toast.cancel(); 
		   		toast.setText(message); 
		   	} 
	   toast.show(); 
   }
   
   private void startGame_Survival(int i){
      Log.d(TAG, "clicked on " + i);
      Intent intent = new Intent(this, SurvivalModeGame.class);
      intent.putExtra(SurvivalModeGame.GAME_STATUS, i);
      startActivity(intent);      
   }
   
   private void startGame_Speed(int i){
	      Log.d(TAG, "clicked on " + i);
	      Intent intent = new Intent(this, TimeModeGame.class);
	      intent.putExtra(TimeModeGame.GAME_STATUS_SP, i);
	      startActivity(intent);      
   }
   
   // back to Main 
   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// clear the record data.
			/*SharedPreferences savedData;
			savedData = getSharedPreferences(SpeedModeGame.SPEED_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor = savedData.edit();
			editor.clear();
			editor.commit();
			SharedPreferences savedData2;
			savedData2 = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor2 = savedData2.edit();
			editor2.clear();
			editor2.commit();*/
			music.stop(this);
			finish();
	        return true;
	    } 
		return false;
	}
}