package io.drdroid.assignment2.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.ProgressBar
import io.drdroid.assignment2.base.BaseActivity
import io.drdroid.assignment2.databinding.ActivitySplashBinding


class Splash : BaseActivity() {

    lateinit var bind: ActivitySplashBinding

    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        progress = bind.progressBar

        CountDown(5000, 10).start()
    }

    internal inner class CountDown(milliSecondsFuture: Long, countDownInterVal: Long) :
        CountDownTimer(milliSecondsFuture, countDownInterVal) {

//        private val dateFormat = SimpleDateFormat("HH : mm : ss", Locale.getDefault())

        override fun onTick(millisUntilFinished: Long) {
            progress.progress = millisUntilFinished.toInt()
        }

        override fun onFinish() {
            this@Splash.startActivity(Intent(this@Splash, Home::class.java))
            this@Splash.finish()
        }

    }
}