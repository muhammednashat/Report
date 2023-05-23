package com.mnashat_dev.saveme.data


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnashat_dev.saveme.data.models.Report

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upSert(vararg reports: Report)

    @Query("SELECT * FROM Reports_table")
    fun getAllReports(): LiveData<List<Report>>




}

private lateinit var INSTANCE: ReportDatabase

@Database(entities = [Report::class], version = 1, exportSchema = false)
abstract class ReportDatabase() : RoomDatabase() {

    abstract val reportDao: ReportDao
}

fun getDatabase(context: Context): ReportDatabase {
    synchronized(ReportDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ReportDatabase::class.java,
                "report.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}