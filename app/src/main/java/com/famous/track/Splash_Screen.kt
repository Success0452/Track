package com.famous.track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class Splash_Screen : AppCompatActivity() {


    val SPLASH_SCREEN = 3000

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation

    private lateinit var imageView: ImageView
    private lateinit var text: TextView
    private lateinit var description: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash__screen)

//        val actionBar = supportActionBar
//        actionBar!!.hide()

        //creating new animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        imageView = findViewById(R.id.image)
        text = findViewById(R.id.text)
        description = findViewById(R.id.description_text)

        imageView.animation = topAnimation
        text.animation = bottomAnimation
        description.animation = bottomAnimation

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())

    }

    }
