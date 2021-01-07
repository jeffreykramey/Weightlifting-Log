package com.example.lifttracker.pastActivityDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PastActivityDao {

    @Insert
    fun insert(activity: Activity)

    @Update
    fun update(activity: Activity)

    @Query("DELETE FROM past_activity_table")
    fun clear()

    @Query("SELECT * FROM past_activity_table ORDER BY date ASC")
    fun getAllActivities(): LiveData<List<Activity>>
}