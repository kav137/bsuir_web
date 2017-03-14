package by.bsuir.bsuirweb.core.settings

import android.content.Context

object Settings {

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun token(context: Context): String? {
        return context.getSharedPreferences("token", Context.MODE_PRIVATE).getString("token", null)
    }

}