package com.task.wsetask.data.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.task.wsetask.R
import com.task.wsetask.utils.Constants

class MainActivity : AppCompatActivity() {
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch : EditText = findViewById(R.id.et_search)
        val btSearch : Button = findViewById(R.id.bt_search)

        btSearch.setOnClickListener {
            val searchText = etSearch.text.toString()
            if (searchText.isNotEmpty()) {
                val listIntent = Intent(this, ListActivity::class.java)
                listIntent.putExtra(Constants.SEARCH_KEY, searchText)
                startActivity(listIntent)
            } else {
                Snackbar.make(btSearch ,"Please Enter Valid Author Name to Search", Snackbar.LENGTH_LONG)
                        .show()
            }

        }

    }
}