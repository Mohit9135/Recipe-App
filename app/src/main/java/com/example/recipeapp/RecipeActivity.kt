package com.example.recipeapp

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    var imgCrop=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
        binding.title.text=intent.getStringExtra("title")
        binding.stepData.text=intent.getStringExtra("des")

        var ing=intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()

        binding.time.text=ing?.get(0)

//        for(i in 1 until ing!!.size){
//            binding.ingData.text=
//                """${binding.ingData.text} 🟢 ${ing[i]}
//
//                """.trimIndent()
//        }
        val formattedIngredients = StringBuilder()
        for (i in 1 until (ing?.size ?: 0)) {
            formattedIngredients.append("🟢 ${ing?.get(i)}\n")
        }
        binding.ingData.text = formattedIngredients.toString().trim()
        binding.step.background=null
        binding.step.setTextColor(getColor((R.color.black)))
        binding.step.setOnClickListener{
            binding.step.setBackgroundResource(R.drawable.btn_ing)
            binding.step.setTextColor(getColor((R.color.white)))
            binding.ing.setTextColor(getColor((R.color.black)))
            binding.ing.background=null
            binding.stepScroll.visibility=View.VISIBLE
            binding.ingScroll.visibility=View.GONE

        }
        binding.ing.setOnClickListener{
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor((R.color.white)))
            binding.step.setTextColor(getColor((R.color.black)))
            binding.step.background=null
            binding.ingScroll.visibility=View.VISIBLE
            binding.stepScroll.visibility=View.GONE
        }

        binding.fullScreen.setOnClickListener{
            if(imgCrop){
                binding.itemImage.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility= View.GONE
                imgCrop=!imgCrop
            }
            else{
                binding.itemImage.scaleType=ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.shade.visibility= View.GONE
                binding.fullScreen.setColorFilter(null)
                imgCrop=!imgCrop
            }
        }
        binding.backBtn.setOnClickListener{
            finish()
        }
    }
}