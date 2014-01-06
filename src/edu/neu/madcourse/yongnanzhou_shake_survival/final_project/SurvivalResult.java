package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import edu.neu.madcourse.yongnanzhou_shake_survival.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.neu.mobileclass.apis.KeyValueAPI;

public class SurvivalResult extends Activity implements OnClickListener {

	private TextView survival_result_time_view;
	private TextView survival_result_shakes_view;
	private TextView survival_result_reverses_view;
	private TextView survival_result_jumps_view;
	private TextView survival_result_calorie_view;
	private TextView survival_result_killed_view;
	private TextView survival_result_bonus_view;
	private TextView survival_result_date_view;
	private TextView survival_result_new_record_view;
	
	private View survival_mode_replay_button;
	private View survival_mode_menu_button;

	private SharedPreferences savedResult;
	ShakeSurvivalMusic music = new ShakeSurvivalMusic();
	
	int survival_time = 0;
	int shakes = 0;
	int jumps = 0;
	int reverses = 0;
	float calories = 0;
	int killed = 0;
	int bonus = 0;
	String date = "";
	boolean survival_if_new_record = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survival_survival_mode_result);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		music.play_effect(this, R.raw.gameover);
		
		survival_result_time_view = (TextView) findViewById(R.id.survival_result_time);
		survival_result_shakes_view = (TextView) findViewById(R.id.survival_result_shakes);
		survival_result_reverses_view = (TextView) findViewById(R.id.survival_result_reverses);
		survival_result_jumps_view = (TextView) findViewById(R.id.survival_result_jumps);
		survival_result_calorie_view = (TextView) findViewById(R.id.survival_result_calorie);
		survival_result_killed_view = (TextView) findViewById(R.id.survival_result_killed);
		survival_result_bonus_view = (TextView) findViewById(R.id.survival_result_bonus);
		survival_result_date_view = (TextView) findViewById(R.id.survival_result_date);
		survival_result_new_record_view = (TextView) findViewById(R.id.survival_result_new_record);
		
		survival_mode_replay_button = (Button) findViewById(R.id.survival_mode_replay_button);
		survival_mode_menu_button = (Button) findViewById(R.id.survival_mode_menu_button);
		survival_mode_replay_button.setOnClickListener(this);
		survival_mode_menu_button.setOnClickListener(this);
		
		Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
	    ((TextView) survival_mode_replay_button).setTypeface(button_font);
	    ((TextView) survival_mode_menu_button).setTypeface(button_font);
	    
		savedResult = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
		survival_time = savedResult.getInt(SurvivalModeGame.SURVIVAL_TIME, 0);
		shakes = savedResult.getInt(SurvivalModeGame.SHAKES, 0);
		reverses = savedResult.getInt(SurvivalModeGame.REVERSES, 0);
		jumps = savedResult.getInt(SurvivalModeGame.NUMBER_OF_JUMP, 0);
		killed = savedResult.getInt(SurvivalModeGame.NUMBER_OF_KILLED, 0);
		bonus = savedResult.getInt(SurvivalModeGame.BONUS, 0);
		date = savedResult.getString(SurvivalModeGame.DATE_TIME, "");
		survival_if_new_record = savedResult.getBoolean(SurvivalModeGame.SURVIVAL_IF_NEW_RECORD, false);
		
		// We find out that each shake burn about 0.05 units of calorie, 
		// each reverse equals to 0.10 units of calorie and each jump burns about 0.20 units of calorie.
		calories =  (float) (0.05 * shakes + 0.2 * jumps + reverses * 0.10);
		
		survival_result_time_view.setText(String.valueOf(survival_time)+"s");
		survival_result_shakes_view.setText(String.valueOf(shakes));
		survival_result_reverses_view.setText(String.valueOf(reverses));
		survival_result_jumps_view.setText(String.valueOf(jumps));
		survival_result_calorie_view.setText(String.valueOf(calories));
		survival_result_killed_view.setText(String.valueOf(killed));
		survival_result_bonus_view.setText(String.valueOf(bonus));
		survival_result_date_view.setText(date);
		if(survival_if_new_record){
			music.play_bonus_effect(this, R.raw.boggle_score_music);
			survival_result_new_record_view.setTextColor(Color.rgb(255,64,64)); // red color
			survival_result_new_record_view.setText("New Record!");
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.survival_mode_replay_button:
			SharedPreferences savedData;
			savedData = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor = savedData.edit();
			editor.clear();
			editor.commit();
			finish();
			Intent replay = new Intent(this, SurvivalModeGame.class);
			startActivity(replay);
			break;
		case R.id.survival_mode_menu_button:
			SharedPreferences savedData2;
			savedData2 = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor2 = savedData2.edit();
			editor2.clear();
			editor2.commit();
			finish();
			Intent menu = new Intent(this, ShakeSurvivalMain.class);
			startActivity(menu);
			break;
		default:
			finish();
		}
	}
	
	public void onPause(){
		super.onPause();
		/*SharedPreferences savedData;
		savedData = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
		SharedPreferences.Editor editor = savedData.edit();
		editor.clear();
		editor.commit();
		finish();*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			SharedPreferences savedData2;
			savedData2 = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor2 = savedData2.edit();
			editor2.clear();
			editor2.commit();
			finish();
			Intent menu = new Intent(this, ShakeSurvivalMain.class);
			startActivity(menu);
	        return true;
	    } 
		return false;
	}
}
