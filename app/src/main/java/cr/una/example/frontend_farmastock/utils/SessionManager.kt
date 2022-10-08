package cr.una.example.frontend_farmastock.utils

import android.content.Context
import android.content.SharedPreferences
import cr.una.example.frontend_farmastock.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }


    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)

    }

    /**
     * Function to delete auth token
     */
    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.remove (USER_TOKEN)
        editor.apply()
    }




    /**
     * Function to save auth token
     */
    fun saveUserIdLogged(id: Long) {
        val editor = prefs.edit()
        editor.putLong(USER_ID, id)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchUserIdLogged(): Long? {
        return prefs.getLong(USER_ID, 0)

    }

    /**
     * Function to delete auth token
     */
    fun deleteUserIdLogged() {
        val editor = prefs.edit()
        editor.remove (USER_ID)
        editor.apply()
    }
}