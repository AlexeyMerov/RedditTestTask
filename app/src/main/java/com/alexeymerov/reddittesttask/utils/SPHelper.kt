package com.alexeymerov.reddittesttask.utils

import android.content.Context
import android.content.SharedPreferences

var sharedPrefs: SharedPreferences? = null

fun Context.getPrefs(): SharedPreferences = sharedPrefs ?: getSharedPreferences("reddittesttask", Context.MODE_PRIVATE)
        .also { sharedPrefs = it }

inline fun String.onChanged(crossinline valueChanged: (String) -> Unit) {
    sharedPrefs?.registerOnSharedPreferenceChangeListener { _, key ->
        run { if (key == this) valueChanged.invoke(key) }
    }
}

inline fun <reified T : Any> Context.getShared(key: String, defValue: T, crossinline observer: (T) -> Unit = {}): T {
    val resultValue = getCurrentValue(key, defValue)
    key.onChanged { observer.invoke(getCurrentValue(key, defValue)) }
    observer.invoke(resultValue)
    return resultValue
}

inline fun <reified T : Any> Context.getCurrentValue(key: String, defValue: T): T {
    val value: Any = getPrefs().run {
        when (defValue) {
            is Boolean -> getBoolean(key, defValue)
            is Long -> getLong(key, defValue)
            is Int -> getInt(key, defValue)
            is String -> getString(key, defValue)!!
            is Float -> getFloat(key, defValue)
            else -> throw Exception("Cannot cast to any type")
        }
    }
    return value as T
}

inline fun <reified T : Any> Context.setShared(key: String, newValue: T) {
    getPrefs().edit {
        when (newValue) {
            is Long -> putLong(key, newValue)
            is Int -> putInt(key, newValue)
            is String -> putString(key, newValue)
            is Boolean -> putBoolean(key, newValue)
            is Float -> putFloat(key, newValue)
            else -> throw Exception("Cannot cast to any type")
        }
    }
}

inline fun SharedPreferences.edit(crossinline action: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        action(this)
        apply()
    }
}