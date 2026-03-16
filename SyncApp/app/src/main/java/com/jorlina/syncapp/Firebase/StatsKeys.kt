package com.jorlina.syncapp.Firebase

import androidx.datastore.preferences.core.intPreferencesKey

class StatsKeys {

    object StatsKeys {
        val APP_OPENS = intPreferencesKey("app_opens")         // Veces que abrimos la app
        val ITEMS_ADDED = intPreferencesKey("items_added")    // Veces que añadimos items
        val ITEMS_REMOVED = intPreferencesKey("items_removed") // Veces que eliminamos items
    }
}