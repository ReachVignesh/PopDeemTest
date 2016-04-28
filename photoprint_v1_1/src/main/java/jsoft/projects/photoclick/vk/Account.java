package jsoft.projects.photoclick.vk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Account {
//    public String access_token;
//    public long user_id;
    
    public static void save(Context context,String access_token,long user_id){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor=prefs.edit();
        editor.putString("access_token", access_token);
        editor.putLong("user_id", user_id);
        editor.commit();
    }
    
    public static void restore(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        Editor editor=prefs.edit();
        editor.remove("access_token")   ;
               editor.remove("user_id" );
        editor.commit();
    }

       public static String getVk_access_tokenid(Context context){
           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                         return  prefs.getString("access_token","");
       }

          public static long getVk_user_id(Context context){
              SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
              return         prefs.getLong("user_id",0);
          }

}
