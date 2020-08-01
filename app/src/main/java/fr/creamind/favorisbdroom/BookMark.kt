package fr.creamind.favorisbdroom

import androidx.room.*
import androidx.room.PrimaryKey

@Entity
data class Folder(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var name:String

)

@Entity(
    foreignKeys=[
        ForeignKey(entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["folder_id"])
    ])
data class BookMark(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,

    @ColumnInfo(name = "folder_id")
    var folder_id:Long,

    var name: String,
    var url:String


)