package com.alexeymerov.reddittesttask.data.database.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(items: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(items: List<T>)

    @Delete
    fun delete(item: T)

    @Delete
    fun delete(items: List<T>)

}