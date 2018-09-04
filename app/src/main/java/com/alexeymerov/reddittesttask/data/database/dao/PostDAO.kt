package com.alexeymerov.reddittesttask.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity

@Dao
interface PostDAO {

    companion object {
        const val TABLE_NAME: String = "post_entity"
        const val DATE_ROW: String = "createdDate"
        const val SCORE_ROW: String = "score"
    }

    @Query("SELECT * FROM post_entity ORDER BY $SCORE_ROW DESC")
    fun getAllLive(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM post_entity ORDER BY $SCORE_ROW DESC")
    fun getAll(): List<PostEntity>

    @Insert(onConflict = REPLACE)
    fun addAll(all: List<PostEntity>)

    @Update(onConflict = REPLACE)
    fun updateAll(all: List<PostEntity>)

    @Delete
    fun deleteAll(all: List<PostEntity>)

}