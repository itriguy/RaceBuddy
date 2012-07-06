package com.xmatters.android.racebuddy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xmatters.android.racebuddy.R;
import com.xmatters.android.racebuddy.RaceBuddyApplication;
import com.xmatters.android.racebuddy.utility.UpdaterService;

/**
 * Base activity that can be used for other activities to extend.
 */
public class BaseActivity extends Activity {
    RaceBuddyApplication application; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (RaceBuddyApplication) getApplication(); 
    }

    // Called only once first time menu is clicked on
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { 
        getMenuInflater().inflate(R.menu.racesmenu, menu);
        return true;
    }

    // Called every time user clicks on a menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { 

        switch(item.getItemId()) {
            case R.id.itemPrefs:
                startActivity(new Intent(this, PrefsActivity.class)
                                  .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.itemToggleService:
                if(application.isServiceRunning()) {
                    stopService(new Intent(this, UpdaterService.class));
                } else {
                    startService(new Intent(this, UpdaterService.class));
                }
                break;
            case R.id.itemAdd:
                startActivity(new Intent(this, RaceActivity.class)
                                  .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.itemEdit:
                startActivity(new Intent(this, RaceActivity.class)
                                  .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.itemDelete:
                //((RaceBuddyApplication) getApplication()).getStatusData().delete();
                //TODO DELETE
                Toast.makeText(this, R.string.title_delete_race, Toast.LENGTH_LONG).show();
                break;
            case R.id.itemSort:
                startActivity(new Intent(this, RaceActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;

        }
        return true;
    }

    // Called every time menu is opened
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) { 
        MenuItem toggleItem = menu.findItem(R.id.itemToggleService); 
        if(application.isServiceRunning()) { 
            toggleItem.setTitle(R.string.title_services_stop);
            toggleItem.setIcon(android.R.drawable.ic_media_pause);
        }
        else { 
            toggleItem.setTitle(R.string.title_services_start);
            toggleItem.setIcon(android.R.drawable.ic_media_play);
        }
        return true;
    }

}