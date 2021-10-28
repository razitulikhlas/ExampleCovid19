package com.example.covid19.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SessionManagerUtil {
    public static final String SESSION_PREFERENCE = "com.example.covid19.session.SessionManagerUtil.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.covid19.session.SessionManagerUtil.SESSION_TOKEN";
    public static final String SESSION_EXPIRY_TIME = "com.example.covid19.session.SessionManagerUtil.SESSION_EXPIRY_TIME";

    private static SessionManagerUtil INSTANCE;
    public static SessionManagerUtil getInstance(){
        if (INSTANCE==null){
            INSTANCE = new SessionManagerUtil();
        }

        return INSTANCE;
    }

    public void StartUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLoggedTime = calendar.getTime();
        calendar.setTime(userLoggedTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expiredTime = calendar.getTime();
        SharedPreferences sp = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(SESSION_EXPIRY_TIME, expiredTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiredAt = new Date(getExpiredDateFromPreference(context));
        return !currentTime.after(sessionExpiredAt);
    }

    private long getExpiredDateFromPreference(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getLong(SESSION_EXPIRY_TIME, 0);
    }

    public void storeUserToken(Context context, String token, String json){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(SESSION_TOKEN, token);
        editor.putString("user_login", json);
        editor.apply();
    }

    public String getUserToken(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getString(SESSION_TOKEN,"");
    }

    public String getUserLogin(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getString("user_login","");
    }

    public void endUserSession(Context context){
        clearStoredData(context);
    }

    private void clearStoredData(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
