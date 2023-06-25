package com.prity.githubmobile.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prity.githubmobile.model.GitDataModel

@Database(entities = [GitDataModel::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): GitRepoDao

    companion object {
        @Volatile
        private var instance: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RepoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RepoDatabase::class.java,
                "repo_database"
            ).build()
        }
    }
}

