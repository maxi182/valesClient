package com.mcba.comandaclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gtoledano on 15/04/2016.
 */
public final class StorageProvider {

    private static SharedPreferences sSharedPreferences;


    public static void init(Context context) {
        sSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    /**
     * Returns the shared preferences.
     *
     * @return Shared prefs.
     */
    public static SharedPreferences getPreferences() {
        return sSharedPreferences;
    }

    /**
     * Saves the preference.
     *
     * @param key   Key for preference.
     * @param value Value for preference store.
     */
    public static void savePreferences(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Saves the preference.
     *
     * @param key   Key for preference.
     * @param value Value for preference store.
     */
    public static void savePreferences(String key, Boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Saves the preference.
     *
     * @param key   Key for preference.
     * @param value Value for preference store.
     */
    public static void savePreferences(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Saves the preference.
     *
     * @param key   Key for preference.
     * @param value Value for preference store.
     */
    public static void savePreferences(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Saves the preference.
     *
     * @param key   Key for preference.
     * @param value Value for preference store.
     */
    public static void savePreferences(String key, HashSet<Integer> value) {
        SharedPreferences.Editor editor = getPreferences().edit();

        HashSet<String> stringSet = new HashSet<>();
        for (Integer i : value) {
            stringSet.add(String.valueOf(i));
        }

        editor.putStringSet(key, stringSet);
        editor.apply();
    }

    /**
     * retrieve a string saved preference.
     *
     * @param key
     * @return
     */
    public static String getPreferencesString(String key) {
        return getPreferencesString(key, null);
    }

    /**
     * retrieve a string saved preference.
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getPreferencesString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    /**
     * retrieve an int saved preference.
     *
     * @param key
     * @return
     */
    public static int getPreferencesInt(String key) {
        return getPreferences().getInt(key, 0);
    }

    /**
     * retrieve a boolean saved preference.
     *
     * @param key
     * @return
     */
    public static boolean getPreferencesBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    /**
     * retrieve a long saved preference.
     *
     * @param key
     * @return
     */
    public static long getPreferencesLong(String key) {
        return getPreferences().getLong(key, 0);
    }

    /**
     * retrieve an Integer Hash Set from saved preference.
     *
     * @param key
     * @return
     */
    public static HashSet<Integer> getPreferencesIntegerHashSet(String key) {

        Set<String> stringSet = getPreferences().getStringSet(key, null);
        HashSet<Integer> integerSet = new HashSet<>();

        if (stringSet != null) {
            for (String s : stringSet) {
                if (s != null) {
                    integerSet.add(Integer.parseInt(s));
                }
            }
        }

        return integerSet;
    }

    public static void deletePreferences(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();

        editor.remove(key);
        editor.apply();
    }
}
