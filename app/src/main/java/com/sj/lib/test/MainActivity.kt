package com.sj.lib.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.xuan.widget.LetterNavigationView
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

        lnvTest.addOnNavigationScrollerListener(object :
            LetterNavigationView.OnNavigationScrollerListener {

            override fun onDown() {
                showLetter.visibility = View.VISIBLE
            }

            override fun onScroll(letter: String?, position: Int) {
                showLetter.text = letter
            }

            override fun onUp() {
                showLetter.visibility = View.GONE
            }
        })
    }
}
