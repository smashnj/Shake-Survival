package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import edu.neu.madcourse.yongnanzhou_shake_survival.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.PathEffect;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;


public class TimeModeView extends View implements SensorEventListener{
	   private static final String TAG = "Time Mode";
	   private static final String CANNON_X_POS = "cannon_x_pos";
	   
	   private static final String VIEW_STATE = "viewState";
	   private static final int ID = 47; 
	   
	   protected float width;    		  // width of one tile
	   protected float height;   		  // height of one tile
	   protected float cannon_x_pos = 0;       
	   float last_z = 10;
	   private long lastUpdate = -1;
	   private static final int SHAKE_THRESHOLD = 400;
	   int flippingCounts = 0;
	   
	   private final TimeModeGame game;
	   
	   int width_for_grid = 0;
	   
	   Boolean if_fire_effect_1 = false; // right one
	   Boolean if_fire_effect_2 = false;
	   Boolean if_fire_effect_3 = false;
	   Boolean if_fire_effect_4 = false;
	   Boolean if_fire_effect_5 = false; // left one
	   
	   Boolean if_bomb_effect = false;
	   Boolean if_bomb_effect_second = false;
	   
	   Boolean if_wall_effect = false;
	   Boolean if_wall_effect_second = false;
	   
	   Boolean if_kill_invader_1 = false; // right one
	   Boolean if_kill_invader_2 = false; 
	   Boolean if_kill_invader_3 = false; 
	   Boolean if_kill_invader_4 = false; 
	   Boolean if_kill_invader_5 = false; // left one
	   
	   // second smoke effect
	   Boolean if_kill_invader_1_second = false; // right one
	   Boolean if_kill_invader_2_second = false; 
	   Boolean if_kill_invader_3_second = false; 
	   Boolean if_kill_invader_4_second = false; 
	   Boolean if_kill_invader_5_second = false; // left one
	   
	   Boolean if_bonus_10_wall_hp = false;
	   Boolean if_bonus_20_energy = false;
	   Boolean if_bonus_bomb = false;
	   
	   Random generator = new Random();
	   int bonus_x_pos = getWidth()/2; // center
	   int bonus_x_pos_left = getWidth()/2; // left
	   int bonus_x_pos_right = getWidth()/2; // right
	   int bonus_1_draw_times = 0;
	   int bonus_2_draw_times = 0;
	   int bonus_3_draw_times = 0;
	   int bonus_position = 0;
	   int level = 1;
	   
	   Boolean if_no_energy_bomb_black_bg = true;
	   Boolean if_bonus_star = false;
	   Boolean if_wall_crashed = false;
	   
	   float mouse_x = 0;
	   float mouse_y = 0;
	   Boolean tap_down_flag = false;
	   Boolean tap_up_flag = false;
	   
	   int kill_effect_y_pos_1 = 0;
	   int kill_effect_y_pos_2 = 0;
	   int kill_effect_y_pos_3 = 0;
	   int kill_effect_y_pos_4 = 0;
	   int kill_effect_y_pos_5 = 0;
	   
	   int[] temp_y_pos_in_column_1 = new int[10];
	   int[] temp_y_pos_in_column_2 = new int[10];
	   int[] temp_y_pos_in_column_3 = new int[10];
	   int[] temp_y_pos_in_column_4 = new int[10];
	   int[] temp_y_pos_in_column_5 = new int[10];
	      
	   /*
	    * Cannon Motion
	    * 
	    * */
 // diameter of the balls in meters
 private static final float sBallDiameter = 0.004f;
 private static final float sBallDiameter2 = sBallDiameter * sBallDiameter;

 // friction of the virtual table and air
 private static final float sFriction = 1.0f;

 private Sensor mAccelerometer;
 private long mLastT;
 private float mLastDeltaT;

 private float mXDpi;
 private float mYDpi;
 private float mMetersToPixelsX;
 private float mMetersToPixelsY;
 private Bitmap mBitmap;
 private Bitmap mWood;
 private float mXOrigin;
 private float mYOrigin;
 private float mSensorX;
 private float mSensorY;
 private long mSensorTimeStamp;
 private long mCpuTimeStamp;
 private float mHorizontalBound;
 private float mVerticalBound;
 private final ParticleSystem mParticleSystem = new ParticleSystem();
 
 /*
  * Each of our particle holds its previous and current position, its
  * acceleration. for added realism each particle has its own friction
  * coefficient.
  */
 class Particle {
     private float mPosX;
     //private float mPosY;
     private float mAccelX;
     //private float mAccelY;
     private float mLastPosX;
     //private float mLastPosY;
     private float mOneMinusFriction;

     Particle() {
         // make each particle a bit different by randomizing its
         // coefficient of friction
         final float r = ((float) Math.random() - 0.5f) * 0.2f;
         mOneMinusFriction = 1.0f - sFriction + r;
     }

     public void computePhysics(float sx, float sy, float dT, float dTC) {
         // Force of gravity applied to our virtual object
         final float m = 1000.0f; // mass of our virtual object
         final float gx = -sx * m;
         final float gy = -sy * m;

         /*
          * ·F = mA <=> A = ·F / m We could simplify the code by
          * completely eliminating "m" (the mass) from all the equations,
          * but it would hide the concepts from this sample code.
          */
         final float invm = 1.0f / m;
         final float ax = gx * invm;
         final float ay = gy * invm;

         /*
          * Time-corrected Verlet integration The position Verlet
          * integrator is defined as x(t+Æt) = x(t) + x(t) - x(t-Æt) +
          * a(t)Ætö2 However, the above equation doesn't handle variable
          * Æt very well, a time-corrected version is needed: x(t+Æt) =
          * x(t) + (x(t) - x(t-Æt)) * (Æt/Æt_prev) + a(t)Ætö2 We also add
          * a simple friction term (f) to the equation: x(t+Æt) = x(t) +
          * (1-f) * (x(t) - x(t-Æt)) * (Æt/Æt_prev) + a(t)Ætö2
          */
         final float dTdT = dT * dT;
         final float x = mPosX + mOneMinusFriction * (float)0.2 * (mPosX - mLastPosX) + mAccelX * (float)0.002;
/*                final float y = mPosY + mOneMinusFriction * dTC * (mPosY - mLastPosY) + mAccelY
                 * dTdT;
*/
         mLastPosX = mPosX;
//         mLastPosY = mPosY;
         mPosX = x;
//         mPosY = y;
         mAccelX = ax;
//         mAccelY = ay;
     }

     /*
      * Resolving constraints and collisions with the Verlet integrator
      * can be very simple, we simply need to move a colliding or
      * constrained particle in such way that the constraint is
      * satisfied.
      */
     public void resolveCollisionWithBounds() {
         final float xmax = mHorizontalBound;
         final float ymax = mVerticalBound;
         final float x = mPosX;
//         final float y = mPosY;
         if (x > xmax) {
             mPosX = xmax;
         } else if (x < -xmax) {
             mPosX = -xmax;
         }
/*                if (y > ymax) {
             mPosY = ymax;
         } else if (y < -ymax) {
             mPosY = -ymax;
         }
*/
    }
 }  
 
 /*
  * A particle system is just a collection of particles
  */
 class ParticleSystem {
     static final int NUM_PARTICLES = 1;
     private Particle mBalls[] = new Particle[NUM_PARTICLES];

     ParticleSystem() {
         /*
          * Initially our particles have no speed or acceleration
          */
         for (int i = 0; i < mBalls.length; i++) {
             mBalls[i] = new Particle();
         }
     }

     /*
      * Update the position of each particle in the system using the
      * Verlet integrator.
      */
     private void updatePositions(float sx, float sy, long timestamp) {
         final long t = timestamp;
         if (mLastT != 0) {
             final float dT = (float) (t - mLastT) * (1.0f / 1000000000.0f);
             if (mLastDeltaT != 0) {
                 final float dTC = dT / mLastDeltaT;
                 final int count = mBalls.length;
                 for (int i = 0; i < count; i++) {
                     Particle ball = mBalls[i];
                     ball.computePhysics(sx, sy, dT, dTC);
                 }
             }
             mLastDeltaT = dT;
         }
         mLastT = t;
     }

     /*
      * Performs one iteration of the simulation. First updating the
      * position of all the particles and resolving the constraints and
      * collisions.
      */
     public void update(float sx, float sy, long now) {
         // update the system's positions
         updatePositions(sx, sy, now);

         // We do no more than a limited number of iterations
         final int NUM_MAX_ITERATIONS = 10;

         /*
          * Resolve collisions, each particle is tested against every
          * other particle for collision. If a collision is detected the
          * particle is moved away using a virtual spring of infinite
          * stiffness.
          */
         boolean more = true;
         final int count = mBalls.length;
         for (int k = 0; k < NUM_MAX_ITERATIONS && more; k++) {
             more = false;
             for (int i = 0; i < count; i++) {
                 Particle curr = mBalls[i];
                 for (int j = i + 1; j < count; j++) {
                     Particle ball = mBalls[j];
                     float dx = ball.mPosX - curr.mPosX;
//                     float dy = ball.mPosY - curr.mPosY;
                     float dd = dx * dx;// + dy * dy;
                     // Check for collisions
                     if (dd <= sBallDiameter2) {
                         /*
                          * add a little bit of entropy, after nothing is
                          * perfect in the universe.
                          */
                         dx += ((float) Math.random() - 0.5f) * 0.0001f;
                         //dy += ((float) Math.random() - 0.5f) * 0.0001f;
                         dd = dx * dx;// + dy * dy;
                         // simulate the spring
                         final float d = (float) Math.sqrt(dd);
                         final float c = (0.5f * (sBallDiameter - d)) / d;
                         curr.mPosX -= dx * c;
                         //curr.mPosY -= dy * c;
                         ball.mPosX += dx * c;
                         //ball.mPosY += dy * c;
                         more = true;
                     }
                 }
                 /*
                  * Finally make sure the particle doesn't intersects
                  * with the walls.
                  */
                 curr.resolveCollisionWithBounds();
             }
         }
     }

     public int getParticleCount() {
         return mBalls.length;
     }

     public float getPosX(int i) {
         return mBalls[i].mPosX;
     }

/*            public float getPosY(int i) {
         return mBalls[i].mPosY;
     }
*/
}       
 public void startSimulation() {
     /*
      * It is not necessary to get accelerometer events at a very high
      * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
      * automatic low-pass filter, which "extracts" the gravity component
      * of the acceleration. As an added benefit, we use less power and
      * CPU resources.
      */
     game.mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
 }

 public void stopSimulation() {
     game.mSensorManager.unregisterListener(this);
 }	   
	   public TimeModeView(Context context) {
	      super(context);
	      //initial_y_pos = 8 * (getHeight() / 5 - (getHeight()/20) - 20);
	      this.game = (TimeModeGame) context;
	      setFocusable(true);
	      setFocusableInTouchMode(true);
	      setId(ID); 
	      
    mAccelerometer = game.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    DisplayMetrics metrics = new DisplayMetrics();
    game.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    mXDpi = metrics.xdpi;
    mYDpi = metrics.ydpi;
    mMetersToPixelsX = mXDpi / 0.0254f;
    mMetersToPixelsY = mYDpi / 0.0254f;

    // rescale the ball so it's about 0.5 cm on screen
    Bitmap cannon = BitmapFactory.decodeResource(getResources(), R.drawable.cannon);
    final int dstWidth = (int) (sBallDiameter * mMetersToPixelsX + 0.5f);
    final int dstHeight = (int) (sBallDiameter * mMetersToPixelsY + 0.5f);
    mBitmap = Bitmap.createScaledBitmap(cannon, dstWidth, dstHeight, true);
    //mBitmap = Bitmap.createScaledBitmap(cannon, getWidth()/5, getWidth()/5, true); // 48
    
    Options opts = new Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    mWood = BitmapFactory.decodeResource(getResources(), R.drawable.space_bg, opts);
	   }
	   
	   public TimeModeView(Context context, AttributeSet attrs){
		   super(context, attrs);
		   this.game = (TimeModeGame) context;
	   }
	   
	   @Override
	   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	      width = w / 5 - 1; // the width between two invaders.
	      width_for_grid = w / 5;
	      height = (float) (h / 5 - (getHeight()/20) - (getHeight()/20)); // getHeight()/33.333 = 20 the height between each wave of invaders.
	      Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
	       // compute the origin of the screen relative to the origin of
	       // the bitmap
	      if(game.cannon_x_pos != 0){
	    	  mXOrigin = game.cannon_x_pos;
	      }
	      else{
	    	  mXOrigin = (w - mBitmap.getWidth()) * 0.5f;
	      }
	      
	      mYOrigin = (h - mBitmap.getHeight()) * 0.5f;
		      
	      mHorizontalBound = ((w / mMetersToPixelsX - sBallDiameter) * 0.5f);
	      mVerticalBound = ((h / mMetersToPixelsY - sBallDiameter) * 0.5f);
		  super.onSizeChanged(w, h, oldw, oldh);
		  }

	   @SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		      // Draw the background
		      canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.space_bg), 0, 0, null);
	          /*
	           * compute the new position of our object, based on accelerometer
	           * data and present time.
	           */

	          final ParticleSystem particleSystem = mParticleSystem;
	          final long now = mSensorTimeStamp + (System.nanoTime() - mCpuTimeStamp);
	          final float sx = mSensorX;
	          final float sy = mSensorY;

	          particleSystem.update(sx, sy, now);

	          final float xc = mXOrigin;
	          final float yc = mYOrigin;
	          final float xs = mMetersToPixelsX;
	          final float ys = mMetersToPixelsY;
	          final Bitmap bitmap = mBitmap;

	          final int count = particleSystem.getParticleCount();
	          for (int i = 0; i < count; i++) {
	              /*
	               * We transform the canvas so that the coordinate system matches
	               * the sensors coordinate system with the origin in the center
	               * of the screen and the unit is the meter.
	               */

	              final float x = xc + particleSystem.getPosX(i) * xs;
	              cannon_x_pos = x;
	              
	              if(x <= (float)bonus_x_pos_right && x >= (float)bonus_x_pos_left){

	            	  // if the cannon touch the bonus.
					  if(if_bonus_10_wall_hp){
						  game.wall_hp = game.wall_hp + 10;
						  if_bonus_10_wall_hp = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  
						  game.not_enough_energy_jump = false;
						  game.not_enough_energy_fire = false;
						  if_bonus_star = true;
					  }
					  if(if_bonus_20_energy){
						  game.energy = game.energy + 20;
						  if_bonus_20_energy = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  
						  game.not_enough_energy_jump = false;
						  game.not_enough_energy_fire = false;
						  if_bonus_star = true;
					  }
					  if(if_bonus_bomb){
						  // balance the data in kill_one_line_invaders() function.
						  game.energy = game.energy + 10;
						  game.number_of_jump--;
						  kill_one_line_invaders();
						  if_bonus_bomb = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  if_bonus_star = true;
					  }            	  
	              }

	              canvas.drawBitmap(bitmap, x, (float)(1.3 * height), null);
	          }
	          
	      // Define colors for the grid lines
	      Paint hilite = new Paint();
	      hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
	      Paint light = new Paint();
	      light.setColor(getResources().getColor(R.color.puzzle_light));
	      Paint red = new Paint();
	      red.setStyle(Style.FILL);
	      red.setTextSize(width * 0.25f);
	      red.setColor(0xFFFF0000);

	      // Draw vertical gray line, starts from bottom_line
	      for (int i = 0; i < 6; i++) {
	         if(i == 5){// last line
	        	 canvas.drawLine(i * width_for_grid, 2 * height + 1, i * width_for_grid - 1, 9 * height, light);
	         }
	         else {
	        	 canvas.drawLine(i * width_for_grid, 2 * height + 1, i * width_for_grid + 1, 9 * height, light);
	         }
	      }
	      
	      // Draw horizontal gray line, starts from bottom_line
	      for (int i = 1; i < 10; i++) {
	    	  	 if(i == 1){// first line behind the canon
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height, hilite);
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height + 1, hilite);
	    	  	 }
	    	  	 if(i == 2){// bottom line
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height, hilite);
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height + 1, hilite);
	    	  	 }
	    	  	 /*if(i == 4){// wall line
	    	  		Paint text_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    		    text_paint.setColor(0xFFFFFFFF);
	    		    text_paint.setStyle(Style.FILL);
	    		    text_paint.setTextSize(width * 0.21f);
	    		      
	    	  		canvas.drawText(""+i*height, getWidth()/2, i*height - 5, text_paint);
	    	  	 }*/
	    	  	 if(i == 9){// last line
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height, red);
	    	  		canvas.drawLine(0, i * height, getWidth(), i * height + 1, red);
	    	  	 }
		         else {
		        	canvas.drawLine(0, i * height, getWidth(), i * height + 1, light);
		         }
		  }
	      
	      // Draw alert line
	      Paint alert_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      alert_paint.setColor(0xFFD42626);
	      alert_paint.setStyle(Style.FILL);
	      alert_paint.setTextSize(width * 0.20f);
	      canvas.drawText("", getWidth()/48, game.alert_line - 5, alert_paint);
	      
	      Paint fgPaintSel = new Paint();
		  fgPaintSel.setARGB(255, 212, 38,38);
		  fgPaintSel.setStyle(Style.STROKE);
		  fgPaintSel.setPathEffect(new DashPathEffect(new float[] {10,10}, 10));
		  fgPaintSel.setAlpha(120); 
		  canvas.drawLine(0, game.alert_line, getWidth(), game.alert_line, fgPaintSel);
		  canvas.drawLine(0, game.alert_line, getWidth(), game.alert_line + 1, fgPaintSel);
		  
	      // Draw the cannon.
	      // canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.canon), getWidth()/2, height, null);
	      
	      // Define color and style for letters
	      /*Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	      foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
	      foreground.setStyle(Style.FILL);
	      foreground.setTextSize(height * 0.75f);
	      foreground.setTextScaleX(width / height);
	      foreground.setTextAlign(Paint.Align.CENTER);*/

	      Paint text_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      text_paint.setColor(0xFFFFFFFF);
	      text_paint.setStyle(Style.FILL);
	      text_paint.setTextSize(width * 0.21f); //text_paint.setTextSize(height * 0.26f);
	      
	      Paint game_over_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      game_over_paint.setColor(0xFFFFFF00);
	      game_over_paint.setStyle(Style.FILL);
	      game_over_paint.setTextSize(height * 0.45f);
	      
	      // getWidth()/24 = 10, getWidth()/48 = 5, getWidth()/8 = 30, getWidth()/12 = 20
	      // getHeight()/40 = 10, getWidth()/2.667 = 90
	      // getWidth()/6.857 = 35, getWidth()/5.333 = 45 getWidth()/4.8 getHeight()/8 = 50
	      // getHeight()/13.33 = 30 getHeight()/16 = 25, getHeight()/11.328 = 35, getHeight()/6.667 = 60
	      // String TIME = "Survive: ";
	      // canvas.drawText(TIME+game.seconds_so_far+"s", getWidth()/2 - getWidth()/12, getHeight()/40, text_paint);
	      // draw timer
	      
	      Paint time_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      time_paint.setColor(0xFFFFFF00);//yellow
	      time_paint.setStyle(Style.FILL);
	      time_paint.setTextSize(width * 0.21f);
	      String TIMER = "Time: ";
	      canvas.drawText(TIMER+String.valueOf(game.remaining_time)+"s", getWidth()/2 - getWidth()/12, getHeight()/40, time_paint);
	      
	      
	      String POINT = "Points: ";
	      //canvas.drawText(POINT+game.point, getWidth()-(getWidth()/40*10), getHeight()/40, text_paint);
	      canvas.drawText(POINT+game.point, getWidth()/48, getHeight()/40, text_paint);
	      
	      String LEVEL = "Level: ";
	      canvas.drawText(LEVEL+level, getWidth()/48, getHeight()/20, text_paint);
	      
	      /*
	      String TIME_INTERVAL = "Wave Interval: ";
	      canvas.drawText(TIME_INTERVAL+game.wave_interval+"s", (float) (getWidth()-(getWidth()/48*10) - getWidth()/2.181), getHeight()/40, text_paint);
	      String SPEED = "Speed: ";
	      canvas.drawText(SPEED+game.invader_moving_speed, getWidth()/48, getHeight()/40, text_paint);
	      
	      String FIRE = "Tap the grid to fire:";
	      canvas.drawText(FIRE+game.number_of_tap, getWidth()/48, getHeight()/20, text_paint);
	      String BOMB = "Tap here to jump:";
	      canvas.drawText(BOMB+game.number_of_jump, getWidth()/48, getHeight()/10, text_paint);
	      String LINES = "Lines:";
	      canvas.drawText(LINES+String.valueOf(game.lines_of_invaders_so_far), getWidth()/48, getHeight()/8, text_paint);
	      */
	      //String KILLED = "Killed:";
	      //canvas.drawText(KILLED+String.valueOf(game.number_of_killed), getWidth()/48, getHeight()/20, text_paint);
	      String BONUS = "Bonus:";
	      //canvas.drawText(BONUS+game.number_of_bonus, getWidth()/48, getHeight()/20, text_paint);
	      canvas.drawText(BONUS+game.number_of_bonus, getWidth()/48, getHeight()/20 + getHeight()/40, text_paint);
	      
	      // Draw Energy
	      if(game.energy > 25){ //green
	    	  Paint text_paint_energy = new Paint(Paint.ANTI_ALIAS_FLAG);
	    	  text_paint_energy.setColor(0xFF5AFA39); //green
	    	  text_paint_energy.setStyle(Style.FILL);
	    	  text_paint_energy.setTextSize(width * 0.21f);
	    	  String ENERGY = "Energy:";
		      canvas.drawText(ENERGY+String.valueOf(game.energy), getWidth()/2 - getWidth()/12, getHeight()/20, text_paint_energy);
		  }
		  else{ //red
			  Paint text_paint_energy = new Paint(Paint.ANTI_ALIAS_FLAG);
			  text_paint_energy.setColor(0xFFF25E68); //red
			  text_paint_energy.setStyle(Style.FILL);
			  text_paint_energy.setTextSize(width * 0.21f);
			  String ENERGY = "Energy:";
			      canvas.drawText(ENERGY+String.valueOf(game.energy), getWidth()/2 - getWidth()/12, getHeight()/20, text_paint_energy);
		  }
	      
	      // Draw the Energy Bar
	      Paint energy_bar_red_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      Paint energy_bar_red_bg_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      Paint energy_bar_green_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      Paint energy_bar_green_bg_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      energy_bar_red_paint.setColor(0xFFFF0000); //red
	      energy_bar_red_bg_paint.setColor(0xFFFF0000); //red
	      energy_bar_green_paint.setColor(0xFF5AFA39); //green
	      energy_bar_green_bg_paint.setColor(0xFF5AFA39); //green
	      energy_bar_red_bg_paint.setAlpha(95);
	      energy_bar_green_bg_paint.setAlpha(95);
	      
	      int energy_bar_start_x_pos = getWidth()/2 - getWidth()/6;
	      int energy_bar_start_y_pos = (int) (getHeight()/13 - getHeight()/60);
	      int energy_bar_height = (int) (getHeight()/20 + getHeight()/22 - getHeight()/13);
	      float width_per_unit_energy_in_bar = (getWidth()/3 + width/8) / 50;
	      int number_of_energy_bar = game.energy/50;
	      int number_of_unit_energy_in_bar_for_display = 0;
	      
	      if(game.energy % 50 == 0 && game.energy > 0){
	    	  number_of_energy_bar--; // otherwise, when energy = 20, the bar is full. And when energy > 20, the bas has less color.
	    	  number_of_unit_energy_in_bar_for_display = 50;
	      }
	      else{
	    	  if(number_of_energy_bar > 0){ // >50  (game.energy- (number_of_unit_energy_in_bar_for_display*50) could be 0. Error
	    		  number_of_unit_energy_in_bar_for_display = ((game.energy - (number_of_energy_bar * 50)) % 50);
	    	  }
		      else{ // there are only less than 50 units of energy remaining.
		    	  if(game.energy > 0){
		    		  number_of_unit_energy_in_bar_for_display = game.energy % 50;
		    	  }
		    	  else{
		    		  number_of_unit_energy_in_bar_for_display = 0;
		    	  }
		      }
	      }
	      
	      if(game.energy > 25){ // green
		      Path green_rectangle1;
		      Path green_rectangle2;
		      RectF green_energy = new RectF(energy_bar_start_x_pos, energy_bar_start_y_pos, 
		    		  energy_bar_start_x_pos + (width_per_unit_energy_in_bar * number_of_unit_energy_in_bar_for_display), energy_bar_start_y_pos + energy_bar_height);
		      RectF green_energy_bg = new RectF(energy_bar_start_x_pos, energy_bar_start_y_pos, energy_bar_start_x_pos + 50 * width_per_unit_energy_in_bar, energy_bar_start_y_pos + energy_bar_height);
		      green_rectangle1 = new Path();
		      green_rectangle1.addRoundRect(green_energy, 5, 5, Direction.CW);
		      green_rectangle2 = new Path();
		      green_rectangle2.addRoundRect(green_energy_bg, 5, 5, Direction.CW);
		      canvas.drawPath(green_rectangle1, energy_bar_green_paint);
		      canvas.drawPath(green_rectangle2, energy_bar_green_bg_paint);
		      
		      Paint text_paint_energy = new Paint(Paint.ANTI_ALIAS_FLAG);
			  text_paint_energy.setColor(0xFF5AFA39); //green
			  text_paint_energy.setStyle(Style.FILL);
			  text_paint_energy.setTextSize(width * 0.21f);
			  Paint text_paint_energy_data = new Paint(Paint.ANTI_ALIAS_FLAG);
			  text_paint_energy_data.setColor(0xFF5AFA39); //green
			  text_paint_energy_data.setStyle(Style.FILL);
			  text_paint_energy_data.setTextSize(width * 0.20f);
		      canvas.drawText("x"+String.valueOf(number_of_energy_bar), 
		    		  energy_bar_start_x_pos + 50 * width_per_unit_energy_in_bar + width/12, energy_bar_start_y_pos + height/5, text_paint_energy);
		      canvas.drawText(String.valueOf(number_of_unit_energy_in_bar_for_display)+"/50", 
		    		  energy_bar_start_x_pos + width/2 + width/8, energy_bar_start_y_pos + height/7 + height/5 + 3, text_paint_energy_data);
	      }
	      else{// red, when there is not enough energy remaining
		      Path red_rectangle1;
		      Path red_rectangle2;
		      RectF red_energy = new RectF(energy_bar_start_x_pos, energy_bar_start_y_pos, 
		    		  energy_bar_start_x_pos + (width_per_unit_energy_in_bar * number_of_unit_energy_in_bar_for_display), energy_bar_start_y_pos + energy_bar_height);
		      RectF red_energy_bg = new RectF(energy_bar_start_x_pos, energy_bar_start_y_pos, energy_bar_start_x_pos + 50 * width_per_unit_energy_in_bar, energy_bar_start_y_pos + energy_bar_height);
		      red_rectangle1 = new Path();
		      red_rectangle1.addRoundRect(red_energy, 5, 5, Direction.CW);
		      red_rectangle2 = new Path();
		      red_rectangle2.addRoundRect(red_energy_bg, 5, 5, Direction.CW);
		      canvas.drawPath(red_rectangle1, energy_bar_red_paint);
		      canvas.drawPath(red_rectangle2, energy_bar_red_bg_paint);
		      
		      Paint text_paint_energy = new Paint(Paint.ANTI_ALIAS_FLAG);
			  text_paint_energy.setColor(0xFFFF0000); //red
			  text_paint_energy.setStyle(Style.FILL);
			  text_paint_energy.setTextSize(width * 0.21f);
			  Paint text_paint_energy_data = new Paint(Paint.ANTI_ALIAS_FLAG);
			  text_paint_energy_data.setColor(0xFFFF0000); //red
			  text_paint_energy_data.setStyle(Style.FILL);
			  text_paint_energy_data.setTextSize(width * 0.20f);
		      canvas.drawText("x"+String.valueOf(number_of_energy_bar), 
		    		  energy_bar_start_x_pos + 50 * width_per_unit_energy_in_bar + width/12, energy_bar_start_y_pos + height/5, text_paint_energy);
		      canvas.drawText(String.valueOf(number_of_unit_energy_in_bar_for_display)+"/50", 
		    		  energy_bar_start_x_pos + width/2 + width/8, energy_bar_start_y_pos + height/7 + height/5 + 3, text_paint_energy_data);
	      }
	      
	      // Draw Game Over
	      if (game.game_over_flag){
	    	  Paint no_energy_paint = new Paint();
	    	  no_energy_paint.setAlpha(70);
	    	  Paint normal_paint = new Paint();
		      canvas.drawRect(0, 0, getWidth(), getHeight(), no_energy_paint);
	    	  String GAMEOVER = "Game Over!";
	    	  canvas.drawText(GAMEOVER, (float) (getWidth() / 2 - getWidth()/5.333), (float) (getHeight()/11.328), game_over_paint);
	      }
	      
	      //Draw the castles
	      Paint p2 = new Paint();
	      Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.castle);
	      for(int j = 1; j <= 6; j++){
	    	  if(j == 1){
	    		  canvas.drawBitmap(b2, (j - 1) * width + width/5, 2 * height, p2);
	    	  }
	    	  else{
	    		  canvas.drawBitmap(b2, (j - 1) * width + width/4, 2 * height, p2);  
	    	  }
	      }
	      
	      // Draw the killed effect, invader becomes smoke
	      Paint p3 = new Paint();
	      p3.setAntiAlias(true);
	 	  p3.setFilterBitmap(true);
	 	  p3.setDither(true);
	 	  Bitmap smoke_1= BitmapFactory.decodeResource(getResources(), R.drawable.smoke);
	 	  smoke_1 = Bitmap.createScaledBitmap(smoke_1, (int) (getWidth()/6.857), (int) (getWidth()/6.857), false);//35
	 	  
	 	  Bitmap smoke_2 = BitmapFactory.decodeResource(getResources(), R.drawable.smoke2);
		  smoke_2 = Bitmap.createScaledBitmap(smoke_2, getWidth()/8, getWidth()/8, false);//30
	  
	 	  if(if_kill_invader_1_second){
	 		 canvas.drawBitmap(smoke_1, (1 - 1) * width + width/5, kill_effect_y_pos_1, p3); // left
	      }
	      if(if_kill_invader_2_second){
	    	  canvas.drawBitmap(smoke_1, (2 - 1) * width + width/4, kill_effect_y_pos_2, p3);
	      }
	      if(if_kill_invader_3_second){
	    	  canvas.drawBitmap(smoke_1, (3 - 1) * width + width/4, kill_effect_y_pos_3, p3);
	      }
	      if(if_kill_invader_4_second){
	    	  canvas.drawBitmap(smoke_1, (4 - 1) * width + width/4, kill_effect_y_pos_4, p3);
	      }
	      if(if_kill_invader_5_second){
	    	  canvas.drawBitmap(smoke_1, (5 - 1) * width + width/4, kill_effect_y_pos_5, p3); // right
	      }
	  
	      if(if_kill_invader_1){
	    	  canvas.drawBitmap(smoke_2, (1 - 1) * width + width/4, kill_effect_y_pos_1, p3);
	      }
	      if(if_kill_invader_2){
	    	  canvas.drawBitmap(smoke_2, (2 - 1) * width + width/4, kill_effect_y_pos_2, p3);
	      }
	      if(if_kill_invader_3){
	    	  canvas.drawBitmap(smoke_2, (3 - 1) * width + width/4, kill_effect_y_pos_3, p3);
	      }
	      if(if_kill_invader_4){
	    	  canvas.drawBitmap(smoke_2, (4 - 1) * width + width/4, kill_effect_y_pos_4, p3);
	      }
	      if(if_kill_invader_5){
	    	  canvas.drawBitmap(smoke_2, (5 - 1) * width + width/5, kill_effect_y_pos_5, p3);
	      }

	      // Draw the invaders
	      draw_updated_y_pos_innvaders_in_column(canvas);
	      
	      //Draw the fire effect
	      Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.fire_effect);
	 	  // resize the effect image according to the screen size.
	 	  b3 = Bitmap.createScaledBitmap(b3, getWidth()/8, getWidth()/8, false); //30
	  
	      if(if_fire_effect_1){
	    	  canvas.drawBitmap(b3, (1 - 1) * width + width/5, 2 * height + height / 3, p3); // left
	      }
	      if(if_fire_effect_2){
	    	  canvas.drawBitmap(b3, (2 - 1) * width + width/4, 2 * height + height / 3, p3);
	      }
	      if(if_fire_effect_3){
	    	  canvas.drawBitmap(b3, (3 - 1) * width + width/4, 2 * height + height / 3, p3);
	      }
	      if(if_fire_effect_4){
	    	  canvas.drawBitmap(b3, (4 - 1) * width + width/4, 2 * height + height / 3, p3);
	      }
	      if(if_fire_effect_5){
	    	  canvas.drawBitmap(b3, (5 - 1) * width + width/4, 2 * height + height / 3, p3); // right
	      }
	      
	      // Draw the bomb effect
	      Bitmap bomb_1 = BitmapFactory.decodeResource(getResources(), R.drawable.big_bang);
	      //b5 = Bitmap.createScaledBitmap(b5, 35, 35, false);
	      int bomb_1_w = bomb_1.getWidth();
	      int bomb_1_h = bomb_1.getHeight();
	      Matrix mtx = new Matrix();
	      mtx.postRotate(180);
	      
	      // rotate the big bang effect
	      Bitmap bomb_2 = Bitmap.createBitmap(bomb_1, 0, 0, bomb_1_w, bomb_1_h, mtx, true);
	      
	      if(if_bomb_effect){
	    	  canvas.drawBitmap(bomb_1, (0 - 1) * width/2, 2 * height + height / 3, p3);
	    	  canvas.drawBitmap(bomb_2, 0  * width/2, 2 * height + height / 3, p3);
	      }
	      
	      if(if_bomb_effect_second){
	    	  canvas.drawBitmap(bomb_1, (float) (0 * width/2), 2 * height + height / 3, p3);
	    	  canvas.drawBitmap(bomb_2, (float) (-1 * width/2), 2 * height + height / 3, p3);
	      }
	      
	      // Draw the bonus
	      Bitmap bonus_1 = BitmapFactory.decodeResource(getResources(), R.drawable.bonus_1);
	      bonus_1 = Bitmap.createScaledBitmap(bonus_1, (int) (getWidth()/8), (int) (getWidth()/8), false);//30
	      Bitmap bonus_2 = BitmapFactory.decodeResource(getResources(), R.drawable.bonus_2);
	      bonus_2 = Bitmap.createScaledBitmap(bonus_2, getWidth()/8, getWidth()/8, false);//30
	      Bitmap bonus_3 = BitmapFactory.decodeResource(getResources(), R.drawable.bonus_3);
	      bonus_3 = Bitmap.createScaledBitmap(bonus_3, getWidth()/6, getWidth()/6, false);//40
	      Bitmap bonus_star = BitmapFactory.decodeResource(getResources(), R.drawable.bonus_star);
	      bonus_star = Bitmap.createScaledBitmap(bonus_star, getWidth()/12, getWidth()/12, false);//20

	      if(if_bonus_10_wall_hp){
	    	  if(bonus_1_draw_times == 0){
	    		  int w = bonus_1.getWidth();
	        	  int max = getWidth() - w;
	        	  int min = w/2;
	    		  bonus_x_pos = generator.nextInt(max - min + 1) + min;
	    		  bonus_x_pos_left = bonus_x_pos - w/2;
	    		  bonus_x_pos_right = bonus_x_pos + w/2;
	    		  bonus_1_draw_times++;
	    	  }
	    	  canvas.drawBitmap(bonus_1, bonus_x_pos, height + height/8, p3);
	      }
	      if(if_bonus_20_energy){
	    	  if(bonus_2_draw_times == 0){
	    		  int w = bonus_2.getWidth();
	        	  int max = getWidth() - w;
	        	  int min = w/2;
	    		  bonus_x_pos = generator.nextInt(max - min + 1) + min;
	    		  bonus_x_pos_left = bonus_x_pos - w/2;
	    		  bonus_x_pos_right = bonus_x_pos + w/2;
	    		  bonus_2_draw_times++;
	    	  }
	    	  canvas.drawBitmap(bonus_2, bonus_x_pos, height + height/8, p3);
	      }
	      if(if_bonus_bomb){
	    	  if(bonus_3_draw_times == 0){
	    		  int w = bonus_3.getWidth();
	        	  int max = getWidth() - w;
	        	  int min = w/2;
	    		  bonus_x_pos = generator.nextInt(max - min + 1) + min;
	    		  bonus_x_pos_left = bonus_x_pos - w/2;
	    		  bonus_x_pos_right = bonus_x_pos + w/2;
	    		  bonus_3_draw_times++;
	    	  }
	    	  canvas.drawBitmap(bonus_3, bonus_x_pos, height, p3);
	      }
	      if(if_bonus_star){
	    	  canvas.drawBitmap(bonus_star, bonus_x_pos, height/2, p3);
	      }
	      
	      // Draw the UFO
	      if(game.ufo_status == 1){
	    	  Bitmap ufo1 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo1);
	    	  ufo1 = Bitmap.createScaledBitmap(ufo1, getWidth()/4, getWidth()/8, false);// 60x30
	    	  canvas.drawBitmap(ufo1, (3 - 1) * width - width/12, 9 * height + 5, p3);
	      }
	      else if (game.ufo_status == 2){
	    	  Bitmap ufo2 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo2);
	    	  ufo2 = Bitmap.createScaledBitmap(ufo2, getWidth()/4, getWidth()/8, false);// 60x30
	    	  canvas.drawBitmap(ufo2, (3 - 1) * width - width/12, 9 * height + 5, p3);
	      }
	      else if (game.ufo_status == 3){
	    	  Bitmap ufo3 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo3);
	    	  ufo3 = Bitmap.createScaledBitmap(ufo3, getWidth()/4, getWidth()/8, false);// 60x30
	    	  canvas.drawBitmap(ufo3, (3 - 1) * width - width/12, 9 * height + 5, p3);
	      }
	      else if (game.ufo_status == 4){
	    	  Bitmap ufo4 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo4);
	    	  ufo4 = Bitmap.createScaledBitmap(ufo4, getWidth()/4, getWidth()/8, false);// 60x30
	    	  canvas.drawBitmap(ufo4, (3 - 1) * width - width/12, 9 * height + 5, p3);
	      }
	      else if (game.ufo_status == 5){
	    	  Bitmap ufo5 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo5);
	    	  ufo5 = Bitmap.createScaledBitmap(ufo5, getWidth()/4, getWidth()/8, false);// 60x30
	    	  canvas.drawBitmap(ufo5, (3 - 1) * width - width/12, 9 * height + 5, p3);
	      }
	      
	      // Draw the pause botton
	      Paint p = new Paint();
	      Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
	      canvas.drawBitmap(b, (5 - 1) * width + width / 6, 9 * height, p);
	      
	      // Draw the alert sign
	      if(game.if_invader_alert){
	    	  Bitmap alert = BitmapFactory.decodeResource(getResources(), R.drawable.alert);
	    	  alert = Bitmap.createScaledBitmap(alert, getWidth()/10, getWidth()/10, false);// 30x30
		      canvas.drawBitmap(alert, (5 - 1) * width - width / 5, getHeight()/24, p);
	      }
	      
	      // Draw the energy sign when shaking 
	      Bitmap energy = BitmapFactory.decodeResource(getResources(), R.drawable.energy);
	      energy = Bitmap.createScaledBitmap(energy, getWidth()/10, getWidth()/10, false);// 30x30
	      if(game.if_shaking){
	    	  canvas.drawBitmap(energy, (3 - 1) * width - 2 * (width / 6) - width/20, height/10 - getHeight()/100, p);
		      canvas.drawBitmap(energy, (4 - 1) * width + width / 6 - width/14, height/10 - getHeight()/100, p);
	      }
	      else{
	    	  Paint translucent_paint = new Paint();
	    	  translucent_paint.setAlpha(90);	       
	    	  canvas.drawBitmap(energy, (3 - 1) * width - 2 * (width / 6) - width/20, height/10 - getHeight()/100, translucent_paint);
		      canvas.drawBitmap(energy, (4 - 1) * width + width / 6 - width/14, height/10 - getHeight()/100, translucent_paint);
	      }
	      
	      // Draw the defend wall
	      if(game.wall_hp > 0){
	    	  Bitmap wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
	    	  wall = Bitmap.createScaledBitmap(wall, getWidth()/10, getWidth()/9, false);
	    	  Bitmap wall2 = BitmapFactory.decodeResource(getResources(), R.drawable.wall2);
	    	  wall2 = Bitmap.createScaledBitmap(wall2, getWidth()/9, getWidth()/10, false);
	    	  Bitmap wall3 = BitmapFactory.decodeResource(getResources(), R.drawable.wall3);
	    	  wall3 = Bitmap.createScaledBitmap(wall3, getWidth()/7, getWidth()/8, false);
	    	  for(int j = 1; j <= 10; j++){// Draw different looks of walls
	    		  if(game.wall_hp > 10){
		    		  if(j%2 == 0){
	  	    			  canvas.drawBitmap(wall, (j - 1) * width/2 + getWidth()/68, game.defend_wall_line, p);
	  	    		  }
	  	    		  else
	  	    		  { 
	  	    			  canvas.drawBitmap(wall2, (j - 1) * width/2 + getWidth()/68, game.defend_wall_line, p);
	  	    		  }
	    		  }
	    		  else{
	    			  canvas.drawBitmap(wall3, (j - 1) * width/2 - getWidth()/68, game.defend_wall_line, p);
	    		  }
	    	  }
	      }
	     
	      Paint text_paint_wall = new Paint(Paint.ANTI_ALIAS_FLAG);
	   	  if(game.wall_hp > 10){
	   		  text_paint_wall.setColor(0xFF5AFA39); //green
	   	  }
	   	  else{
	   		  //text_paint_wall.setColor(0xFFF25E68); //red
	   		  text_paint_wall.setColor(0xFFFF0000); //yellow
	   	  }
	   	  text_paint_wall.setStyle(Style.FILL);
	   	  text_paint_wall.setTextSize(width * 0.21f);
	   	  canvas.drawText("Wall HP: "+game.wall_hp, getWidth()/48, 9 * height + height/4, text_paint_wall);
	     	
	   	  // Draw the Wall HP Bar
	   	  int wall_hp_bar_start_x_pos = getWidth()/48;
	      float width_per_unit_wall_hp_in_bar = (getWidth()/4) / 20;
	      int number_of_wall_hp_bar = game.wall_hp/20;
	      int number_of_unit_wall_hp_in_bar_for_display = 0;
	      
	      if(game.wall_hp % 20 == 0 && game.wall_hp > 0){
	    	  number_of_wall_hp_bar--; // otherwise, when hp=20, the bar is full. And when hp > 20, the bas has less color.
	    	  number_of_unit_wall_hp_in_bar_for_display = 20;
	      }
	      else{
	    	  if(number_of_wall_hp_bar > 0){ // > 20  ((game.wall_hp - (number_of_wall_hp_bar * 50)) % 50) could be 0. Error
	    		  number_of_unit_wall_hp_in_bar_for_display = ((game.wall_hp - (number_of_wall_hp_bar * 20)) % 20);
	    	  }
		      else{ // there are only less than 20 units of energy remaining.
		    	  if(game.wall_hp > 0){
		    		  number_of_unit_wall_hp_in_bar_for_display = game.wall_hp % 20;
		    	  }
		    	  else{
		    		  number_of_unit_wall_hp_in_bar_for_display = 0;
		    	  }
		      }
	      }
	      
	      int hp_bar_height = getHeight()/20 + getHeight()/22 - getHeight()/13;
	      int hp_bar_start_y_pos = (int) (9 * height + height/3);
	      
	      if(game.wall_hp > 10){ // green
		      Path green_rectangle1;
		      Path green_rectangle2;
		      RectF green_energy = new RectF(wall_hp_bar_start_x_pos, hp_bar_start_y_pos, 
		    		  wall_hp_bar_start_x_pos + (width_per_unit_wall_hp_in_bar * number_of_unit_wall_hp_in_bar_for_display), hp_bar_start_y_pos + hp_bar_height);
		      RectF green_energy_bg = new RectF(wall_hp_bar_start_x_pos, hp_bar_start_y_pos, wall_hp_bar_start_x_pos + 20 * width_per_unit_wall_hp_in_bar, hp_bar_start_y_pos + hp_bar_height);
		      green_rectangle1 = new Path();
		      green_rectangle1.addRoundRect(green_energy, 5, 5, Direction.CW);
		      green_rectangle2 = new Path();
		      green_rectangle2.addRoundRect(green_energy_bg, 5, 5, Direction.CW);
		      canvas.drawPath(green_rectangle1, energy_bar_green_paint);
		      canvas.drawPath(green_rectangle2, energy_bar_green_bg_paint);
		      
		      Paint text_paint_hp = new Paint(Paint.ANTI_ALIAS_FLAG);
		      text_paint_hp.setColor(0xFF5AFA39); //green
		      text_paint_hp.setStyle(Style.FILL);
		      text_paint_hp.setTextSize(width * 0.21f);
		      Paint text_paint_hp_data = new Paint(Paint.ANTI_ALIAS_FLAG);
		      text_paint_hp_data.setColor(0xFF5AFA39); //green
		      text_paint_hp_data.setStyle(Style.FILL);
		      text_paint_hp_data.setTextSize(width * 0.20f);
		      canvas.drawText("x"+String.valueOf(number_of_wall_hp_bar), 
		    		  wall_hp_bar_start_x_pos + 20 * width_per_unit_wall_hp_in_bar + width/12, hp_bar_start_y_pos + height/7, text_paint_hp);
		      canvas.drawText(String.valueOf(number_of_unit_wall_hp_in_bar_for_display)+"/20", 
		    		  wall_hp_bar_start_x_pos + width/3 + width/10, hp_bar_start_y_pos + hp_bar_height + 2 * (height / 7), text_paint_hp_data);
	      }
	      else{// red, when there is not enough hp remaining
		      Path red_rectangle1;
		      Path red_rectangle2;
		      RectF red_energy = new RectF(wall_hp_bar_start_x_pos, hp_bar_start_y_pos, 
		    		  wall_hp_bar_start_x_pos + (width_per_unit_wall_hp_in_bar * number_of_unit_wall_hp_in_bar_for_display), hp_bar_start_y_pos + hp_bar_height);
		      RectF red_energy_bg = new RectF(wall_hp_bar_start_x_pos, hp_bar_start_y_pos, wall_hp_bar_start_x_pos + 20 * width_per_unit_wall_hp_in_bar, hp_bar_start_y_pos + hp_bar_height);
		      red_rectangle1 = new Path();
		      red_rectangle1.addRoundRect(red_energy, 5, 5, Direction.CW);
		      red_rectangle2 = new Path();
		      red_rectangle2.addRoundRect(red_energy_bg, 5, 5, Direction.CW);
		      canvas.drawPath(red_rectangle1, energy_bar_red_paint);
		      canvas.drawPath(red_rectangle2, energy_bar_red_bg_paint);
		      
		      Paint text_paint_hp = new Paint(Paint.ANTI_ALIAS_FLAG);
		      text_paint_hp.setColor(0xFFFF0000); //red
		      text_paint_hp.setStyle(Style.FILL);
		      text_paint_hp.setTextSize(width * 0.21f);
		      Paint text_paint_hp_data = new Paint(Paint.ANTI_ALIAS_FLAG);
		      text_paint_hp_data.setColor(0xFFFF0000); //red
		      text_paint_hp_data.setStyle(Style.FILL);
		      text_paint_hp_data.setTextSize(width * 0.20f);
		      canvas.drawText("x"+String.valueOf(number_of_wall_hp_bar), 
		    		  wall_hp_bar_start_x_pos + 20 * width_per_unit_wall_hp_in_bar + width/12, hp_bar_start_y_pos + height/7, text_paint_hp);
		      canvas.drawText(String.valueOf(number_of_unit_wall_hp_in_bar_for_display)+"/20", 
		    		  wall_hp_bar_start_x_pos + width/3 + width/10, hp_bar_start_y_pos + hp_bar_height + 2 * (height / 7), text_paint_hp_data);
	      }
	   	  
	   	  // Draw the defend wall bomb effect
	      if(if_wall_effect){
	    	  canvas.drawBitmap(bomb_1, (0 - 1) * width/2, game.defend_wall_line, p3);
	    	  canvas.drawBitmap(bomb_2, 0  * width/2, game.defend_wall_line, p3);
	    	  Paint white_paint = new Paint();
	    	  white_paint.setColor(0xFFFFFFFF);
	    	  white_paint.setAlpha(30);	       
		      canvas.drawRect(0, height, getWidth(), 9*height, white_paint);
	      }
	      
	      if(if_wall_effect_second){
	    	  canvas.drawBitmap(bomb_1, (float) (0 * width/2), game.defend_wall_line, p3);
	    	  canvas.drawBitmap(bomb_2, (float) (-1 * width/2), game.defend_wall_line, p3);
	      }
	      
	      // Draw the crash of the defend wall
	      if(if_wall_crashed){
	    	  Bitmap crash_smoke = BitmapFactory.decodeResource(getResources(), R.drawable.smoke_coil);
	    	  crash_smoke = Bitmap.createScaledBitmap(crash_smoke, getWidth()/8, getWidth()/8, false);
	    	  Bitmap blast = BitmapFactory.decodeResource(getResources(), R.drawable.blast);
	    	  blast = Bitmap.createScaledBitmap(blast, getWidth()/8, getWidth()/8, false);
	    	  for(int j = 1; j <= 10; j++){
	  	    		canvas.drawBitmap(crash_smoke, (j - 1) * width/2, game.defend_wall_line, p);
	  	    		canvas.drawBitmap(blast, (j - 1) * width/2, game.defend_wall_line, p);
	    	  }
	    	  Paint white_paint = new Paint();
	    	  white_paint.setColor(0xFFFFFFFF);
	    	  white_paint.setAlpha(50);	       
		      canvas.drawRect(0, height, getWidth(), 9*height, white_paint);
	      }

	      // Draw not enough energy effect 
	      if (game.not_enough_energy_fire){
	    	  canvas.drawText("No energy to fire!", (float) (getWidth()/2 - getWidth()/5.6), getHeight()/8, red);
	    	  Paint normal_paint = new Paint();
	    	  Paint no_energy_paint = new Paint();
	    	  no_energy_paint.setAlpha(50);
		      canvas.drawRect(0, 0, getWidth(), getHeight(), no_energy_paint);
		      
		      Bitmap no_enregy = BitmapFactory.decodeResource(getResources(), R.drawable.no_energy);
		      //no_enregy = Bitmap.createScaledBitmap(no_enregy, getWidth()/2, getWidth()/2, false);//120
			  canvas.drawBitmap(no_enregy, getWidth()/2 - width - width/4, getHeight()/2 - height - height/6, normal_paint);
			  Bitmap shake_now = BitmapFactory.decodeResource(getResources(), R.drawable.shake_now);
			  canvas.drawBitmap(shake_now, getWidth()/2 - width - width/4, getHeight()/2 - height/6, normal_paint);
	      }
	      if(game.not_enough_energy_jump && if_no_energy_bomb_black_bg){
	    	  canvas.drawText("No energy to throw a super bomb!", (float)(getWidth()/2 - getWidth()/2.667), (float) (getHeight()/6.667), red);
	    	  Paint normal_paint = new Paint();
	    	  Paint no_energy_paint = new Paint();
	    	  no_energy_paint.setAlpha(50);
		      canvas.drawRect(0, 0, getWidth(), getHeight(), no_energy_paint);
		      
		      Bitmap no_enregy = BitmapFactory.decodeResource(getResources(), R.drawable.no_energy);
		      //no_enregy = Bitmap.createScaledBitmap(no_enregy, getWidth()/2, getWidth()/2, false);//120
			  canvas.drawBitmap(no_enregy, getWidth()/2 - width - width/4, getHeight()/2 - height - height/6, normal_paint);
			  Bitmap shake_now = BitmapFactory.decodeResource(getResources(), R.drawable.shake_now);
			  canvas.drawBitmap(shake_now, getWidth()/2 - width - width/4, getHeight()/2 - height/6, normal_paint);
	      }
	      
	      // Draw the ready go effect
	      Paint ready_go_paint = new Paint();
	      ready_go_paint.setAlpha(70);
	      Bitmap are_you_ready = BitmapFactory.decodeResource(getResources(), R.drawable.ready);
		  are_you_ready = Bitmap.createScaledBitmap(are_you_ready, (int) (getWidth()/1.6), getHeight()/8, false);//150 x 50
	      if(game.if_ready_time_go_over){// game starts
	    	  Bitmap b_go = BitmapFactory.decodeResource(getResources(), R.drawable.go);
	    	  b_go = Bitmap.createScaledBitmap(b_go, getWidth()/2, getWidth()/2, false);//120
			  canvas.drawBitmap(b_go, getWidth()/2 - width - width/2, getHeight()/2 - height - height/2, p);
	      }
	      if(game.if_ready_time_1_over){
			  Bitmap b_1 = BitmapFactory.decodeResource(getResources(), R.drawable.ready_1);
			  b_1 = Bitmap.createScaledBitmap(b_1, getWidth()/3, getWidth()/3, false);//80
			  canvas.drawRect(0, 0, getWidth(), getHeight(), ready_go_paint);
			  canvas.drawBitmap(b_1, getWidth()/2 - width, getHeight()/2 - height, p);
			  canvas.drawBitmap(are_you_ready, getWidth()/2 - width - width/2, getHeight()/2 - 2*height - height/2, p);
			  
			  Bitmap shake = BitmapFactory.decodeResource(getResources(), R.drawable.raise);
			  canvas.drawBitmap(shake, getWidth()/2 - width + width/4, getHeight()/2 + height, p);
	      }
	      if(game.if_ready_time_2_over){
	    	  Bitmap b_2 = BitmapFactory.decodeResource(getResources(), R.drawable.ready_2);
	    	  b_2 = Bitmap.createScaledBitmap(b_2, getWidth()/3, getWidth()/3, false);//80
	    	  canvas.drawRect(0, 0, getWidth(), getHeight(), ready_go_paint);
			  canvas.drawBitmap(b_2, getWidth()/2 - width, getHeight()/2 - height, p);
			  canvas.drawBitmap(are_you_ready, getWidth()/2 - width - width/2, getHeight()/2 - 2*height - height/2, p);
			  
			  Bitmap shake = BitmapFactory.decodeResource(getResources(), R.drawable.flip);
			  canvas.drawBitmap(shake, getWidth()/2 - width/2, getHeight()/2 + height, p);
		  }
	      if(game.if_ready_time_3_over){
	    	  Bitmap b_3 = BitmapFactory.decodeResource(getResources(), R.drawable.ready_3);
	    	  b_3 = Bitmap.createScaledBitmap(b_3, getWidth()/3, getWidth()/3, false);//80
	    	  canvas.drawRect(0, 0, getWidth(), getHeight(), ready_go_paint);
			  canvas.drawBitmap(b_3, getWidth()/2 - width, getHeight()/2 - height, p);
			  canvas.drawBitmap(are_you_ready, getWidth()/2 - width - width/2, getHeight()/2 - 2*height - height/2, p);
			  
			  Bitmap shake = BitmapFactory.decodeResource(getResources(), R.drawable.shake);
			  canvas.drawBitmap(shake, getWidth()/2 - width + width/7, getHeight()/2 + height, p);
		  }
	      
	      // Draw finger position and attack area
	      if(tap_down_flag && game.if_ready_time_is_over){
	    	  Paint tap_paint = new Paint();
	    	  tap_paint.setAntiAlias(true);
	    	  tap_paint.setColor(0xFFFFFF00);
	    	  tap_paint.setAlpha(80);	       
	    	  
	    	  Paint column_paint = new Paint();
	    	  column_paint.setAntiAlias(true);
	    	  //column_paint.setColor(0x00000000);
    		  column_paint.setAlpha(40);
    		  
    		  Paint green_line = new Paint();
    		  green_line.setAlpha(150);
    		  green_line.setColor(0xFF5AFA39);
    	      
    		  Bitmap arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
    		  arrow = Bitmap.createScaledBitmap(arrow, getWidth()/11, getWidth()/11, false);
    		  
    		  int arrow_w = arrow.getWidth();
    	      int arrow_h = arrow.getHeight();
    	      // rotate the arrow image
    	      arrow = Bitmap.createBitmap(arrow, 0, 0, arrow_w, arrow_h, mtx, true);
    	      
	    	  if((mouse_x <= width_for_grid + 1) && (mouse_y > 1 * height) && (mouse_y <= 9 * height)){
	    		  canvas.drawLine(0, 2 * height + 1, 0 * width_for_grid + 1, 9 * height, green_line); // left line
	    		  canvas.drawRect(0, 2*height, width_for_grid + 1, 9*height, column_paint);
	    		  canvas.drawLine(1 * width_for_grid, 2 * height + 1, 1 * width_for_grid + 1, 9 * height, green_line); // right line
	    		  // Draw arrow at tapped column.
		    	  canvas.drawBitmap(arrow, width/5 + width/8, 2 * height + 2*height/3, p);
	    	  }
	    	  else if((width_for_grid + 1 < mouse_x) && (mouse_x <= 2 * width_for_grid + 1) && (mouse_y > 1 * height) && (mouse_y <= 9 * height)){
	    		  canvas.drawLine(1 * width_for_grid, 2 * height + 1, 1 * width_for_grid + 1, 9 * height, green_line);
	    		  canvas.drawRect(width_for_grid, 2*height, 2 * width_for_grid + 1, 9*height, column_paint);
	    		  canvas.drawLine(2 * width_for_grid, 2 * height + 1, 2 * width_for_grid + 1, 9 * height, green_line);
	    		  // Draw arrow at tapped column.
		    	  canvas.drawBitmap(arrow, (2 - 1) * width + width/4 + width/10, 2 * height + 2*height/3, p);
	    	  }
	    	  else if((2 * width_for_grid + 1 < mouse_x) && (mouse_x <= 3 * width_for_grid + 1) && (mouse_y > 1 * height) && (mouse_y <= 9 * height)){
	    		  canvas.drawLine(2 * width_for_grid, 2 * height + 1, 2 * width_for_grid + 1, 9 * height, green_line);
	    		  canvas.drawRect(2 * width_for_grid, 2 * height, 3 * width_for_grid + 1, 9*height, column_paint);
	    		  canvas.drawLine(3 * width_for_grid, 2 * height + 1, 3 * width_for_grid + 1, 9 * height, green_line);
	    		  // Draw arrow at tapped column.
		    	  canvas.drawBitmap(arrow, (3 - 1) * width + width/4 + width/10, 2 * height + 2*height/3, p);
	    	  }
	    	  else if((3 * width_for_grid + 1 < mouse_x) && (mouse_x <= 4 * width_for_grid + 1) && (mouse_y > 1 * height) && (mouse_y <= 9 * height)){
	    		  canvas.drawLine(3 * width_for_grid, 2 * height + 1, 3 * width_for_grid + 1, 9 * height, green_line);
	    		  canvas.drawRect(3 * width_for_grid, 2 * height, 4 * width_for_grid + 1, 9*height, column_paint);
	    		  canvas.drawLine(4 * width_for_grid, 2 * height + 1, 4 * width_for_grid + 1, 9 * height, green_line);
	    		  // Draw arrow at tapped column.
		    	  canvas.drawBitmap(arrow, (4 - 1) * width + width/4 + width/10, 2 * height + 2*height/3, p);
			  }
	    	  else if((4 * width_for_grid + 1 < mouse_x) && (mouse_x <= 5 * width_for_grid) && (mouse_y > 1 * height) && (mouse_y <= 9 * height)){
	    		  canvas.drawLine(4 * width_for_grid, 2 * height + 1, 4 * width_for_grid - 1, 9 * height, green_line); // left line
	    		  canvas.drawRect(4 * width_for_grid, 2 * height, 5 * width_for_grid - 1, 9 * height, column_paint);
	    		  canvas.drawLine(5 * width_for_grid, 2 * height + 1, 5 * width_for_grid - 1, 9 * height, green_line); // right line
	    		  // Draw arrow at tapped column.
		    	  canvas.drawBitmap(arrow, (5 - 1) * width + width/4 + width/10, 2 * height + 2*height/3, p);
	    	  }
	    	  
	    	  // Draw tapping circle area
	    	  canvas.drawCircle(mouse_x, mouse_y, getWidth()/12, tap_paint);
	    	  // Draw the cross
	    	  Bitmap focus = BitmapFactory.decodeResource(getResources(), R.drawable.focus2);
	    	  focus = Bitmap.createScaledBitmap(focus, getWidth()/4, getWidth()/4, false);
	    	  int focus_w = focus.getWidth();
	    	  int focus_h = focus.getHeight();
	    	  canvas.drawBitmap(focus, mouse_x - focus_w/2, mouse_y - focus_h/2, p);
	    	  
	    	  Bitmap cross = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
	    	  cross = Bitmap.createScaledBitmap(cross, getWidth()/6, getWidth()/6, false);
	    	  int cross_w = cross.getWidth();
	    	  int cross_h = cross.getHeight();
	    	  canvas.drawBitmap(cross, mouse_x - cross_w/2, mouse_y - cross_h/2, p);
	      }
	      
	      // Draw the alarm clock image if time <= 10s
	      if(game.remaining_time <= 10){
	    	  Bitmap alarm = BitmapFactory.decodeResource(getResources(), R.drawable.alarm);
	    	  alarm = Bitmap.createScaledBitmap(alarm, getWidth()/10, getWidth()/10, false);// 30x30
		      canvas.drawBitmap(alarm, (2 - 1) * width, getHeight()/24, p);
	      }
		      
	      // Draw the countdown timer 
	      int rect_w = (int) (4 * width/5);
	      int rect_h = (int) (4 * width/5);
	      int rect_start_x_pos = (int) getWidth()-(getWidth()/50*10); //
	      int rect_start_y_pos = (int) height/20;

	      RectF mBigOval = new RectF(rect_start_x_pos, rect_start_y_pos, rect_start_x_pos + rect_w, rect_start_y_pos + rect_h);
	      Paint time_path_basic = new Paint(Paint.ANTI_ALIAS_FLAG);
	        
	      DashPathEffect dashPath = new DashPathEffect(new float[]{5,5}, (float)1.0);
	      PathEffect path = new PathEffect();
	      time_path_basic.setPathEffect(path);
	      time_path_basic.setStyle(Style.FILL_AND_STROKE);
	      if(game.remaining_time > 10){
	    	  time_path_basic.setColor(Color.GREEN);
	      }
	      else{
	    	  time_path_basic.setColor(Color.RED);
	      }
	      time_path_basic.setAlpha(80);
	      canvas.drawArc(mBigOval, 0, 360, true, time_path_basic);// basic pie
	      time_path_basic.setAlpha(150);
	      canvas.drawArc(mBigOval, 0, 6 * game.remaining_time, true, time_path_basic);// second pie
	      Paint display_time_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      display_time_paint.setStyle(Style.FILL);
	      display_time_paint.setTextSize(width * 0.22f);
	     // display_time_paint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
	      Typeface font = Typeface.createFromAsset(getContext().getAssets(), "KOMIKAX_.ttf");
	      display_time_paint.setTypeface(font);
	      display_time_paint.setAlpha(150);
	      display_time_paint.setColor(Color.YELLOW);
	      canvas.drawText(String.valueOf(game.remaining_time), rect_start_x_pos + rect_w/3, rect_start_y_pos + 3*rect_h/5, display_time_paint); 
	      
	      invalidate();
	  }
	   
	   // make the (n+1)th invader be the nth invader.
	   private void update_y_pos_in_column_after_killed (int[] temp_y_pos, int[] invader_y_pos){
		   for(int i = 0; i < invader_y_pos.length; i++){
			   temp_y_pos[i] = invader_y_pos[i];
		   }
		  
		   for(int j = 0; j < temp_y_pos.length - 1; j++){
			   if(j == temp_y_pos.length - 2){
				   invader_y_pos[j] = game.initial_y_pos;
			   }
			   else{
				   invader_y_pos[j] = temp_y_pos[j+1];
			   }
		   }		   
	   }
	   
	   // draw invaders in each column.
	   private void draw_updated_y_pos_innvaders_in_column (Canvas canvas){
	      Paint p = new Paint();
	      Bitmap invader_1 = BitmapFactory.decodeResource(getResources(), R.drawable.space_invader_1);
	      Bitmap invader_2 = BitmapFactory.decodeResource(getResources(), R.drawable.space_invader_2);
	      invader_1 = Bitmap.createScaledBitmap(invader_1, getWidth()/6, getWidth()/6, false); //40x40
	      invader_2 = Bitmap.createScaledBitmap(invader_2, getWidth()/6, getWidth()/6, false); //40x40
	      
	      for(int j = 0; j < game.number_of_invaders_in_column_1; j++){
		    	if(game.time_counter2 % 2 == 0){
		    		canvas.drawBitmap(invader_1, (1 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_1[j], p);
		    	} // getWidth()/24 = 10
		    	else {
		    		canvas.drawBitmap(invader_2, (1 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_1[j], p);
		    	}
	      }
	      
	      for(int j = 0; j < game.number_of_invaders_in_column_2; j++){
		    	if(game.time_counter2 % 2 == 0){
		    		canvas.drawBitmap(invader_1, (2 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_2[j], p);
		    	} // getWidth()/24 = 10
		    	else {
		    		canvas.drawBitmap(invader_2, (2 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_2[j], p);
		    	}
		  }
	      
	      for(int j = 0; j < game.number_of_invaders_in_column_3; j++){
		    	if(game.time_counter2 % 2 == 0){
		    		canvas.drawBitmap(invader_1, (3 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_3[j], p);
		    	} // getWidth()/24 = 10
		    	else {
		    		canvas.drawBitmap(invader_2, (3 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_3[j], p);
		    	}
		  }
	      
	      for(int j = 0; j < game.number_of_invaders_in_column_4; j++){
		    	if(game.time_counter2 % 2 == 0){
		    		canvas.drawBitmap(invader_1, (4 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_4[j], p);
		    	} // getWidth()/24 = 10
		    	else {
		    		canvas.drawBitmap(invader_2, (4 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_4[j], p);
		    	}
	      }
	      
	      for(int j = 0; j < game.number_of_invaders_in_column_5; j++){
		    	if(game.time_counter2 % 2 == 0){
		    		canvas.drawBitmap(invader_1, (5 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_5[j], p);
		    	} // getWidth()/24 = 10
		    	else {
		    		canvas.drawBitmap(invader_2, (5 - 1) * width + getWidth()/24, game.y_pos_of_invader_in_column_5[j], p);
		    	}
	      }
	   }
	   
	   @Override
	   public boolean onTouchEvent(MotionEvent event) {
		  super.onTouchEvent(event);
		  Log.d(TAG, "onTouchEvent: x " + event.getX() + ", y " + event.getY());
		  mouse_x = event.getX();
		  mouse_y = event.getY();
		  
		  if (event.getAction() == MotionEvent.ACTION_DOWN){
			  game.screenPressed = true;		  
			  tap_down_flag = true;
			  tap_up_flag = false;
		  }
		  else if (event.getAction() == MotionEvent.ACTION_UP)
	      { 
			  game.screenPressed = false;
			  tap_up_flag = true;
			  tap_down_flag = false;
	      }

		 if (!game.game_over_flag && game.if_ready_time_is_over){// when the game is not over and the game hasn't started.
			      //if (mouse_y >= 2 * height && mouse_y <= 9 * height && tap_up_flag){ // TEST
			 	  //if(!(event.getY() >= 9 * height + 5 && event.getX() >= 4 * width) && tap_up_flag){
			 		  //kill_an_invader();
			 	  //}
			 	
			      // kill invader when tapping on the board, prevent bomb at the same time when testing.
			 	  if (mouse_y > 1 * height && mouse_y <= 9 * height){// area that can kill invaders when tapped
			 		 if(tap_up_flag && game.if_ready_time_is_over){
			 			 check_if_kill_an_invader();
			 		 }
			 	  }
			 	  
			      // throw a bomb by tapping, TEST
			      /*if (mouse_y <= 1 * height && tap_down_flag){
			 		  kill_one_line_invaders();
			 	  }*/
				  
				  // pause button
				  if (event.getY() >= 9 * height + 5 && event.getX() >= 4 * width){
					  game.finish();
				  }
				  
				  // add energy at bottom, TEST
				  /*else if (event.getY() >= 9 * height && event.getX() < 4 * width){
					  game.energy = game.energy + 50;
					  game.not_enough_energy_fire = false;
					  game.not_enough_energy_jump = false;
				  }*/
				  
				  // tap to get bonus , for test
				  /*if (event.getY() >= height && event.getY() <= 2 * height){
					  if(if_bonus_10_energy && event.getX() >= bonus_x_pos_left && event.getX() <= bonus_x_pos_right){
						  game.energy = game.energy + 10;
						  if_bonus_10_energy = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  
						  game.not_enough_energy_jump = false;
						  game.not_enough_energy_fire = false;
						  if_bonus_star = true;
					  }
					  if(if_bonus_20_energy && event.getX() >= bonus_x_pos_left && event.getX() <= bonus_x_pos_right){
						  game.energy = game.energy + 20;
						  if_bonus_20_energy = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  
						  game.not_enough_energy_jump = false;
						  game.not_enough_energy_fire = false;
						  if_bonus_star = true;
					  }
					  if(if_bonus_bomb && event.getX() >= bonus_x_pos_left && event.getX() <= bonus_x_pos_right){
						  // balance the data in kill_one_line_invaders() function.
						  game.energy = game.energy + 10;
						  game.number_of_jump--;
						  kill_one_line_invaders();
						  if_bonus_bomb = false;
						  game.point = game.point + 30;
						  game.number_of_bonus++;
						  game.play_bonus_sound_effect();
						  if_bonus_star = true;
					  }
				  }*/
		  }
	      return true;
	   }
	   
	   // kill an invader based on finger position.
	   protected void check_if_kill_an_invader(){
			if(game.energy >= 2){
	    	  game.number_of_tap++;
			  game.number_of_killed++;
			  game.point = game.point + 10; 
	  		  game.energy = game.energy - 2;
	  		  if_no_energy_bomb_black_bg = false;
	  		  game.vibrate(50);
	  		  game.play_sound_effect();
			  if(mouse_x <= width){
				  // for data structure
				  game.number_of_invaders_in_column_1--;
				  // for render image
				  if_fire_effect_1 = true;
	  			  if_kill_invader_1_second = true;
	  			  kill_effect_y_pos_1 = game.y_pos_of_invader_in_column_1[0];
	  			  update_y_pos_in_column_after_killed(temp_y_pos_in_column_1, game.y_pos_of_invader_in_column_1);
	    	  }
	    	  else if((width < mouse_x) && (mouse_x <= 2 * width)){
	    		  // for data structure
	    		  game.number_of_invaders_in_column_2--;
	    		  // for render image
				  if_fire_effect_2 = true;
	  			  if_kill_invader_2_second = true;
	  			  kill_effect_y_pos_2 = game.y_pos_of_invader_in_column_2[0];
	  			  update_y_pos_in_column_after_killed(temp_y_pos_in_column_2, game.y_pos_of_invader_in_column_2);
	    	  }
	    	  else if((2 * width < mouse_x) && (mouse_x <= 3 * width)){
	    		  // for data structure
	    		  game.number_of_invaders_in_column_3--;
				  // for render image
				  if_fire_effect_3 = true;
	  			  if_kill_invader_3_second = true;
	  			  kill_effect_y_pos_3 = game.y_pos_of_invader_in_column_3[0];
	  			  update_y_pos_in_column_after_killed(temp_y_pos_in_column_3, game.y_pos_of_invader_in_column_3);
	    	  }
	    	  else if((3 * width < mouse_x) && (mouse_x <= 4 * width)){
	    		  // for data structure
	    		  game.number_of_invaders_in_column_4--;
				  // for render image
				  if_fire_effect_4 = true;
	  			  if_kill_invader_4_second = true;
	  			  kill_effect_y_pos_4 = game.y_pos_of_invader_in_column_4[0];
	  			  update_y_pos_in_column_after_killed(temp_y_pos_in_column_4, game.y_pos_of_invader_in_column_4);
			  }
	    	  else if((4 * width < mouse_x) && (mouse_x <= 5 * width)){
	    		  // for data structure
	    		  game.number_of_invaders_in_column_5--;
				  // for render image
				  if_fire_effect_5 = true;
	  			  if_kill_invader_5_second = true;
	  			  kill_effect_y_pos_5 = game.y_pos_of_invader_in_column_5[0];
	  			  update_y_pos_in_column_after_killed(temp_y_pos_in_column_5, game.y_pos_of_invader_in_column_5);
	    	  }
			}
			else if (game.energy < 2){
	    		game.not_enough_energy_fire = true; 
	    		game.play_no_energy_sound_effect();
		    	//game.toastShow("Not enough energy to fire!");
			}
	   }
	   
	   // kill one line of invaders.
	   protected void kill_one_line_invaders(){
		   if(game.if_ready_time_is_over){
			  if_no_energy_bomb_black_bg = true;
			  if((game.number_of_invaders_in_column_1 > 0
	    	   || game.number_of_invaders_in_column_2 > 0
	    	   || game.number_of_invaders_in_column_3 > 0
	    	   || game.number_of_invaders_in_column_4 > 0
	    	   || game.number_of_invaders_in_column_5 > 0) && game.energy >= 10){
				    game.vibrate(500);
				    game.play_bomb_sound_effect();
				    game.number_of_jump++;
			  		game.energy = game.energy - 10;
			  		if_bomb_effect = true;
			  		if(game.number_of_invaders_in_column_1 > 0){
			  			kill_effect_y_pos_1 = game.y_pos_of_invader_in_column_1[0];
			  			game.number_of_invaders_in_column_1--;
			  			game.point = game.point + 10; 
			  			game.number_of_killed++;
			  			if_kill_invader_1_second = true;
			  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_1, game.y_pos_of_invader_in_column_1);
			  		}
			  		if(game.number_of_invaders_in_column_2 > 0){
			  			kill_effect_y_pos_2 = game.y_pos_of_invader_in_column_2[0];
			  			game.number_of_invaders_in_column_2--;
			  			game.point = game.point + 10; 
			  			game.number_of_killed++;
			  			if_kill_invader_2_second = true;
			  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_2, game.y_pos_of_invader_in_column_2);
			  		}
			  		if(game.number_of_invaders_in_column_3 > 0){
			  			kill_effect_y_pos_3 = game.y_pos_of_invader_in_column_3[0]; 
			  			game.number_of_invaders_in_column_3--;
			  			game.point = game.point + 10; 
			  			game.number_of_killed++;
			  			if_kill_invader_3_second = true;
			  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_3, game.y_pos_of_invader_in_column_3);
			  		}
			  		if(game.number_of_invaders_in_column_4 > 0){
			  			kill_effect_y_pos_4 = game.y_pos_of_invader_in_column_4[0]; 
			  			game.number_of_invaders_in_column_4--;
			  			game.point = game.point + 10; 
			  			game.number_of_killed++;
			  			if_kill_invader_4_second = true;
			  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_4, game.y_pos_of_invader_in_column_4);	
			  		}
			  		if(game.number_of_invaders_in_column_5 > 0){
			  			kill_effect_y_pos_5 = game.y_pos_of_invader_in_column_5[0]; 
			  			game.number_of_invaders_in_column_5--;
			  			game.point = game.point + 10; 
			  			game.number_of_killed++;
			  			if_kill_invader_5_second = true;
			  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_5, game.y_pos_of_invader_in_column_5);
			  		}
			    }
			    else if (game.energy < 10){
			    	game.not_enough_energy_jump = true;
			    	game.play_no_energy_sound_effect();
			    	//game.toastShow("Not enough energy to fire!");
			    }
		   }
	   }
	   
	   // kill one line of invaders by defend wall.
	   protected void wall_kill_one_line_invaders(){
		   if(game.if_ready_time_is_over && 
			 (game.number_of_invaders_in_column_1 > 0
		   || game.number_of_invaders_in_column_2 > 0
		   || game.number_of_invaders_in_column_3 > 0
	  	   || game.number_of_invaders_in_column_4 > 0
		   || game.number_of_invaders_in_column_5 > 0)){
		  		if_wall_effect = true;
		  		game.vibrate(200);
	 			game.play_wall_crash_sound_effect();
	 			// if there is not enough energy, we kill the left invader first ect.
		  		if((game.y_pos_of_invader_in_column_1[0] + game.invader_moving_speed <= game.alert_line) && game.wall_hp > 0){
		  			game.wall_hp--;
		  			game.point = game.point + 10; 
		  			game.number_of_killed++;
		  			if_kill_invader_1_second = true;
		  			game.number_of_invaders_in_column_1--;
		  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_1, game.y_pos_of_invader_in_column_1);
		  			kill_effect_y_pos_1 = game.y_pos_of_invader_in_column_1[0] - game.invader_moving_speed;
		  		}
		  		if((game.y_pos_of_invader_in_column_2[0] + game.invader_moving_speed <= game.alert_line) && game.wall_hp > 0){
		  			game.wall_hp--;
		  			game.point = game.point + 10; 
		  			game.number_of_killed++;
		  			if_kill_invader_2_second = true;
		  			game.number_of_invaders_in_column_2--;
		  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_2, game.y_pos_of_invader_in_column_2);
		  			kill_effect_y_pos_2 = game.y_pos_of_invader_in_column_2[0] - game.invader_moving_speed;
		  		}
		  		if((game.y_pos_of_invader_in_column_3[0] + game.invader_moving_speed <= game.alert_line) && game.wall_hp > 0){
		  			game.wall_hp--;
		  			game.point = game.point + 10; 
		  			game.number_of_killed++;
		  			if_kill_invader_3_second = true;
		  			game.number_of_invaders_in_column_3--;
		  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_3, game.y_pos_of_invader_in_column_3);
		  			kill_effect_y_pos_3 = game.y_pos_of_invader_in_column_3[0] - game.invader_moving_speed;
		  		}
		  		if((game.y_pos_of_invader_in_column_4[0] + game.invader_moving_speed <= game.alert_line) && game.wall_hp > 0){
		  			game.wall_hp--;
		  			game.point = game.point + 10; 
		  			game.number_of_killed++;
		  			if_kill_invader_4_second = true;
		  			game.number_of_invaders_in_column_4--;
		  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_4, game.y_pos_of_invader_in_column_4);
		  			kill_effect_y_pos_4 = game.y_pos_of_invader_in_column_4[0] - game.invader_moving_speed;
		  		}
		  		if((game.y_pos_of_invader_in_column_5[0] + game.invader_moving_speed <= game.alert_line) && game.wall_hp > 0){
		  			game.wall_hp--;
		  			game.point = game.point + 10; 
		  			game.number_of_killed++;
		  			if_kill_invader_5_second = true;
		  			game.number_of_invaders_in_column_5--;
		  			update_y_pos_in_column_after_killed(temp_y_pos_in_column_5, game.y_pos_of_invader_in_column_5);
		  			kill_effect_y_pos_5 = game.y_pos_of_invader_in_column_5[0] - game.invader_moving_speed;
		  		}
		   }
	   }
	   
	   public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	
	    
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
	        /*
	         * record the accelerometer data, the event's timestamp as well as
	         * the current time. The latter is needed so we can calculate the
	         * "present" time during rendering. In this application, we need to
	         * take into account how the screen is rotated with respect to the
	         * sensors (which always return data in a coordinate space aligned
	         * to with the screen in its native orientation).
	         */
			long curTime = System.currentTimeMillis();
			float z = event.values[2];
	        switch (game.mDisplay.getOrientation()) {
	            case Surface.ROTATION_0:
	                mSensorX = event.values[0];
	                mSensorY = event.values[1];
	                break;
	            case Surface.ROTATION_90:
	                mSensorX = -event.values[1];
	                mSensorY = event.values[0];
	                break;
	            case Surface.ROTATION_180:
	                mSensorX = -event.values[0];
	                mSensorY = -event.values[1];
	                break;
	            case Surface.ROTATION_270:
	                mSensorX = event.values[1];
	                mSensorY = -event.values[0];
	                break;
	        }
	        
		    if ((curTime - lastUpdate) > 250 && !game.screenPressed) {

		    	if (z * last_z < 0 && ((z < -5 && last_z > 5 ) || (z > 5 && last_z < -5 ))) {
	            	
	            	flippingCounts++;
	            }
	            
	            if (flippingCounts == 2){
	            	game.energy += 10;
	            	game.reverses++;
	            	flippingCounts = 0;
	            	game.play_flip_effect();
	            	game.vibrate(150);
	            	game.not_enough_energy_fire = false;
	            	if(game.energy >= 10){
						game.not_enough_energy_jump = false;
					}
	            }
	            lastUpdate = curTime;
	            last_z = z;
		    }
	        
	        mSensorTimeStamp = event.timestamp;
	        mCpuTimeStamp = System.nanoTime();	
		}	   
	}