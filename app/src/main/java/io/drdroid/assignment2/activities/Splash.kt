package io.drdroid.assignment2.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.ProgressBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.base.BaseActivity
import io.drdroid.assignment2.databinding.ActivitySplashBinding
import io.drdroid.assignment2.network.ApiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Splash : BaseActivity() {

    @Inject
    lateinit var apiCall: ApiCall

    lateinit var bind: ActivitySplashBinding

    lateinit var progress: ProgressBar

    private var listGenres: List<String>? = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            listGenres = try {
                apiCall.getShows().asSequence().filter { it.genres.isNotEmpty() }
                    .map { showModel -> showModel.genres }.map { l -> l.first() }.toSet().toList()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

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
            val intent = Intent(this@Splash, Home::class.java)
            intent.putExtra("genres", Gson().toJson(listGenres?.sorted()))
            this@Splash.startActivity(intent)
            this@Splash.finish()
        }

    }
}