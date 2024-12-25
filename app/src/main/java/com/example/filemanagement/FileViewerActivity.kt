package com.example.filemanagement

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileViewerActivity : AppCompatActivity() {

    private lateinit var fileContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)

        fileContent = findViewById(R.id.fileContent)

        val filePath = intent.getStringExtra("filePath")
        if (filePath != null) {
            val file = File(filePath)
            if (file.exists()) {
                val content = file.readText()
                fileContent.text = content
            } else {
                fileContent.text = "File not found"
            }
        }
    }
}