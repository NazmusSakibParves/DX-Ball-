package parves.android.DxBall;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author S.M. NAZMUS SAKIB PARVES
 *
 */

public class DxBall_v3Activity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        final MediaPlayer mp = MediaPlayer.create(this, R.raw.soundbible);
	        setContentView(new GameCanvas(this, mp));
	    }
	    
	    @Override
	    protected void onPause() {
	    	
	    	super.onPause();
	    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	    }
	    
	    @Override
	    protected void onResume() {
	    	
	    	super.onResume();
	    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    }
}