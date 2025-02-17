package com.example.recipeapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var rvAdapter:PopularAdapter
    private lateinit var dataList:ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.more.setOnClickListener{
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }

        binding.search.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }

        binding.salad.setOnClickListener{
            val myIntent=(Intent(this@HomeActivity,CategoryActivity::class.java))
            myIntent.putExtra("TITLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.mainDish.setOnClickListener{
            val myIntent=(Intent(this@HomeActivity,CategoryActivity::class.java))
            myIntent.putExtra("TITLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }
        binding.drinks.setOnClickListener{
            val myIntent=(Intent(this@HomeActivity,CategoryActivity::class.java))
            myIntent.putExtra("TITLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }
        binding.desserts.setOnClickListener{
            val myIntent=(Intent(this@HomeActivity,CategoryActivity::class.java))
            myIntent.putExtra("TITLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        dataList= ArrayList()
        binding.rvPopular.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val db = Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject=db.getDao()
        val recipes=daoObject.getAll()
        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }
            rvAdapter=PopularAdapter(dataList,this)
            binding.rvPopular.adapter=rvAdapter
        }




    }
}