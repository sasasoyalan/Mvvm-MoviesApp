package com.sssoyalan.movieapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.sssoyalan.movieapp.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.Main){
            setupAnim()
            delay(1000L)
            goMain()
        }
    }

    private fun setupAnim(){
        val slideAnimationImage = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        val slideAnimationText = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text)
        splash_text.startAnimation(slideAnimationText)
        splash_image.startAnimation(slideAnimationImage)
    }

    private fun goMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}