package com.example.contactsmanager.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// If no name is mentioned room will use class name
@Entity(tableName = "user")

data class User(
    @PrimaryKey(autoGenerate = true) // Specify user_id as primary key
    // without ColumnInfo room will create fields with variable names
    @ColumnInfo(name = "user_id")
    val id: Int,
    @ColumnInfo(name = "user_name")
    var name: String,
    @ColumnInfo(name = "user_email")
    var email: String
)
