package fr.creamind.favorisbdroom

import android.app.Application
import androidx.room.Room

class App : Application(){

    companion object{
        lateinit var dataBase: AppDataBase
    }
    override fun onCreate() {
        super.onCreate()
        dataBase = Room.databaseBuilder(this,AppDataBase::class.java,"bookmarksDB")
            .build()
    }
}