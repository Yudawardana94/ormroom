package com.example.ormroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {
    abstract fun studentDao (): StudentDao

    companion object{
        private var instance: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase? {
            if(instance == null) {
                synchronized(StudentDatabase::class){
                    instance = Room.databaseBuilder(context, StudentDatabase::class.java, "Student.db").build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}