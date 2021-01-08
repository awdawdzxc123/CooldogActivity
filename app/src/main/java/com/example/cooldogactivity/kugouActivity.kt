package com.example.cooldogactivity

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import kotlinx.android.synthetic.main.activity_kugou.*
import java.util.*

class kugouActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    private var time = 7
    private val timer = Timer()
    private val task: TimerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                time--
                button2.text = "$time 跳过"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kugou)
        supportActionBar?.hide()//标题弃掉


        val fd = assets.openFd("kugou.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
        mediaPlayer.start()

        handler.postDelayed({
            init()
        }, 7000)

        timer.schedule(task, 1000, 1000)

        button2.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            init()
        }

        handler.postDelayed({
            imageView3.setImageResource(R.drawable.kg)
            button2.visibility = View.VISIBLE
        }, 2000)
    }

    private fun init() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}