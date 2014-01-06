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

public class TimeResult extends Activity implements OnClickListener {

	private TextView speed_result_point_view;
	private TextView speed_result_shakes_view;
	private TextView speed_result_reverses_view;
	private TextView speed_result_jumps_view;
	private TextView speed_result_calorie_view;
	private TextView speed_result_killed_view;
	private TextView speed_result_bonus_view;
	private TextView speed_result_date_view;
	private TextView speed_result_new_record_view;
	
	private View speed_mode_replay_button;
	private View speed_mode_menu_button;

	private SharedPreferences savedResult;
	ShakeSurvivalMusic music = new ShakeSurvivalMusic();
	
	int points = 0;
	int shakes = 0;
	int jumps = 0;
	int reverses = 0;
	float calories = 0;
	int killed = 0;
	int bonus = 0;
	boolean speed_if_new_record = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survival_time_mode_result);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		music.play_effect(this, R.raw.gameover);
		
		speed_result_point_view = (TextView) findViewById(R.id.speed_result_points);
		speed_result_shakes_view = (TextView) findViewById(R.id.speed_result_shakes);
		speed_result_reverses_view = (TextView) findViewById(R.id.speed_result_reverses);
		speed_result_jumps_view = (TextView) findViewById(R.id.speed_result_jumps);
		speed_result_calorie_view = (TextView) findViewById(R.id.speed_result_calorie);
		speed_result_killed_view = (TextView) findViewById(R.id.speed_result_killed);
		speed_result_bonus_view = (TextView) findViewById(R.id.speed_result_bonus);
		speed_result_date_view = (TextView) findViewById(R.id.speed_result_date);
		speed_result_new_record_view = (TextView) findViewById(R.id.speed_result_new_record);
		
		speed_mode_replay_button = (Button) findViewById(R.id.speed_mode_replay_button);
		speed_mode_menu_button = (Button) findViewById(R.id.speed_mode_menu_button);
		speed_mode_replay_button.setOnClickListener(this);
		speed_mode_menu_button.setOnClickListener(this);
		
		Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
	    ((TextView) speed_mode_replay_button).setTypeface(button_font);
	    ((TextView) speed_mode_menu_button).setTypeface(button_font);
	    
		savedResult = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
		points = savedResult.getInt(TimeModeGame.POINT, 0);
		shakes = savedResult.getInt(TimeModeGame.SHAKES, 0);
		reverses = savedResult.getInt(TimeModeGame.REVERSES, 0);
		jumps = savedResult.getInt(TimeModeGame.NUMBER_OF_JUMP_SP, 0);
		killed = savedResult.getInt(TimeModeGame.NUMBER_OF_KILLED_SP, 0);
		bonus = savedResult.getInt(TimeModeGame.BONUS, 0);
		speed_if_new_record = savedResult.getBoolean(TimeModeGame.SPEED_IF_NEW_RECORD, false);
		
		String date = savedResult.getString(TimeModeGame.DATE_TIME, "default"); 
		// We find out that each shake burn about 0.05 units of calorie, 
		// each reverse equals to 0.10 units of calorie and each jump burns about 0.20 units of calorie.
		calories =  (float) (0.05 * shakes + 0.2 * jumps + reverses * 0.10);
		
		speed_result_point_view.setText(String.valueOf(points));
		speed_result_shakes_view.setText(String.valueOf(shakes));
		speed_result_reverses_view.setText(String.valueOf(reverses));
		speed_result_jumps_view.setText(String.valueOf(jumps));
		speed_result_calorie_view.setText(String.valueOf(calories));
		speed_result_killed_view.setText(String.valueOf(killed) + " (x 10)");
		speed_result_bonus_view.setText(String.valueOf(bonus) + " (x 30)");
		speed_result_date_view.setText(date);
		if(speed_if_new_record){
			music.play_bonus_effect(this, R.raw.boggle_score_music);
			speed_result_new_record_view.setTextColor(Color.rgb(255,64,64)); // red color
			speed_result_new_record_view.setText("New Record!");
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.speed_mode_replay_button:
			SharedPreferences savedData;
			savedData = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor = savedData.edit();
			editor.clear();
			editor.commit();
			finish();
			Intent replay = new Intent(this, TimeModeGame.class);
			startActivity(replay);
			break;
		case R.id.speed_mode_menu_button:
			SharedPreferences savedData2;
			savedData2 = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
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
		savedData = getSharedPreferences(SpeedModeGame.SPEED_MODE_GAME_NAME, 0);
		SharedPreferences.Editor editor = savedData.edit();
		editor.clear();
		editor.commit();
		finish();*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			SharedPreferences savedData;
			savedData = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor = savedData.edit();
			editor.clear();
			editor.commit();
			finish();
			Intent menu = new Intent(this, ShakeSurvivalMain.class);
			startActivity(menu);
	        return true;
	    } 
		return false;
	}
}
