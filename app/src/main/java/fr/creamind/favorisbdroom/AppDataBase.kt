package fr.creamind.favorisbdroom

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Folder::class, BookMark::class],version=1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun folderDao():FolderDao
    abstract fun bookmarkDao():BookmarkDao
}