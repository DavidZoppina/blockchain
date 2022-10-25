

package com.andtv.flicknplay.recommendation.data.local.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

/**
 *
 */
class LocalSettings @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getLongFromPersistence(key: String, defValue: Long) =
            sharedPreferences.getLong(key, defValue)

    fun applyLongToPersistence(key: String, value: Long) {
        sharedPreferences.edit {
            putLong(key, value)
        }
    }
}
