package com.sj.lib.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.xuan.widget.badgelibrary.BadgeViewPro
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topToolbarTest.addToolbarLeftOnClickListener(View.OnClickListener { finish() })

        appEtvText.initWidth(windowManager.defaultDisplay.width)
        appEtvText.initMaxLines(2)
        appEtvText.setCloseText("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试")

        val badgeViewPro = BadgeViewPro(this)
        badgeViewPro.text = "100"
        badgeViewPro.setTargetView(topToolbarTest)
    }
}
