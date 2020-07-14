package com.alexeymerov.reddittesttask.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity

@Dao
interface PostDAO : BaseDAO<PostEntity> {

    companion object {
        const val TABLE_NAME: String = "post_entity"
        const val DATE_ROW: String = "createdDate"
        const val SCORE_ROW: String = "score"
    }

    @Query("SELECT * FROM post_entity ORDER BY $SCORE_ROW DESC")
    fun getAllLive(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM post_entity ORDER BY $SCORE_ROW DESC")
    fun getAll(): List<PostEntity>

    @Query("DELETE FROM post_entity WHERE $DATE_ROW <= :timestamp")
    fun clearPostsBefore(timestamp: Long)

}