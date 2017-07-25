package net.gnu.texteditor;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import net.gnu.common.activities.SampleActivityBase;
//import com.free.common.logger.Log;
import net.gnu.common.logger.LogFragment;
import net.gnu.common.logger.LogWrapper;
import net.gnu.common.logger.MessageOnlyLogFilter;
import net.gnu.texteditor.R;

import android.content.*;
import android.view.*;
import java.io.*;
import android.util.*;
import android.support.v4.app.*;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class TextEditorActivity extends SampleActivityBase {//

    private static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
	SlidingTabsFragment slideFrag;
	private FragmentManager supportFragmentManager;
	public Main main;
	//static Menu menu;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		new File(
			"/storage/emulated/0/AppProjects/0SearchExplore/PowerExplorer/Jota/Application/build/bin/resources.ap_")
			.delete();
		new File(
			"/storage/emulated/0/AppProjects/0SearchExplore/PowerExplorer/Jota/Application/build/bin/classes.dex")
			.delete();
		new File(
			"/storage/emulated/0/AppProjects/0SearchExplore/PowerExplorer/Jota/Application/build/bin/Application.apk")
			.delete();
		File ff = new File("/storage/emulated/0/.aide/enginecache");
		if (ff.exists()) {
			File[] fs = ff.listFiles();
			for (File f : fs) {
				f.delete();
			}
		}
		
        supportFragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
            slideFrag = new SlidingTabsFragment();
        } else {
			slideFrag = (SlidingTabsFragment) supportFragmentManager.findFragmentByTag("slideFrag");
		}
		FragmentTransaction transaction = supportFragmentManager.beginTransaction();
		transaction.replace(R.id.content_fragment, slideFrag, "slideFrag");
		transaction.commit();
		Intent intent = getIntent();
		setIntent(null);
		Log.d(TAG, "onCreate intent " + intent + ", savedInstanceState=" + savedInstanceState);
		if (savedInstanceState == null) {
			slideFrag.addTab(intent, "Untitled 1.txt");//, "utf-8", false, '\n');
		}
    }
	
	@Override
	protected void onStart() {
		Log.d(TAG, "onStart intent=" + getIntent() + ", main=" + main);
		super.onStart();
		if (main == null) {
			main = slideFrag.getCurFrag();
		}
	}
	
	@Override
	public void onResume() {
		Log.d(TAG, "onResume main=" + main);
		super.onResume();
		if (main == null) {
			main = slideFrag.getCurFrag();
		}
		Log.d(TAG, "onResume main=" + main);
	}
	
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//		Log.d(TAG, "onPrepareOptionsMenu " + menu);
//        
//        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
//        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
//        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);
//
//		//main.onPrepareOptionsMenu(menu);
//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.menu_toggle_log:
//                mLogShown = !mLogShown;
//                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
//                if (mLogShown) {
//					output.setVisibility(View.VISIBLE);
//                    output.setDisplayedChild(0);
//                } else {
//                    output.setVisibility(View.GONE);//.setDisplayedChild(0);
//                }
//                supportInvalidateOptionsMenu();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /** Create a chain of targets that will receive log data */
//    @Override
//    public void initializeLogging() {
//        // Wraps Android's native log framework.
//        LogWrapper logWrapper = new LogWrapper();
//        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
//        com.free.common.logger.Log.setLogNode(logWrapper);
//
//        // Filter strips out everything except the message text.
//        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
//        logWrapper.setNext(msgFilter);
//
//        // On screen logging via a fragment with a TextView.
//        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.log_fragment);
//        msgFilter.setNext(logFragment.getLogView());
//
//        Log.d(TAG, "Ready");
//    }
	
	public void quit() {
		Log.d(TAG, "quit " + main);
		Main.saved = 0;
		Main.count = slideFrag.pagerAdapter.getCount();
		for (int i = slideFrag.pagerAdapter.getCount() - 1; i >= 0; i--) {
			final Main item = slideFrag.pagerAdapter.getItem(i);
			item.confirmSave(item.mProcQuit);
		}
		//m.confirmSave(m.mProcQuit);
	}
	
	@Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent " + intent);
		super.onNewIntent(intent);
		//m.onNewIntent(intent);
		//intent = getIntent();
		//setIntent(null);
		if (intent != null && !Intent.ACTION_MAIN.equals(intent.getAction())) {
			slideFrag.addTab(intent, "Untitled 1.txt");//, "utf-8", false, '\n');
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		return main.onKeyDown(keyCode, event);
	}
	
	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
		return main.onKeyUp(keyCode, event);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		//this.menu = menu;
		Log.d(TAG, "onCreateOptionsMenu " + menu);
        super.onCreateOptionsMenu(menu);
		
        //getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
	
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		return main.onMenuItemSelected(featureId, item);
////		if (main.onMenuItemSelected(featureId, item)) {
////			return true;
////		} else {
////			return super.onMenuItemSelected(featureId, item);
////		}
//    }
}
