package pers.zy.colorablespanmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pers.zy.colorable_lib.ColorableTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_color_able.setColorAbleClickListener(0, 12, object : ColorableTextView.ColorClickListener {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "点了", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
