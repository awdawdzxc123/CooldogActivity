package com.example.cooldogactivity

import android.app.Activity
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var index = 0
    private val newsList = ArrayList<Data>()
    private val mediaParser = MediaPlayer()

    private fun initData() {
        repeat(1) {
            newsList.add(Data("传闻(DJ版)", "周柏豪", R.drawable.zhoubaihao, "music_01.mp3"))
            newsList.add(Data("尼古丁 (2015梦想系音乐会)", "周柏豪", R.drawable.xiaofuj, "music_02.mp3"))
            newsList.add(Data("我爱的人", "周柏豪", R.drawable.zhouyi, "music_05.mp3"))
            newsList.add(Data("到此为止", "周柏豪", R.drawable.zhouer, "music_04.mp3"))
            newsList.add(Data("爱不疚", "周柏豪", R.drawable.zhousi,"music_03.mp3"))
            newsList.add(Data("一事无成", "周柏豪", R.drawable.zhou1, "music_06.mp3"))
            newsList.add(Data("告白", "周柏豪", R.drawable.zhouyi, "music_07.mp3"))
            newsList.add(Data(" Lovin' You 第二版", "周柏豪", R.drawable.zhousi, "music_08.mp3"))
            newsList.add(Data("Smiley Face (Live)", "周柏豪", R.drawable.zhou1, "music_09.mp3"))
            newsList.add(Data("百年不合 (Live)", "周柏豪", R.drawable.zhouer, "music_10.mp3"))
            newsList.add(Data("陈某 (Encore)", "周柏豪", R.drawable.zhouyi, "music_11.mp3"))
            newsList.add(Data("傳聞", "周柏豪", R.drawable.zhoubaihao, "music_12.mp3"))
            newsList.add(Data("等不到 (Live)", "周柏豪", R.drawable.zhouer, "music_13.mp3"))
            newsList.add(Data("数码超能量", "周柏豪", R.drawable.xiaofuj, "music_14.mp3"))
            newsList.add(Data("我的宣言 (片段)", "周柏豪", R.drawable.zhou1, "music_15.mp3"))
            newsList.add(Data("小孩 (Live)", "周柏豪", R.drawable.zhousi, "music_16.mp3"))
            newsList.add(Data("一生爱你一个", "周柏豪", R.drawable.zhou1, "music_17.mp3"))
            newsList.add(Data("早班火车", "周柏豪", R.drawable.xiaofuj, "music_18.mp3"))
            newsList.add(Data(" 最后的三分十六", "周柏豪", R.drawable.zhou1, "music_19.mp3"))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()//标题弃掉 //
        initData()
        fullScreen(this)
        initMediaPlayer(newsList[index].musicId)

        RecyclerViewTest.layoutManager = LinearLayoutManager(this)
        val adapter = NewsAdapter(newsList)
        RecyclerViewTest.adapter = adapter

        musicPlay.setOnClickListener {
            if (!mediaParser.isPlaying) {
                musicPlay.setImageResource(R.drawable.ic_zan)
                mediaParser.start()
            } else {
                musicPlay.setImageResource(R.drawable.ic_bof)
                mediaParser.pause()
            }

        }
        nextSong.setOnClickListener {
            index++
            mediaParser.reset()
            if (index > newsList.size -1){
                index = 0
            }
            musicManager()
        }

        lastSong.setOnClickListener {
            index--
            mediaParser.reset()
            if (index < 0) {
                index = newsList.size - 1
            }
            musicManager()
        }
    }

    private fun zhuang(int:Int){
        val translateAnimation = RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        translateAnimation.duration = 10000
        translateAnimation.repeatCount = int / 60000
        translateAnimation.repeatMode = 1
        picture.animation = translateAnimation
        translateAnimation.start()
    }
    private fun musicManager(){
        initMediaPlayer(newsList[index].musicId)
        music_name.text = newsList[index].geming
        music_author.text = newsList[index].geshou
        picture.setImageResource(newsList[index].tupian)
        mediaParser.start()
        musicPlay.setImageResource(R.drawable.ic_zan)
    }


    //适配器获取信息
    inner class NewsAdapter(private val newsList: ArrayList<Data>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val geming: TextView = view.findViewById(R.id.geming)
            val geshou: TextView = view.findViewById(R.id.geshou)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_data, parent, false)
            val viewHolder = ViewHolder(view)

            viewHolder.itemView.setOnClickListener {
                index = viewHolder.adapterPosition
                val list =newsList[index]
                //Toast.makeText(parent.context,"你点击"+newsList[index].geming,Toast.LENGTH_LONG).show()
                mediaParser.reset()
                initMediaPlayer(newsList[index].musicId)
                mediaParser.start()

                music_name.text = list.geming
                music_author.text = list.geshou
                picture.setImageResource(list.tupian)
            }
            return viewHolder
        }

        override fun getItemCount() = newsList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = newsList[position]
            holder.geming.text = item.geming
            holder.geshou.text = item.geshou
        }
    }

    private fun initMediaPlayer(music:String){
        val assetMediaPlayer =assets
        val fd =assetMediaPlayer.openFd(music)
        mediaParser.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaParser.prepare()
        zhuang(mediaParser.duration)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaParser.stop()
        mediaParser.release()
    }
    /*** 通过设置全屏，设置状态栏透明** @param activity*/
    private fun fullScreen(activity: Activity) {
        run {

            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window = activity.window
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
