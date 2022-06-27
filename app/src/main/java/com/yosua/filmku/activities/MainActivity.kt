package com.yosua.filmku.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yosua.filmku.R
import com.yosua.filmku.adapter.MainAdapter
import com.yosua.filmku.api.Api
import com.yosua.filmku.model.MainModel
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var rvUpcoming: RecyclerView? = null
    private var mainModel: MainModel? = null
    var mainAdapter: MainAdapter? = null
    private var list: ArrayList<MainModel> = arrayListOf()
    private var txtName: String? = null
    private var imgPoster: String? = null
    private lateinit var textItemName: TextView
    private lateinit var imageItemPoster: ImageView
    private lateinit var btn2x2: ImageButton
    private lateinit var btn2x3: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUpcoming = findViewById(R.id.rvUpcoming)
        btn2x2 = findViewById(R.id.btnGrid2x2)
        btn2x3 = findViewById(R.id.btnGrid2x3)

        btn2x2.setOnClickListener {
            showGridList2x2()
        }
        btn2x3.setOnClickListener {
            showGridList2x3()
        }
    if (mainModel != null){
        txtName = mainModel?.txtName
        imgPoster = mainModel?.imgSrc
        textItemName.text = txtName
        Glide.with(this)
            .load(imgPoster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageItemPoster)
    }
        getUpcomingName()
        showGridList2x2()
    }

    private fun getUpcomingName() {
        AndroidNetworking.get(Api.baseUrl)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: JSONObject?){
                    try{
                        val jsonArray = response?.getJSONArray("results")
                        if(jsonArray != null){
                            for (i in 0 until jsonArray.length()){
                                val jsonObjectList = jsonArray.getJSONObject(i)
                                val dataApi = MainModel()
                                dataApi.txtName = jsonObjectList.getString("title")
                                dataApi.imgSrc = jsonObjectList.getString("poster_path")
                                dataApi.backdropSrc = jsonObjectList.getString("backdrop_path")
                                dataApi.txtOriTitle = jsonObjectList.getString("original_title")
                                dataApi.dateRelease = jsonObjectList.getString("release_date")
                                dataApi.popularity = jsonObjectList.getString("popularity")
                                dataApi.ratings = jsonObjectList.getString("vote_average")
                                dataApi.rateCount = jsonObjectList.getString("vote_count")
                                dataApi.overview = jsonObjectList.getString("overview")

                                list.add(dataApi)
                            }
                        }
                        mainAdapter?.notifyDataSetChanged()
                    } catch (e: JSONException){
                        e.printStackTrace()
                        Toast.makeText(
                            this@MainActivity,
                            "Gagal menampilkan data.. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Maaf tidak ada koneksi internet..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    private fun showGridList2x2() {
        rvUpcoming?.layoutManager = GridLayoutManager(this,2)
        mainAdapter = MainAdapter(this, list)
        rvUpcoming?.adapter = mainAdapter
    }
    private fun showGridList2x3() {
        rvUpcoming?.layoutManager = GridLayoutManager(this,3)
        mainAdapter = MainAdapter(this, list)
        rvUpcoming?.adapter = mainAdapter
    }
}