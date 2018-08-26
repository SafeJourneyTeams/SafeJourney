package rsta.safejourney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by TekNath on 15-Jan-18.
 */

public class SharedPrefmanager {
    private static final String SHARED_PREF_NAME="sjuserpref";
    private static final String KEY_NAME="keyname";
    private static final String KEY_PHONE="keyphone";
    private static final String KEY_GENDER="keyGender";
    private static final String KEY_DRIVER="driver";

    private static SharedPrefmanager mInstance;
    private static Context mCtx;

    private SharedPrefmanager(Context context){
        mCtx=context;
    }
    public static synchronized SharedPrefmanager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefmanager(context);
        }
        return mInstance;
    }
    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_DRIVER, user.getDriver());
        editor.apply();
    }
    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }
    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }
}
