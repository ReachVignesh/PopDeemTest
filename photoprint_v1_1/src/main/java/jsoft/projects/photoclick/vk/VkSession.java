package jsoft.projects.photoclick.vk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * Manage access token and user data. Token and user data data are stored in shared preferences.
 */

public class VkSession {
    private Context mContext;
    private SharedPreferences mSharedPref;

    private static final String SHARED = "Vk_Preferences";
    private static final String USERID	= "user_id";
//    private static final String USERNAME = "username";
//    private static final String FULLNAME = "fullname";
//    private static final String PROFILPIC = "profilpic";
    private static final String ACCESS_TOKEN = "token";

    public VkSession(Context context) {
        mContext	= context;
        mSharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
    }

    /**
     * Save user data
     *
     * @param user User data
     */
    public void store(VkUser user) {
        Editor editor = mSharedPref.edit();

        editor.putString(ACCESS_TOKEN,  user.accessToken);
        editor.putString(USERID, 		user.id);
//        editor.putString(USERNAME, 		user.username);
//        editor.putString(FULLNAME, 		user.fullName);
//        editor.putString(PROFILPIC, 	user.profilPicture);

        editor.commit();
    }

    /**
     * Reset user data
     */
    public void reset() {
        Editor editor = mSharedPref.edit();

        editor.putString(ACCESS_TOKEN, 	"");
        editor.putString(USERID, 		"");
//        editor.putString(USERNAME, 		"");
//        editor.putString(FULLNAME, 		"");
//        editor.putString(PROFILPIC, 	"");

        editor.commit();

        CookieSyncManager.createInstance(mContext);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    /**
     * Get user data
     *
     * @return User data
     */
    public VkUser getUser() {
        if (mSharedPref.getString(ACCESS_TOKEN, "").equals("")) {
            return null;
        }

        VkUser user 	= new VkUser();

        user.id				= mSharedPref.getString(USERID, "");
//        user.username		= mSharedPref.getString(USERNAME, "");
//        user.fullName		= mSharedPref.getString(FULLNAME, "");
//        user.profilPicture	= mSharedPref.getString(PROFILPIC, "");
        user.accessToken	= mSharedPref.getString(ACCESS_TOKEN, "");
//        account.access_token = b.getString("token");
//        account.user_id = b.getLong("user_id", 0);
        return user;
    }

    /**
     * Get access token
     *
     * @return Access token
     */
    public String getAccessToken() {
        return mSharedPref.getString(ACCESS_TOKEN, "");
    }
    public String getId() {
        return mSharedPref.getString(USERID, "");
    }

    /**
     * Check if there is an active session.
     *
     * @return true if active and vice versa
     */
    public boolean isActive() {
        return (mSharedPref.getString(ACCESS_TOKEN, "").equals("")) ? false : true;
    }
}