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
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ShakeSurvivalAbout extends Activity implements OnClickListener{
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.survival_about);
      
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      View backButton = findViewById(R.id.survival_about_back_button);
      backButton.setOnClickListener(this);
      View acButton = findViewById(R.id.survival_about_acknowledgements_button);
      acButton.setOnClickListener(this);
      
      Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
      ((TextView) backButton).setTypeface(button_font);
      ((TextView) acButton).setTypeface(button_font);
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
   
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.survival_about_back_button:
	    	  finish();
	    	  Intent exit = new Intent(this, ShakeSurvivalMain.class);
	          startActivity(exit);
	          break;
	      case R.id.survival_about_acknowledgements_button:
	    	  finish();
	    	  Intent ac = new Intent(this, ShakeSurvivalAcknowledgements.class);
	          startActivity(ac);
	          break;
	      default:
				finish();
		}
	}
}
