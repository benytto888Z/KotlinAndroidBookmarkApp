package fr.creamind.favorisbdroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

fun EditText.textString():String{
    return this.text.toString()
}

class MainActivity : AppCompatActivity() {

    private lateinit var folderDao:FolderDao
    private lateinit var bookmarkDao: BookmarkDao

    private val folderNameLiveData = MutableLiveData<String>()
    private lateinit var getFolderLiveData: LiveData<Folder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        folderDao = App.dataBase.folderDao()
        bookmarkDao = App.dataBase.bookmarkDao()

        bookmarkDao.getAllBookmarks().observe(this,Observer{bookmarks ->
            bookmarksTextView.text = bookmarks!!
                .joinToString("\n"){ bookmark -> bookmark.toString()}
        })

        getFolderLiveData = Transformations.switchMap(folderNameLiveData){folderName->
            folderDao.getFolderByName(folderName)
        }

        // on s'abonne aux changements

        getFolderLiveData.observe(this, Observer {folder->
            createBookmark(folder!!.id, bookmarkFolderNameEditText.textString(),
                bookmarkUrlEditText.textString()
            )
        })

        createFolderButton.setOnClickListener{
            createFolder(createFolderEditText.textString())
        }

        createBookmarkButton.setOnClickListener{
            folderNameLiveData.value = bookmarkFolderNameEditText.textString()

        }
    }

    private fun createBookmark(folderId: Long, bookmarkName: String, bookmarkUrl: String) {
        Executors.newSingleThreadExecutor().execute{
            val bookmark = BookMark(folder_id = folderId,name=bookmarkName,url = bookmarkUrl)
            bookmarkDao.insertBookmark(bookmark)
        }

        Toast.makeText(this," Bookmark $bookmarkName successful created",Toast.LENGTH_LONG).show()
    }

    private fun createFolder(folderName: String) {
        Executors.newSingleThreadExecutor().execute{
            folderDao.insertFolder(Folder(name = folderName))
        }

        Toast.makeText(this,"Folder successful created",Toast.LENGTH_LONG).show()

        bookmarkFolderNameEditText.setText(folderName,TextView.BufferType.EDITABLE)
    }
}