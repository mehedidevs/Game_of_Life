package com.mehedi.game_of_life.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mehedi.game_of_life.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.gameView.performClick()

    }

    override fun onResume() {
        super.onResume()
       // binding.gameView.start()
        binding.placeholderImg.visibility=View.VISIBLE


        binding.playBtn.setOnClickListener {
            binding.placeholderImg.visibility=View.GONE

            when (binding.playBtn.text.toString()) {

                "Play" -> {

                    binding.gameView.start()
                    binding.playBtn.text = "Stop"
                }

                "Stop" -> {

                    binding.gameView.stop()
                    binding.playBtn.text = "Play"
                }


            }


        }


    }

    override fun onPause() {
        super.onPause()
        binding.gameView.stop()
    }
}