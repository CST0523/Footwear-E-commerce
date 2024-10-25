package com.example.final_assignment.Helper

import android.content.Context
import com.example.final_assignment.Model.ItemsModel

class FavoriteManager(context: Context) {
    private val tinyDB = TinyDB(context)

    fun addToFavorites(item: ItemsModel) {
        val favoritesList = getFavoritesList()
        if (!favoritesList.any { it.title == item.title }) {
            favoritesList.add(item)
            tinyDB.putListObject("FavoritesList", favoritesList)
        }
    }

    fun removeFromFavorites(item: ItemsModel) {
        val favoritesList = getFavoritesList()
        favoritesList.removeAll { it.title == item.title }
        tinyDB.putListObject("FavoritesList", favoritesList)
    }

    fun isFavorite(item: ItemsModel): Boolean {
        return getFavoritesList().any { it.title == item.title }
    }

    private fun getFavoritesList(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("FavoritesList") ?: arrayListOf()
    }
}