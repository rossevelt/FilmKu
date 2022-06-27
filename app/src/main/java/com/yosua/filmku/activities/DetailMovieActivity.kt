package com.yosua.filmku.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yosua.filmku.R
import com.yosua.filmku.api.Api
import com.yosua.filmku.model.MainModel

class DetailMovieActivity : AppCompatActivity() {
    private var mainModel: MainModel? = null
    private var txtName: String? = null
    private var imgPoster: String? = null
    private var overview: String? = null
    private var popularity: String? = null
    private var releaseDate: String? = null
    private var title: String? = null
    private var ratings: String? = null
    private var backdropImage: String? = null
    private lateinit var tvTitle: TextView
    private lateinit var tvOriginalTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvRatings: TextView
    private lateinit var tvOverview: TextView
    private lateinit var tvPopularity: TextView
    private lateinit var ivPoster: ImageView
    private lateinit var ivBackdrop: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        tvTitle = findViewById(R.id.txtTitle)
        tvOriginalTitle = findViewById(R.id.txtOriginalTitle)
        tvDate = findViewById(R.id.txtReleaseDate)
        tvPopularity = findViewById(R.id.txtPopularity)
        tvRatings = findViewById(R.id.txtRating)
        tvOverview = findViewById(R.id.txtOverview)
        ivPoster = findViewById(R.id.imagePoster)
        ivBackdrop = findViewById(R.id.imageBackdrop)
        mainModel = intent.getSerializableExtra(DETAIL_UPCOMING) as MainModel
        if(mainModel != null){
            txtName = mainModel?.txtName
            imgPoster = Api.poster+mainModel?.imgSrc
            overview = mainModel?.overview
            popularity = mainModel?.popularity
            releaseDate = mainModel?.dateRelease
            title = mainModel?.txtOriTitle
            ratings = mainModel?.ratings+" / "+mainModel?.rateCount
            backdropImage = Api.backdrop+mainModel?.backdropSrc

            tvTitle.text = txtName
            tvOriginalTitle.text = title
            tvDate.text = releaseDate
            tvPopularity.text = popularity
            tvRatings.text = ratings
            tvOverview.text = overview
            Glide.with(this)
                .load(imgPoster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPoster)
            Glide.with(this)
                .load(backdropImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBackdrop)
        }
    }
    companion object{
        const val DETAIL_UPCOMING = "detail_upcoming"
    }
}