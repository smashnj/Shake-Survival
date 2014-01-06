package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import java.text.DecimalFormat;

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

public class HighestRecord extends Activity implements OnClickListener {
	
	private TextView survival_record_time_view;
	private TextView survival_record_shakes_view;
	private TextView survival_record_reverses_view;
	private TextView survival_record_jumps_view;
	private TextView survival_record_calorie_view;
	private TextView survival_record_killed_view;
	private TextView survival_record_bonus_view;
	private TextView survival_record_date_view;
	
	private TextView speed_record_point_view;
	private TextView speed_record_shakes_view;
	private TextView speed_record_reverses_view;
	private TextView speed_record_jumps_view;
	private TextView speed_record_calorie_view;
	private TextView speed_record_killed_view;
	private TextView speed_record_bonus_view;
	private TextView speed_record_date_view;
	
	private TextView highest_record_total_calories_view;
	private TextView highest_record_start_date_view;
	
	private View back_button;
	private View clear_button;
	
	private SharedPreferences survivalResult;
	private SharedPreferences speedResult;
	
	int survival_time = 0;
	int survival_shakes = 0;
	int survival_jumps = 0;
	int survival_reverses = 0;
	float survival_calories = 0;
	int survival_killed = 0;
	int survival_bonus = 0;
	String survival_date = "";
	
	int speed_points = 0;
	int speed_shakes = 0;
	int speed_jumps = 0;
	int speed_reverses = 0;
	float speed_calories = 0;
	int speed_killed = 0;
	int speed_bonus = 0;
	String speed_date = "";
	
	float survival_total_calories = 0;
	float speed_total_calories = 0;
	float total_calories = 0;
	String survival_start_date = "";
	String speed_start_date = "";
	String final_start_date = "";
	
	final Context context = this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survival_highest_record);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		survival_record_time_view = (TextView) findViewById(R.id.survival_record_survival_time);
		survival_record_shakes_view = (TextView) findViewById(R.id.survival_record_shakes);
		survival_record_reverses_view = (TextView) findViewById(R.id.survival_record_reverses);
		survival_record_jumps_view = (TextView) findViewById(R.id.survival_record_jumps);
		survival_record_calorie_view = (TextView) findViewById(R.id.survival_record_calorie);
		survival_record_killed_view = (TextView) findViewById(R.id.survival_record_killed);
		survival_record_bonus_view = (TextView) findViewById(R.id.survival_record_bonus);
		survival_record_date_view = (TextView) findViewById(R.id.survival_record_date);
		
		speed_record_point_view = (TextView) findViewById(R.id.speed_record_points);
		speed_record_shakes_view = (TextView) findViewById(R.id.speed_record_shakes);
		speed_record_reverses_view = (TextView) findViewById(R.id.speed_record_reverses);
		speed_record_jumps_view = (TextView) findViewById(R.id.speed_record_jumps);
		speed_record_calorie_view = (TextView) findViewById(R.id.speed_record_calorie);
		speed_record_killed_view = (TextView) findViewById(R.id.speed_record_killed);
		speed_record_bonus_view = (TextView) findViewById(R.id.speed_record_bonus);
		speed_record_date_view = (TextView) findViewById(R.id.speed_record_date);
		
		highest_record_total_calories_view = (TextView) findViewById(R.id.highest_record_total_calories);
		highest_record_start_date_view = (TextView) findViewById(R.id.highest_record_start_date);
		
		back_button = (Button) findViewById(R.id.survival_record_back_button);
		back_button.setOnClickListener(this);
		clear_button = (Button) findViewById(R.id.survival_record_clear_button);
		clear_button.setOnClickListener(this);
		
		Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
	    ((TextView) back_button).setTypeface(button_font);
	    ((TextView) clear_button).setTypeface(button_font);
	    
		survivalResult = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_RECORD, 0);
		speedResult = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_RECORD, 0);
		
		survival_time = survivalResult.getInt(SurvivalModeGame.SURVIVAL_TIME, 0);
		survival_shakes = survivalResult.getInt(SurvivalModeGame.SHAKES, 0);
		survival_jumps = survivalResult.getInt(SurvivalModeGame.NUMBER_OF_JUMP, 0);
		survival_reverses = survivalResult.getInt(SurvivalModeGame.REVERSES, 0);
		survival_killed = survivalResult.getInt(SurvivalModeGame.NUMBER_OF_KILLED, 0);
		survival_bonus = survivalResult.getInt(SurvivalModeGame.BONUS, 0);
		survival_date = survivalResult.getString(SurvivalModeGame.DATE_TIME, "");
		survival_total_calories = survivalResult.getFloat(SurvivalModeGame.SURVIVAL_TOTAL_CALORIES, 0);
		survival_start_date = survivalResult.getString(SurvivalModeGame.SURVIVAL_GAME_START_DATE, "");
		
		speed_points = speedResult.getInt(TimeModeGame.POINT, 0);
		speed_shakes = speedResult.getInt(TimeModeGame.SHAKES, 0);
		speed_jumps = speedResult.getInt(TimeModeGame.NUMBER_OF_JUMP_SP, 0);
		speed_reverses = speedResult.getInt(TimeModeGame.REVERSES, 0);
		speed_killed = speedResult.getInt(TimeModeGame.NUMBER_OF_KILLED_SP, 0);
		speed_bonus = speedResult.getInt(TimeModeGame.BONUS, 0);
		speed_date = speedResult.getString(TimeModeGame.DATE_TIME, "");
		speed_total_calories = speedResult.getFloat(TimeModeGame.SPEED_TOTAL_CALORIES, 0);
		speed_start_date = speedResult.getString(TimeModeGame.SPEED_GAME_START_DATE, "");
		
		// We find out that each shake burn about 0.05 units of calorie, 
		// each reverse equals to 0.10 units of calorie and each jump burns about 0.20 units of calorie.
		survival_calories =  (float) (0.05 * survival_shakes + 0.2 * survival_jumps + survival_reverses * 0.10);
		speed_calories =  (float) (0.05 * speed_shakes + 0.2 * speed_jumps + speed_reverses * 0.10);
		total_calories = speed_total_calories + survival_total_calories;
		
		if(!survival_start_date.equals("")){
			final_start_date =  survival_start_date;
		}
		if(!speed_start_date.equals("")){
			final_start_date =  speed_start_date;
		}
				
		survival_record_time_view.setText("Survival Time: "+String.valueOf(survival_time)+"s");
		survival_record_shakes_view.setText("Shake Times: "+String.valueOf(survival_shakes));
		survival_record_reverses_view.setText("Flip Times: "+String.valueOf(survival_reverses));
		survival_record_jumps_view.setText("Raise Times: "+String.valueOf(survival_jumps));
		survival_record_calorie_view.setText("Calories Burned: "+String.valueOf(survival_calories));
		survival_record_killed_view.setText("Invaders Killed: "+String.valueOf(survival_killed));
		survival_record_bonus_view.setText("Bonus: "+String.valueOf(survival_bonus));
		survival_record_date_view.setText("Date: "+survival_date);
		
		speed_record_point_view.setText("Points: "+String.valueOf(speed_points));
		speed_record_shakes_view.setText("Shake Times: "+String.valueOf(speed_shakes));
		speed_record_reverses_view.setText("Flip Times: "+String.valueOf(speed_reverses));
		speed_record_jumps_view.setText("Raise Times: "+String.valueOf(speed_jumps));
		speed_record_calorie_view.setText("Calories Burned: "+String.valueOf(speed_calories));
		speed_record_killed_view.setText("Invaders Killed: "+String.valueOf(speed_killed));
		speed_record_bonus_view.setText("Bonus: "+String.valueOf(speed_bonus));
		speed_record_date_view.setText("Date: "+speed_date);
		DecimalFormat dec = new DecimalFormat("###.##");
		highest_record_total_calories_view.setText("Calories burned so far: "+ dec.format(total_calories));
		if(final_start_date.equals("")){
			highest_record_start_date_view.setText("Since: Not start yet.");
		}
		else{
			highest_record_start_date_view.setText("Since: "+final_start_date);
		}
		
    clear_button.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View arg0) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			context);

		// set title
		alertDialogBuilder.setTitle("Clear Record");

		// set dialog message
		alertDialogBuilder
			.setMessage("Are you sure to clear the record?")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close current activity
					// and clear the data.
					clear_record();
					HighestRecord.this.finish();
					update_page();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	});
}
	
	private void clear_record(){
		// clear the record data.
		SharedPreferences savedData;
		savedData = getSharedPreferences(TimeModeGame.SPEED_MODE_GAME_RECORD, 0);
		SharedPreferences.Editor editor = savedData.edit();
		editor.clear();
		editor.commit();
		SharedPreferences savedData2;
		savedData2 = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_RECORD, 0);
		SharedPreferences.Editor editor2 = savedData2.edit();
		editor2.clear();
		editor2.commit();
	}
	
	public void onPause(){
		super.onPause();
	}
	
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

	private void update_page(){
		Intent update = new Intent(this, ShakeSurvivalMain.class);
		startActivity(update);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.survival_record_back_button:
			finish();
			Intent menu = new Intent(this, ShakeSurvivalMain.class);
			startActivity(menu);
			break;
		default:
			finish();
		}
	}
}
