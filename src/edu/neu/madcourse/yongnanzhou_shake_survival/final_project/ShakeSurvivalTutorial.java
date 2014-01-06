package edu.neu.madcourse.yongnanzhou_shake_survival.final_project;

import java.util.ArrayList;
import edu.neu.madcourse.yongnanzhou_shake_survival.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ShakeSurvivalTutorial extends Activity implements OnClickListener {
	
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private ViewGroup main, group;  
    private ImageView imageView;  
    private ImageView[] imageViews; 
    
    public static final String SHAKE_SURVIVAL_TUTORIAL = "shake_survival_tutorial";
	public static final String IF_FIRST_TIME_COME = "if_first_time_come";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_main, null));
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item01, null));  
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item02, null));  
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item03, null));  
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item04, null));
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item05, null));
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item06, null));
        pageViews.add(inflater.inflate(R.layout.survival_tutorial_item07, null));
  
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.survival_tutorial, null);  
        
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);  
  
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(ShakeSurvivalTutorial.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            if (i == 0) {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
            group.addView(imageViews[i]);  
        }  
  
        setContentView(main);  
        View backButton = findViewById(R.id.tutorial_back_button);
        backButton.setOnClickListener(this);
        Typeface button_font = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");  
        ((TextView) backButton).setTypeface(button_font); 
        
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        
        // do not open the tutorial next time when open the game 
        /*SharedPreferences savedTutorialData;
        savedTutorialData = getSharedPreferences(ShakeSurvivalMain.SHAKE_SURVIVAL_MENU, 0);
        
		SharedPreferences.Editor editor = savedTutorialData.edit();
		editor.putBoolean(ShakeSurvivalMain.IF_FIRST_TIME_COME, false);
		editor.commit();*/
    }
    
    class GuidePageAdapter extends PagerAdapter {  
    	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0]  
                        .setBackgroundResource(R.drawable.page_indicator_focused);  
                if (arg0 != i) {  
                    imageViews[i]  
                            .setBackgroundResource(R.drawable.page_indicator);  
                }  
            }
          }
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
	      case R.id.tutorial_back_button:
	    	  finish();
	    	  Intent exit = new Intent(this, ShakeSurvivalMain.class);
	          startActivity(exit);
	          break;
	      default:
	    	  finish();
		}
	}
}