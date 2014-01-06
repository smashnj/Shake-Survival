package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import edu.neu.madcourse.yongnanzhou_shake_survival.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TimeModeGame extends Activity implements OnClickListener, SensorEventListener{

	private final String TAG = "Time Mode";
	public static final String SPEED_MODE_GAME_NAME = "time_mode_game_name";
	public static final String SPEED_MODE_GAME_RECORD = "time_mode_game_record";
	public static final String GAME_STATUS_SP = "game_status";
	protected static final int CONTINUE = 1;
	public static final int PLAY = 0;
	
	public static final String SURVIVAL_TIME_SP = "seconds_so_far";
	public static final String ENERGY_SP = "energy";
	public static final String TIME_COUNTER_SP = "time_counter";
	public static final String SECONDS_FOR_NEW_WAVE_SP = "m_seconds_for_new_wave";
	public static final String INVADER_MOVING_SPEED_SP = "invader_moving_speed";
	public static final String TIME_COUNTER_BONUS_1_SP = "time_counter_bonus_1";
	public static final String TIME_COUNTER_BONUS_2_SP = "time_counter_bonus_2";
	public static final String TIME_COUNTER_BONUS_3_SP = "time_counter_bonus_3";
	public static final String NUMBER_OF_TAP_SP = "number_of_tap";
	public static final String NUMBER_OF_JUMP_SP = "number_of_jump";
	public static final String NUMBER_OF_KILLED_SP = "number_of_killed";
	public static final String NOT_ENOUGH_ENERGY_FIRE_SP = "not_enough_energy_fire";
	public static final String NOT_ENOUGH_ENERGY_JUMP_SP = "not_enough_energy_jump";
	public static final String REMAINING_TIME_SP = "remaining_time";
	public static final String POINT = "point";
	public static final String SPEED_RESUME_FLAG = "speed_resume_flag";
	public static final String SHAKES = "shakes";
	public static final String REVERSES = "reverses";
	public static final String BONUS = "number_of_bonus";
	public static final String DATE_TIME = "date_time";
	public static final String CANNON_X_POS = "cannon_x_pos";
	public static final String SPEED_TOTAL_CALORIES = "speed_total_calories";
	public static final String SPEED_IF_NEW_RECORD = "speed_if_new_record";
	public static final String SPEED_GAME_START_DATE = "speed_game_start_date";
	public static final String WALL_HP = "wall_hp";
	public static final String IF_INVADER_ALERT = "if_invader_alert";
	
	public static final String Y_POS_OF_INVADER_IN_COLUMN_1 = "y_pos_of_invader_in_column_1";
	public static final String Y_POS_OF_INVADER_IN_COLUMN_2 = "y_pos_of_invader_in_column_2";
	public static final String Y_POS_OF_INVADER_IN_COLUMN_3 = "y_pos_of_invader_in_column_3";
	public static final String Y_POS_OF_INVADER_IN_COLUMN_4 = "y_pos_of_invader_in_column_4";
	public static final String Y_POS_OF_INVADER_IN_COLUMN_5 = "y_pos_of_invader_in_column_5";
	
	public static final String NUMBER_OF_INVADERS_IN_COLUMN_1 = "number_of_invaders_in_column_1";
	public static final String NUMBER_OF_INVADERS_IN_COLUMN_2 = "number_of_invaders_in_column_2";
	public static final String NUMBER_OF_INVADERS_IN_COLUMN_3 = "number_of_invaders_in_column_3";
	public static final String NUMBER_OF_INVADERS_IN_COLUMN_4 = "number_of_invaders_in_column_4";
	public static final String NUMBER_OF_INVADERS_IN_COLUMN_5 = "number_of_invaders_in_column_5";
	
	static final int TOTAL_TIME = 60;
	
	private TimeModeView speedModeView;
	private int SMOKE_1_LAST_TIME = 500;
	private int SMOKE_2_LAST_TIME = 1000;
	private int BONUS_LAST_TIME = 3000;
	
	int time_counter = 0;
	int time_counter2 = 0;// used to draw invaders animation
	int time_counter_fire_effect_1 = 0;
	int time_counter_fire_effect_2 = 0;
	int time_counter_fire_effect_3 = 0;
	int time_counter_fire_effect_4 = 0;
	int time_counter_fire_effect_5 = 0;
	int time_counter_big_bang = 0;
	int time_counter_wall_effect = 0;
	int time_counter_energy_sign = 0;
	
	int seconds_so_far = 0;
	int m_seconds_for_new_wave = 5000; // ms
	int wave_interval = 5; // Seconds
	// how many pixels invaders move each time
	int invader_moving_speed = 5;
	// time interval for updating the invaders moving speed
	int time_interval_for_update_speed = 1000;

	int initial_y_pos = 0;
	
	int[] y_pos_of_invader_in_column_1 = {10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000};
	int[] y_pos_of_invader_in_column_2 = {10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000};
	int[] y_pos_of_invader_in_column_3 = {10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000};
	int[] y_pos_of_invader_in_column_4 = {10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000};
	int[] y_pos_of_invader_in_column_5 = {10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000};
	
	int number_of_invaders_in_column_1 = 1;
	int number_of_invaders_in_column_2 = 1;
	int number_of_invaders_in_column_3 = 1;
	int number_of_invaders_in_column_4 = 1;
	int number_of_invaders_in_column_5 = 1;
	// y pos of bottom line
	int bottom_line = 100; 
	int alert_line = 100; 
	int time_counter_bonus_1 = 0;
	int time_counter_bonus_2 = 0;
	int time_counter_bonus_3 = 0;
	int time_counter_bonus_star = 0;
	int time_counter_war_crash = 0;
	
	int number_of_tap = 0;
	int number_of_jump = 0;
	int number_of_killed = 0;
	Boolean not_enough_energy_fire = false;
	Boolean not_enough_energy_jump = false;
	
	Boolean game_over_flag = false;
	int energy = 50;
	
    protected int remaining_time = 60;
    int point = 0;
    int shakes = 0;
    int reverses = 0;
    int number_of_bonus = 0;
    Boolean onPause_flag = false;
    String date_time = "";
    SharedPreferences savedData, savedData2, savedData3;
    protected float cannon_x_pos = 0;  
    float speed_total_calories = 0;
    Boolean speed_if_new_record = false;
    String speed_game_start_date = "";
    Boolean if_ready_time_is_over = false;
    Boolean if_ready_time_go_over = false;
    Boolean if_ready_time_3_over = false;
    Boolean if_ready_time_2_over = false;
    Boolean if_ready_time_1_over = false;
    int ready_go_time_counter = 0; 
    int ufo_status = 1;
    int defend_wall_line = 100;
    int wall_hp = 20;
    Boolean if_invader_alert = false;
    Boolean if_shaking = false;
    
	/**For the sensor part*/
	public SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mLinearAccel;
	private WakeLock mWakeLock;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    public Display mDisplay;
    static final float ALPHA = 0.2f;
    protected float[] accelVals;
    protected float[] linearAccelVals;
    public boolean screenPressed = false;
	int curPos = 0;
	long sTimestamp1 = 0;
	long sTimestamp2 = 0;
    private float x, y, z;
	private float last_x = 10 , last_y = 10 , last_z = 10, last_z_linear = 0;
    private long lastUpdate = -1;
    private static final int SHAKE_THRESHOLD = 400;
    int flippingCounts = 0;
    
    // size of screen
    int width = 0;
	int height = 0;
    
	ShakeSurvivalMusic alert_player = new ShakeSurvivalMusic();
	ShakeSurvivalMusic count_player = new ShakeSurvivalMusic();
	ShakeSurvivalMusic shaking_player = new ShakeSurvivalMusic();
	ShakeSurvivalMusic shaking_player_2 = new ShakeSurvivalMusic();
	int play_tick_times = 0; 
	int play_alert_times = 0;
	// Handlers to handle cannon motion
	
	Handler cannonMotionStart = null;
	Handler cannonStop = null;
	public static final int CANNONSTART = 0x000001;
	public static final int CANNONSTOP = 0x000002;
	
	protected void vibrate(int x){ 
	   // Get instance of Vibrator from current Context
	   Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
   	   // Vibrate for x milliseconds
   	   v.vibrate(x);
	}
	
	// when the first line of invaders are all killed, gives a sound 
	public void play_sound_effect() {
		ShakeSurvivalMusic.play_effect(this, R.raw.boggle_score);
	}
	
	public void play_flip_effect() {
		ShakeSurvivalMusic.play_effect(this, R.raw.flip_sound);
	}
	
	public void play_readygo_sound_effect() {
		ShakeSurvivalMusic.play_effect(this, R.raw.readygo);
	}
	
	public void play_countdown_sound_effect(int second) {
		if(second == 11){
			count_player.play_countdown_effect(this, R.raw.countdown_10);//10s
		}
		if(second == 10){
			count_player.play_countdown_effect(this, R.raw.countdown_9);
		}
		if(second == 9){
			count_player.play_countdown_effect(this, R.raw.countdown_8);
		}
		if(second == 8){
			count_player.play_countdown_effect(this, R.raw.countdown_7);
		}
		if(second == 7){
			count_player.play_countdown_effect(this, R.raw.countdown_6);
		}
		if(second == 6){
			count_player.play_countdown_effect(this, R.raw.countdown_5);
		}
		if(second == 5){
			count_player.play_countdown_effect(this, R.raw.countdown_4);
		}
		if(second == 4){
			count_player.play_countdown_effect(this, R.raw.countdown_3);
		}
		if(second == 3){
			count_player.play_countdown_effect(this, R.raw.countdown_2);
		}
		if(second == 2){
			count_player.play_countdown_effect(this, R.raw.countdown_1);
		}
	}
	
	public void play_tick_sound_effect() {
		count_player.play_tick_effect(this, R.raw.tick);
	}
	
	public void stop_tick_sound_effect() {
		count_player.stop_tick(this);// just stop tick.mp3
	}
	
	public void play_bonus_sound_effect() {
		ShakeSurvivalMusic.play_bonus_effect(this, R.raw.bonus);
	}
	
	public void play_bomb_sound_effect() {
		ShakeSurvivalMusic.play_bomb_effect(this, R.raw.bomb_sound);
	}
	
	public void play_no_energy_sound_effect() {
		ShakeSurvivalMusic.play_effect(this, R.raw.no_energy_alert); 
	}
	
	public void play_alert_sound_effect() {
		alert_player.play(this, R.raw.invader_alert); // loop
	}
	
	public void stop_alert_sound_effect() {
		alert_player.stop(this);
	}
	
	public void play_wall_crash_sound_effect() {
		ShakeSurvivalMusic.play_wall_crash_effect(this, R.raw.wall_crash);
	}
	
	@SuppressLint("NewApi")
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		/*        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);    			
		*/
		
		Log.d(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();

		width = point.x;
		height = point.y;
		invader_moving_speed = height/80; // 5 is the initial speed of invader in the screen with the height 400.
		//initial_y_pos = 7 * (height / 5 - (height/20) - 20);
		initial_y_pos = 7 * (height / 5 - (height/20) - (height/20)) + (height/11);
		//bottom_line = height / 5 - (height / 20) - 20;
		bottom_line = height / 5 - (height / 20) - (height / 20);
		alert_line = (int) (height/2.057); // height = 432, alert_line = 210
		defend_wall_line = (int) (height/2.571); // defend_wall_line = 168
		
		// Get the service from internal sensor manager
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLinearAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());
        
		speedModeView = new TimeModeView(this);
	    setContentView(speedModeView);
	    speedModeView.requestFocus();

	    // Handlers to handle cannon motion
		cannonMotionStart = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if (msg.what == CANNONSTART){
					speedModeView.startSimulation();
				}	
				
				if (msg.what == CANNONSTOP){
					speedModeView.stopSimulation();
				}
				super.handleMessage(msg);
			}
		};	    
		int game_status = getIntent().getIntExtra(GAME_STATUS_SP, PLAY);
		if(game_status == 1){ // CONTINUE
			initilize_data_continue();
		}
		else { // new game
			time_counter = 0;
			for(int i = 0; i < 10; i++){
				y_pos_of_invader_in_column_1[i] = initial_y_pos;
				y_pos_of_invader_in_column_2[i] = initial_y_pos;
				y_pos_of_invader_in_column_3[i] = initial_y_pos;
				y_pos_of_invader_in_column_4[i] = initial_y_pos;
				y_pos_of_invader_in_column_5[i] = initial_y_pos;
			}
		}
		// If the activity is restarted, do a continue next time
		getIntent().putExtra(GAME_STATUS_SP, CONTINUE);
	}
	
	// getting data from SharedPreference
	private void initilize_data_continue(){
		SharedPreferences getSavedData;
		getSavedData = getSharedPreferences(SPEED_MODE_GAME_NAME, 0);
		seconds_so_far = getSavedData.getInt(SURVIVAL_TIME_SP, 0) - 1; // resume has 1s delay
		energy = getSavedData.getInt(ENERGY_SP, 0);
		time_counter = getSavedData.getInt(TIME_COUNTER_SP, 0) - 1000;
		m_seconds_for_new_wave = getSavedData.getInt(SECONDS_FOR_NEW_WAVE_SP, 0);
		invader_moving_speed = getSavedData.getInt(INVADER_MOVING_SPEED_SP, 0);
		time_counter_bonus_1 = getSavedData.getInt(TIME_COUNTER_BONUS_1_SP, 0);
		time_counter_bonus_2 = getSavedData.getInt(TIME_COUNTER_BONUS_2_SP, 0);
		time_counter_bonus_3 = getSavedData.getInt(TIME_COUNTER_BONUS_3_SP, 0);
		number_of_tap = getSavedData.getInt(NUMBER_OF_TAP_SP, 0);
		number_of_jump = getSavedData.getInt(NUMBER_OF_JUMP_SP, 0);
		number_of_killed = getSavedData.getInt(NUMBER_OF_KILLED_SP, 0);
		not_enough_energy_fire = getSavedData.getBoolean(NOT_ENOUGH_ENERGY_FIRE_SP, false);
		not_enough_energy_jump = getSavedData.getBoolean(NOT_ENOUGH_ENERGY_JUMP_SP, false);
		remaining_time = getSavedData.getInt(REMAINING_TIME_SP, 60000);
		point = getSavedData.getInt(POINT, 0);
		shakes = getSavedData.getInt(SHAKES, 0);
		reverses = getSavedData.getInt(REVERSES, 0);
		number_of_bonus = getSavedData.getInt(BONUS, 0);
		cannon_x_pos = getSavedData.getFloat(CANNON_X_POS, 0);
		wall_hp = getSavedData.getInt(WALL_HP, 0);
		if_invader_alert = getSavedData.getBoolean(IF_INVADER_ALERT, false);
		
		y_pos_of_invader_in_column_1 = formIntArray(getSavedData.getString(Y_POS_OF_INVADER_IN_COLUMN_1, "0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"));
		y_pos_of_invader_in_column_2 = formIntArray(getSavedData.getString(Y_POS_OF_INVADER_IN_COLUMN_2, "0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"));
		y_pos_of_invader_in_column_3 = formIntArray(getSavedData.getString(Y_POS_OF_INVADER_IN_COLUMN_3, "0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"));
		y_pos_of_invader_in_column_4 = formIntArray(getSavedData.getString(Y_POS_OF_INVADER_IN_COLUMN_4, "0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"));
		y_pos_of_invader_in_column_5 = formIntArray(getSavedData.getString(Y_POS_OF_INVADER_IN_COLUMN_5, "0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"));
		
		number_of_invaders_in_column_1 = getSavedData.getInt(NUMBER_OF_INVADERS_IN_COLUMN_1, 1);
		number_of_invaders_in_column_2 = getSavedData.getInt(NUMBER_OF_INVADERS_IN_COLUMN_2, 1);
		number_of_invaders_in_column_3 = getSavedData.getInt(NUMBER_OF_INVADERS_IN_COLUMN_3, 1);
		number_of_invaders_in_column_4 = getSavedData.getInt(NUMBER_OF_INVADERS_IN_COLUMN_4, 1);
		number_of_invaders_in_column_5 = getSavedData.getInt(NUMBER_OF_INVADERS_IN_COLUMN_5, 1);
	}
	
	// Given a String, returns an Array that includes each char in the string.
	// It is used for getting the y pos of invaders from a String.
	protected int[] formIntArray(String Saved_Y_Pos_String) {
		  int[] Saved_Y_Pos_Array = {0 ,0, 0, 0, 0, 0, 0, 0, 0, 0};
		  int j = 0;
		  String temp_number = "";
		  
	      for (int i = 0; i < Saved_Y_Pos_String.length(); i++) {
	    	  if(!Saved_Y_Pos_String.substring(i,i+1).equals(" ")){
	    		  temp_number = temp_number + Saved_Y_Pos_String.substring(i,i+1); // a y pos form an invader
	    	  }
	    	  else{// put he y pos in the array
	    		  if(j > 9){ // prevent index out of array
	    			  j = 9;
	    		  }
	    		  Saved_Y_Pos_Array[j] = Integer.parseInt(temp_number);
	    		  j++;
	    		  // clear temp_number
	    		  temp_number = "";
	    	  }  
	      }
	      return Saved_Y_Pos_Array;
	}
	
	// Given a String array, returns a String that contains all the elements.
	// It is used for saving the y pos of invaders from an Array
    protected String formString(int Y_Pos_Array[]) {
	      String Saved_Y_Pos_String = "";
	      for (int i = 0; i < Y_Pos_Array.length; i++) {
	    	  Saved_Y_Pos_String = Saved_Y_Pos_String + Y_Pos_Array[i] + " ";
	      }
	      return Saved_Y_Pos_String;
	}
	   
    public void startSimulation() {
        /*
         * It is not necessary to get accelerometer events at a very high
         * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
         * automatic low-pass filter, which "extracts" the gravity component
         * of the acceleration. As an added benefit, we use less power and
         * CPU resources.
         */
//        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mLinearAccel, SensorManager.SENSOR_DELAY_UI);
    }
	
	@Override
	protected void onStart() {
	  super.onStart();
	  Thread update_invader_interval = new Thread(new Runnable() {
	   public void run() {
	    for(int i = 0; i < 1000000; i++)
	    {
	     try {
	    	 if(ready_go_time_counter == 0){// add "3" image
	    		 if_ready_time_3_over = true;
	    	 }
	    	 if(ready_go_time_counter == 1000){// add "2" image, delete "3" image
	    		 if_ready_time_3_over = false;
	    		 if_ready_time_2_over = true;	
	    	 }
	    	 if(ready_go_time_counter == 2000){// add "1" image, delete "2" image
	    		 if_ready_time_2_over = false;
	    		 if_ready_time_1_over = true;
	    		 play_readygo_sound_effect();
	    	 }
	    	 if(ready_go_time_counter == 3000){// add "go" image, delete "1" image
	    		 if_ready_time_1_over = false;
	    		 if_ready_time_go_over = true;
	    		 if_ready_time_is_over = true;
	    	 }
	    	 if(ready_go_time_counter == 4000){// delete "go" image
	    		 if_ready_time_go_over = false;
	    		 speedModeView.startSimulation();
	    		 startSimulation();
	    		 
	    	 }
	    	 ready_go_time_counter = ready_go_time_counter + 1000;
	    	 
	    	 if(if_ready_time_is_over){
		    	 if(!onPause_flag){
			    	if(!game_over_flag){
			    		// update y_pos of invaders in each column
			    		if(number_of_invaders_in_column_1 > 0){
			    			for(int n = 0; n < number_of_invaders_in_column_1; n++){
			    				y_pos_of_invader_in_column_1[n] = y_pos_of_invader_in_column_1[n] - invader_moving_speed;
			    			}
			    		}
			    		if(number_of_invaders_in_column_2 > 0){
			    			for(int n = 0; n < number_of_invaders_in_column_2; n++){
			    				y_pos_of_invader_in_column_2[n] = y_pos_of_invader_in_column_2[n] - invader_moving_speed;
			    			}
			    		}
			    		if(number_of_invaders_in_column_3 > 0){
			    			for(int n = 0; n < number_of_invaders_in_column_3; n++){
			    				y_pos_of_invader_in_column_3[n] = y_pos_of_invader_in_column_3[n] - invader_moving_speed;
			    			}
			    		}
			    		if(number_of_invaders_in_column_4 > 0){
			    			for(int n = 0; n < number_of_invaders_in_column_4; n++){
			    				y_pos_of_invader_in_column_4[n] = y_pos_of_invader_in_column_4[n] - invader_moving_speed;
			    			}
			    		}
			    		if(number_of_invaders_in_column_5 > 0){
			    			for(int n = 0; n < number_of_invaders_in_column_5; n++){
			    				y_pos_of_invader_in_column_5[n] = y_pos_of_invader_in_column_5[n] - invader_moving_speed;
			    			}
			    		}
			    		
			    		}
			    	else{
			    		break;
			    	} // if(!onPause_flag)
			    	
			    	// count down sound effect. It's just for speed mode.
			    	if(remaining_time == 1){
			    		play_countdown_sound_effect(1);
		    		}
			    	if(remaining_time == 2){
			    		play_countdown_sound_effect(2);
		    		}
			    	if(remaining_time == 3){
			    		play_countdown_sound_effect(3);
		    		}
			    	if(remaining_time == 4){
			    		play_countdown_sound_effect(4);
		    		}
			    	if(remaining_time == 5){
			    		play_countdown_sound_effect(5);
		    		}
			    	if(remaining_time == 6){
			    		play_countdown_sound_effect(6);
		    		}
			    	if(remaining_time == 7){
			    		play_countdown_sound_effect(7);
		    		}
			    	if(remaining_time == 8){
			    		play_countdown_sound_effect(8);
			    	}
			    	if(remaining_time == 9){
			    		play_countdown_sound_effect(9);
			    	}
			    	if(remaining_time == 10){
			    		play_countdown_sound_effect(10);
			    	}
			    	
			    	if(remaining_time < 11){
			    		if(play_tick_times == 0){
			    			play_tick_sound_effect();
			    			play_tick_times++;
			    		}
			    	}
			    	
			    	// when the time is over.
			    	if(remaining_time == 0){
		    			game_over_flag = true;
		    			goResult();
		    			break;
		    		}
			    	
			    	if(!game_over_flag){// if game is over, stop updating.
			    		time_counter = time_counter + 1000;
			    	 	seconds_so_far = time_counter / 1000;
			    	 	remaining_time = TOTAL_TIME - seconds_so_far;
		    	 	}
			    	
		    	 	// add new wave every 5 s, but may be faster. 2s is the limit 
		    	    if(!game_over_flag && time_counter > 0 && time_counter % m_seconds_for_new_wave == 0){
		    	    	// add invaders in each column
		    	    	if(number_of_invaders_in_column_1 < 10){
		    	    		number_of_invaders_in_column_1++;
		    	    	}
		    	    	if(number_of_invaders_in_column_2 < 10){
		    	    		number_of_invaders_in_column_2++;
		    	    	}
		    	    	if(number_of_invaders_in_column_3 < 10){
		    	    		number_of_invaders_in_column_3++;
		    	    	}
		    	    	if(number_of_invaders_in_column_4 < 10){
		    	    		number_of_invaders_in_column_4++;
		    	    	}
		    	    	if(number_of_invaders_in_column_5 < 10){
		    	    		number_of_invaders_in_column_5++;
		    	    	}
		    	    }
		    	    
		    	    // add the speed for moving the invaders every 5 s.
		    	    // add the interval for adding new wave of invaders every 30 s.
		    	    if(!game_over_flag && time_counter > 0 && time_counter % 5000 == 0){
		    	    	if(m_seconds_for_new_wave > 2000){// the limit is 2 seconds
		    	    		m_seconds_for_new_wave = m_seconds_for_new_wave - 1000;
		    	    		wave_interval = m_seconds_for_new_wave / 1000;
		    	    		//"Invaders come faster!"
		    	    	}
		    	    	
		    	    	if(invader_moving_speed < height/16){ //25 is the limit in screen with height is 400
		    	    		invader_moving_speed = invader_moving_speed + height/80; //5
		    	    		speedModeView.level++;
		    	    		//"Invaders move faster!"
		    	    	}
		    	    }
	
		    	    // add bonus every 5 s, and it lasts 3 s.
		    	    if(!game_over_flag && time_counter > 0 && time_counter % 5000 == 0){
		    	    	Random generator = new Random();
		    	    	int randomIndex = generator.nextInt(3);
		    	    	if (randomIndex == 0){// 10 wall hp
		    	    		speedModeView.if_bonus_10_wall_hp = true;
		    	    	}
		    	    	else if (randomIndex == 1){// 20 energy
		    	    		speedModeView.if_bonus_20_energy = true;
		    	    	}
		    	    	else if (randomIndex == 2){// bomb if there are invaders, otherwise 10 energy 
		    	    		if(number_of_invaders_in_column_1 > 0
		    	    		|| number_of_invaders_in_column_2 > 0
		    	    		|| number_of_invaders_in_column_3 > 0
		    	    		|| number_of_invaders_in_column_4 > 0
		    	    		|| number_of_invaders_in_column_5 > 0){
		    	    			speedModeView.if_bonus_bomb = true;
		    	    		}
		    	    		else {
		    	    			speedModeView.if_bonus_10_wall_hp = true;
		    	    		}
		    	    	}
		    	    }
		    	    
		    	    
		     }// onPause_flag
	     else {// when on pause.
	    	 break;
	     }// if(!onPause_flag)
	    	 }// end of if(if_ready_time_is_over)
	    	 Thread.sleep(1000);
	     	} catch (Exception e) {
	     		Log.v("Error", e.toString());
	     	}
	    }
	   }
	  });
		  update_invader_interval.start();
	  
	  Thread frequent_check_update_thread = new Thread(new Runnable() {
		   public void run() {
		    for(int i = 0; i < 1000000; i++)
		    {
		     try {
		    	 if(if_ready_time_is_over){
		    	 if(!onPause_flag){
		    		// update the UFO status
		    		if(ufo_status == 5){
		    			ufo_status = 1;
		    		}
		    		else{
		    			ufo_status++;
		    		}
		    		
		    		// Check if the game is over
		    	 	if(((y_pos_of_invader_in_column_1[0] - invader_moving_speed <= bottom_line) &&
		    	 		(number_of_invaders_in_column_1 > 0))
		    	 	|| ((y_pos_of_invader_in_column_2[0] - invader_moving_speed <= bottom_line) &&
		    	 		(number_of_invaders_in_column_2 > 0))
		    	 	||(( y_pos_of_invader_in_column_3[0] - invader_moving_speed <= bottom_line) &&
		    	 		(number_of_invaders_in_column_3 > 0))
		    	 	|| ((y_pos_of_invader_in_column_4[0] - invader_moving_speed <= bottom_line) &&
		    	 		(number_of_invaders_in_column_4 > 0))
		    	 	|| ((y_pos_of_invader_in_column_5[0] - invader_moving_speed <= bottom_line) && 
		    	 		(number_of_invaders_in_column_5 > 0))){
		    	 		game_over_flag = true;
				        goResult();
				        break;
		    	 	}
		    	 	
		    	    // Check if the invaders has reached the alert line. 
		    	 	// So defend walls will automatically kill that invader
		    	 	if(y_pos_of_invader_in_column_1[0] + invader_moving_speed <= alert_line
		    	 	|| y_pos_of_invader_in_column_2[0] + invader_moving_speed <= alert_line
		    	 	|| y_pos_of_invader_in_column_3[0] + invader_moving_speed <= alert_line
		    	 	|| y_pos_of_invader_in_column_4[0] + invader_moving_speed <= alert_line
		    	 	|| y_pos_of_invader_in_column_5[0] + invader_moving_speed <= alert_line){
		    	 		if(play_alert_times == 0){
		    	 			play_alert_sound_effect();
		    	 			if_invader_alert = true;
		    	 		}
		    	 		play_alert_times++;
		    	 		
		    	 		if(wall_hp > 0){
		    	 			if(wall_hp == 0){ // the wall is crashed
			    	 			speedModeView.if_wall_crashed = true;
			    	 		}
		    	 			// check wall hp in this function
		    	 			speedModeView.wall_kill_one_line_invaders();
		    	 		}
				    }
		    	 	else{
		    	 		play_alert_times = 0;
		    	 		stop_alert_sound_effect();
		    	 		if_invader_alert = false;
		    	 	}
			    	
			    	if(!game_over_flag){
			    		// shut down the bomb effect after 500 ms
			    		if(speedModeView.if_bomb_effect || speedModeView.if_bomb_effect_second){
			    			time_counter_big_bang = time_counter_big_bang +50;
			    			if(time_counter_big_bang % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_1_second = false;
			    				speedModeView.if_kill_invader_2_second = false;
			    				speedModeView.if_kill_invader_3_second = false;
			    				speedModeView.if_kill_invader_4_second = false;
			    				speedModeView.if_kill_invader_5_second = false;
				    			
			    				speedModeView.if_kill_invader_1 = true;
				    			speedModeView.if_kill_invader_2 = true;
				    			speedModeView.if_kill_invader_3 = true;
				    			speedModeView.if_kill_invader_4 = true;
				    			speedModeView.if_kill_invader_5 = true;
				    			
				    			speedModeView.if_bomb_effect = false;
				    			speedModeView.if_bomb_effect_second = true;
				    		}
			    			
			    			if(time_counter_big_bang % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_1 = false;
			    				speedModeView.if_kill_invader_2 = false;
				    			speedModeView.if_kill_invader_3 = false;
				    			speedModeView.if_kill_invader_4 = false;
				    			speedModeView.if_kill_invader_5 = false;
				    			
				    			speedModeView.if_bomb_effect_second = false;
				    		}
			    		}
			    		
			    		// shut down the wall bomb effect after 500 ms
			    		if(speedModeView.if_wall_effect || speedModeView.if_wall_effect_second){
			    			time_counter_wall_effect = time_counter_wall_effect +50;
			    			if(time_counter_wall_effect % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_1_second = false;
			    				speedModeView.if_kill_invader_2_second = false;
			    				speedModeView.if_kill_invader_3_second = false;
			    				speedModeView.if_kill_invader_4_second = false;
			    				speedModeView.if_kill_invader_5_second = false;
				    			
			    				speedModeView.if_kill_invader_1 = true;
				    			speedModeView.if_kill_invader_2 = true;
				    			speedModeView.if_kill_invader_3 = true;
				    			speedModeView.if_kill_invader_4 = true;
				    			speedModeView.if_kill_invader_5 = true;
				    			
				    			speedModeView.if_wall_effect = false;
				    			speedModeView.if_wall_effect_second = true;
				    		}
			    			
			    			if(time_counter_wall_effect % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_1 = false;
			    				speedModeView.if_kill_invader_2 = false;
				    			speedModeView.if_kill_invader_3 = false;
				    			speedModeView.if_kill_invader_4 = false;
				    			speedModeView.if_kill_invader_5 = false;
				    			
				    			speedModeView.if_wall_effect_second = false;
				    		}
			    		}
			    		
			    		// shut down the wall crash effect after 1000ms
			    		if(speedModeView.if_wall_crashed){
			    			time_counter_wall_effect = time_counter_wall_effect +50;
			    			if(time_counter_wall_effect % 1000 == 0){
			    				speedModeView.if_wall_crashed = false;
			    			}
			    		}
			    		
			    		// shut down smoke_1 after 250s then open the smoke_2 effect that lasts 250s. 
			    		// shut down fire and smoke_2 effect after 500s.
			    		if(speedModeView.if_fire_effect_1){
			    			time_counter_fire_effect_1 = time_counter_fire_effect_1 +50;
			    			if(time_counter_fire_effect_1 % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_1_second = false;
				    			speedModeView.if_kill_invader_1 = true;
			    			}
			    			if(time_counter_fire_effect_1 % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_fire_effect_1 = false;
				    			speedModeView.if_kill_invader_1 = false;
			    			}
			    		}
			    		if(speedModeView.if_fire_effect_2){
			    			time_counter_fire_effect_2 = time_counter_fire_effect_2 +50;
			    			if(time_counter_fire_effect_2 % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_2_second = false;
				    			speedModeView.if_kill_invader_2 = true;
			    			}
			    			if(time_counter_fire_effect_2 % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_fire_effect_2 = false;
			    				speedModeView.if_kill_invader_2 = false;
			    			}
			    		}
			    		if(speedModeView.if_fire_effect_3){
			    			time_counter_fire_effect_3 = time_counter_fire_effect_3 +50;
			    			if(time_counter_fire_effect_3 % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_3_second = false;
			    				speedModeView.if_kill_invader_3 = true;
			    			}
			    			if(time_counter_fire_effect_3 % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_fire_effect_3 = false;
			    				speedModeView.if_kill_invader_3 = false;
			    			}
			    		}
			    		if(speedModeView.if_fire_effect_4){
			    			time_counter_fire_effect_4 = time_counter_fire_effect_4 +50;
			    			if(time_counter_fire_effect_4 % SMOKE_1_LAST_TIME == 0){
				    			speedModeView.if_kill_invader_4_second = false;
				    			speedModeView.if_kill_invader_4 = true;
			    			}
			    			if(time_counter_fire_effect_4 % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_fire_effect_4 = false;
			    				speedModeView.if_kill_invader_4 = false;
			    			}
			    		}
			    		if(speedModeView.if_fire_effect_5){
			    			time_counter_fire_effect_5 = time_counter_fire_effect_5 +50;
			    			if(time_counter_fire_effect_5 % SMOKE_1_LAST_TIME == 0){
			    				speedModeView.if_kill_invader_5_second = false;
				    			speedModeView.if_kill_invader_5 = true;
			    			}
			    			if(time_counter_fire_effect_5 % SMOKE_2_LAST_TIME == 0){
			    				speedModeView.if_fire_effect_5 = false;
			    				speedModeView.if_kill_invader_5 = false;
			    			}
			    		}
			    		
			    		// delete the bonus after 3s, and delete bonus star after 1s 
			    		if(speedModeView.if_bonus_10_wall_hp){
			    			time_counter_bonus_1 = time_counter_bonus_1 + 50;
			    			if(time_counter_bonus_1 % 3000 == 0){
			    				speedModeView.if_bonus_10_wall_hp = false;
			    				speedModeView.bonus_1_draw_times = 0;
			    			}
			    		}
			    		else if(speedModeView.if_bonus_20_energy){
							time_counter_bonus_2 = time_counter_bonus_2 + 50;
							if(time_counter_bonus_2 % 3000 == 0){
								speedModeView.if_bonus_20_energy = false;
			    				speedModeView.bonus_2_draw_times = 0;
			    			}
						}
			    		else if(speedModeView.if_bonus_bomb){
							time_counter_bonus_3 = time_counter_bonus_3 + 50;
							if(time_counter_bonus_3 % 3000 == 0){
								speedModeView.if_bonus_bomb = false;
								speedModeView.bonus_3_draw_times = 0;
			    			}
						}
			    		if(speedModeView.if_bonus_star){
			    			time_counter_bonus_star = time_counter_bonus_star + 50;
			    			if(time_counter_bonus_star % 1000 == 0){
			    				speedModeView.if_bonus_star = false;
			    			}
			    		}
			    		
			    		// shut down the energy sign after 2s
			    		if(if_shaking){
			    			time_counter_energy_sign = time_counter_energy_sign + 50;
			    			if(time_counter_energy_sign % 2000 == 0){
			    				if_shaking = false;
			    			}
			    		}
			    	}
			    	
			    	// instead increase the speed, increase the moving frequency.
		    	    /*if(!game_over_flag && time_counter > 0 && time_counter % 5000 == 0){
		    	    	if(invader_moving_speed < 20){
		    	    		invader_moving_speed = invader_moving_speed + 5;
		    	    	}
		    	    }
		    	    /*if(!game_over_flag && time_counter > 10 && time_interval_for_update_speed == 1000){
		    	    	// update the frequency between two moves
		    	    	time_interval_for_update_speed = time_interval_for_update_speed - 500;
		    	    }*/
		    	    
		    	   //Thread.sleep(time_interval_for_update_speed);
			       time_counter2++;
		    	   // Thread.sleep(50);
		    	 }
		    	 else{// when on pause
		    		 break;
		    	 }
		     	} // end of if(if_ready_time_is_over)
		    	 Thread.sleep(50);
		     } 
		     catch (Exception e) {
		     		Log.v("Error", e.toString());
		     	}
		    }
		   }
		});
	  frequent_check_update_thread.start();
	}
		
	@Override
	protected void onResume() {
		super.onResume();
	       /*
         * when the activity is resumed, we acquire a wake-lock so that the
         * screen stays on, since the user will likely not be fiddling with the
         * screen or buttons.
         */
        mWakeLock.acquire();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		onPause_flag = true;
		stop_alert_sound_effect();
		stop_tick_sound_effect();
		mSensorManager.unregisterListener(this);
		speedModeView.stopSimulation();
		save_data();
		// and release our wake-lock
        mWakeLock.release();
	}	

	protected void save_data(){
		if(!game_over_flag){
			savedData = getSharedPreferences(SPEED_MODE_GAME_NAME, 0);
			SharedPreferences.Editor editor = savedData.edit();
			editor.putBoolean(SPEED_RESUME_FLAG, ShakeSurvivalMain.speed_resume_flag);
			editor.putInt(SURVIVAL_TIME_SP, seconds_so_far);
			editor.putInt(ENERGY_SP, energy);
			editor.putInt(TIME_COUNTER_SP, time_counter);
			editor.putInt(SECONDS_FOR_NEW_WAVE_SP, m_seconds_for_new_wave);
			editor.putInt(INVADER_MOVING_SPEED_SP, invader_moving_speed);
			editor.putInt(TIME_COUNTER_BONUS_1_SP, time_counter_bonus_1);
			editor.putInt(TIME_COUNTER_BONUS_2_SP, time_counter_bonus_2);
			editor.putInt(TIME_COUNTER_BONUS_3_SP, time_counter_bonus_3);
			editor.putInt(NUMBER_OF_TAP_SP, number_of_tap);
			editor.putInt(NUMBER_OF_JUMP_SP, number_of_jump);
			editor.putInt(NUMBER_OF_KILLED_SP, number_of_killed);
			editor.putBoolean(NOT_ENOUGH_ENERGY_FIRE_SP, not_enough_energy_fire);
			editor.putBoolean(NOT_ENOUGH_ENERGY_JUMP_SP, not_enough_energy_jump);
			editor.putInt(REMAINING_TIME_SP, remaining_time);
			editor.putInt(POINT, point); 
			editor.putInt(SHAKES, shakes);
			editor.putInt(REVERSES, reverses); 
			editor.putInt(BONUS, number_of_bonus);
			editor.putBoolean(SPEED_RESUME_FLAG, true);
			editor.putFloat(CANNON_X_POS, speedModeView.cannon_x_pos);
			editor.putInt(WALL_HP, wall_hp);
			editor.putBoolean(IF_INVADER_ALERT, if_invader_alert);
			
			editor.putString(Y_POS_OF_INVADER_IN_COLUMN_1, formString(y_pos_of_invader_in_column_1));
			editor.putString(Y_POS_OF_INVADER_IN_COLUMN_2, formString(y_pos_of_invader_in_column_2));
			editor.putString(Y_POS_OF_INVADER_IN_COLUMN_3, formString(y_pos_of_invader_in_column_3));
			editor.putString(Y_POS_OF_INVADER_IN_COLUMN_4, formString(y_pos_of_invader_in_column_4));
			editor.putString(Y_POS_OF_INVADER_IN_COLUMN_5, formString(y_pos_of_invader_in_column_5));
			
			editor.putInt(NUMBER_OF_INVADERS_IN_COLUMN_1, number_of_invaders_in_column_1);
			editor.putInt(NUMBER_OF_INVADERS_IN_COLUMN_2, number_of_invaders_in_column_2);
			editor.putInt(NUMBER_OF_INVADERS_IN_COLUMN_3, number_of_invaders_in_column_3);
			editor.putInt(NUMBER_OF_INVADERS_IN_COLUMN_4, number_of_invaders_in_column_4);
			editor.putInt(NUMBER_OF_INVADERS_IN_COLUMN_5, number_of_invaders_in_column_5);
			
			editor.commit();
			finish();
			Intent back = new Intent(this, ShakeSurvivalMain.class);
			startActivity(back);
			}
	}
	
	protected void goResult(){
		savedData = getSharedPreferences(SPEED_MODE_GAME_NAME, 0);
		SharedPreferences.Editor editor = savedData.edit();
		editor.putInt(POINT, point); 
		editor.putInt(SHAKES, shakes);
		editor.putInt(REVERSES, reverses); 
		editor.putInt(NUMBER_OF_JUMP_SP, number_of_jump);
		editor.putInt(BONUS, number_of_bonus);
		editor.putInt(NUMBER_OF_KILLED_SP, number_of_killed);
		editor.putBoolean(SPEED_RESUME_FLAG, false);
		date_time = new SimpleDateFormat("MM-dd HH:mm").format(Calendar.getInstance().getTime());
		editor.putString(DATE_TIME, date_time);
		// reset the if_new_record to false
		editor.putBoolean(SPEED_IF_NEW_RECORD, false);
		editor.commit();
		
		SharedPreferences speedResult;
		speedResult = getSharedPreferences(SPEED_MODE_GAME_RECORD, 0);
		// count the total calories
		speed_total_calories = speedResult.getFloat(SPEED_TOTAL_CALORIES, 0);
		float temp_total_calories =  (float) (0.05 * shakes + 0.2 * number_of_jump + reverses * 0.10);
		speed_total_calories = speed_total_calories + temp_total_calories;
		// save new total calories
		SharedPreferences.Editor editor3 = speedResult.edit();
		editor3.putFloat(SPEED_TOTAL_CALORIES, speed_total_calories);
		// store the start time when play game at the first time
		// set the first play time here. check if the speed mode has been played before
		SharedPreferences survivalResult = getSharedPreferences(SurvivalModeGame.SURVIVAL_MODE_GAME_RECORD, 0);
		String survival_game_start_date = survivalResult.getString(SurvivalModeGame.SURVIVAL_GAME_START_DATE, "");
		// store the start time when play game at the first time
		speed_game_start_date = speedResult.getString(SPEED_GAME_START_DATE, "");
		if(survival_game_start_date.equals("") && speed_game_start_date.equals("")){
			speed_game_start_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			editor3.putString(SPEED_GAME_START_DATE, speed_game_start_date);
		}
		editor3.commit();
				
		int recorded_point = speedResult.getInt(TimeModeGame.POINT, 0);
		if(point > recorded_point){// new highest record
			speed_if_new_record = true;
			savedData2 = getSharedPreferences(SPEED_MODE_GAME_RECORD, 0);
			SharedPreferences.Editor editor2 = savedData2.edit();
			editor2.putInt(POINT, point); 
			editor2.putInt(SHAKES, shakes);
			editor2.putInt(REVERSES, reverses); 
			editor2.putInt(NUMBER_OF_JUMP_SP, number_of_jump);
			editor2.putInt(BONUS, number_of_bonus);
			editor2.putInt(NUMBER_OF_KILLED_SP, number_of_killed);
			editor2.putString(DATE_TIME, date_time);
			editor2.commit();
			// it's a new record
			editor.putBoolean(SPEED_IF_NEW_RECORD, true);
			editor.commit();
		}
		finish();
		Intent result = new Intent(this, TimeResult.class);
		startActivity(result);
	}
	   
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		long timestamp = sensorEvent.timestamp;
		Boolean if_linear_sensor_works = false; 
		
		if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			
			float z =  sensorEvent.values[SensorManager.DATA_Z];
			
		    if (Math.abs(z - last_z_linear) > 20.0 && (timestamp - sTimestamp1) / 10000000L > 100 && screenPressed) {
		    	if_linear_sensor_works = true;
	    		speedModeView.kill_one_line_invaders();
	    		sTimestamp1 = timestamp;		
		    }		    		
		    
            last_z_linear = z;
		}
			
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{

/*			float x = sensorEvent.values[SensorManager.DATA_X];
			float y = sensorEvent.values[SensorManager.DATA_Y];
			float z = sensorEvent.values[SensorManager.DATA_Z];
*/
			/* this one has bug. when you slightly tilt the phone, it counts shake continuously.
			accelVals = lowPass(sensorEvent.values, accelVals);
			float x = accelVals[0];
			float y = accelVals[1];
			float z = accelVals[2];
			*/
			
			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1];
			float z = sensorEvent.values[2];
			
			/** User can gain extra energy by shaking the phone, the time interval is 40 miliseconds*/
			// backup: >5, >4, >30 
			if((Math.abs(x - last_x) > 10 || Math.abs(y - last_y) > 10) && (timestamp - sTimestamp2)/10000000 > 40 && !screenPressed)
			{
				if(sTimestamp2 != 0){
					energy += 4;
					shakes++;
					wall_hp++;
					vibrate(50);
					if_shaking = true;
				}
			
				if(energy >= 2){
					not_enough_energy_fire = false;
				}
				if(energy >= 10){
					not_enough_energy_jump = false;
				}

				sTimestamp2 = timestamp;
				
			}
			
			if (Math.abs(z - last_z) > 16.0 && (timestamp - sTimestamp1) / 10000000L > 100 && screenPressed && !if_linear_sensor_works)
			{
				speedModeView.kill_one_line_invaders();	
				sTimestamp1 = timestamp;
			}
			
			last_x = x;
			last_y = y;
			last_z = z;
	    }
	}
	
	protected float[] lowPass( float[] input, float[] output ) {
	    if ( output == null ) return input;

	    for ( int i=0; i<input.length; i++ ) {
	        output[i] = output[i] + ALPHA * (input[i] - output[i]);
	    }
	    return output;
	}
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
