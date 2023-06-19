package ru.veider.mydrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.veider.mydrive.databinding.ActivityMainBinding
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
	lateinit var car: ImageView
	lateinit var container: RelativeLayout
	var _binding: ActivityMainBinding? = null
	val binding get() = _binding!!
	var height: Int = 0
	var width: Int = 0
	var carHeight: Int = 0
	var carWidth: Int = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		car = binding.car.apply {
			setOnClickListener {
				runDrive()
			}
		}
		container = binding.container

	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}

	private fun runDrive() {
		carHeight = car.height
		carWidth = car.width
		height = container.height-50-carHeight
		width = container.width-carWidth
		val x1 = width/3.0
		val y1 = height*2/3.0
		GlobalScope.launch {
			var rot = -atan2(x1,y1)*(180/PI)+180
			var length = sqrt(x1*x1+y1*y1)
			for (i in 0..length.toInt()){
				delay(1)
				val x0 = i*(x1/length)
				val y0 = i*(y1/length)
				car.post {
					car.rotation = rot.toFloat()
					car.x = x0.toFloat()
					car.y = y0.toFloat()
				}
			}
			rot = -atan2(width-x1,height-y1)*(180/PI)+180
			length = sqrt((width-x1)*(width-x1)+(height-y1)*(height-y1))
			for (i in 0..length.toInt()){
				delay(1)
				val x0 = x1+i*((width-x1)/length)
				val y0 = y1+i*(height-y1)/(length)
				car.post {
					car.rotation = rot.toFloat()
					car.x = x0.toFloat()
					car.y = y0.toFloat()
				}
			}
			car.post {
				car.rotation = 0f
			}
		}
	}
}