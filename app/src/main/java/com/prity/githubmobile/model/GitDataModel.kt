package com.prity.githubmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repoTable")
data class GitDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?,
    val url: String?
)
