package com.example.languagetranslator.activity

import com.example.languagetranslator.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.languagetranslator.adapters.CategoryAdapter
import com.example.languagetranslator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = CategoryAdapter(this,supportFragmentManager)
        binding.viewpager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewpager)
    }
}