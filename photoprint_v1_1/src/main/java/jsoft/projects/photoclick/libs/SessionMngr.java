package jsoft.projects.photoclick.libs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import jsoft.projects.photoclick.Login;
import jsoft.projects.photoclick.TutorialViewPager;

import java.util.ArrayList;
import java.util.HashSet;


public class SessionMngr {
	
	private SharedPreferences prefs;
	
	Context _cntx;
	public SessionMngr(Context cntx){
		_cntx = cntx;
		prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
	}
	
	public void setStringArrayList(String key, ArrayList<String> al){
		prefs.edit().putStringSet(key, new HashSet<String>(al));
		prefs.edit().commit();
	}

	public void setKeyValues(String key, String value){
		prefs.edit().putString(key, value).commit();
		prefs.edit().commit();
	}
	
	public void setKeyValues(String key, int value){
		prefs.edit().putInt(key, value).commit();
		prefs.edit().commit();
	}
	
	public String getStringValues(String key){
		String value = prefs.getString(key, "");
		return value;
	}
	
	
	public int getIntValues(String key){
		int nullValue = 0;
		int value = prefs.getInt(key, nullValue);
		return value;
	}
	
	public void unsetSession(String key){
		prefs.edit().remove(key).commit();
		
	}


    public void setIntroValues(int value){
        prefs.edit().putInt("Intro", value).commit();
        prefs.edit().commit();
    }

    public boolean IsLoggedIn(){
		if(prefs.getInt("uid", 0) == 0){

            if(prefs.getInt("Intro",0)==0){
                Intent i = new Intent(_cntx, TutorialViewPager.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _cntx.startActivity(i);
                return false;
            }   else {

                Intent i = new Intent(_cntx, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _cntx.startActivity(i);
                return false;
            }
		}
		return true;
	}
}
