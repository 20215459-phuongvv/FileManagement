package com.example.filemanagement

import FileAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val rootDirectory: File = File(
        Environment.getExternalStorageDirectory().absolutePath // Truy cập sdcard
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        } else {
            setupRecyclerView()
            loadFiles(rootDirectory)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.main)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadFiles(directory: File) {
        val files = directory.listFiles()?.toList() ?: emptyList()
        recyclerView.adapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                // Load nội dung thư mục
                loadFiles(file)
            } else {
                // Hiển thị nội dung file
                val intent = Intent(this, FileViewerActivity::class.java)
                intent.putExtra("filePath", file.absolutePath)
                startActivity(intent)
            }
        }
    }
}
