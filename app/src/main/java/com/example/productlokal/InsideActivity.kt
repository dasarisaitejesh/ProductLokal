package com.example.productlokal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.productlokal.data.Product
import com.squareup.picasso.Picasso

class InsideActivity : AppCompatActivity() {

    private lateinit var imageSlider : ImageSlider
    private lateinit var product: Product
    private lateinit var title : TextView
    private lateinit var description : TextView
    private lateinit var price : TextView
    private lateinit var discount : TextView
    private lateinit var rating : TextView
    private lateinit var brand : TextView
    private lateinit var category : TextView
    private lateinit var stock : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside)

        imageSlider = findViewById(R.id.image_slider)
        title = findViewById(R.id.inside_title)
        description = findViewById(R.id.inside_description)
        price = findViewById(R.id.inside_price)
        discount = findViewById(R.id.inside_discount)
        rating = findViewById(R.id.inside_rating)
        brand = findViewById(R.id.inside_brand)
        category = findViewById(R.id.inside_category)
        stock = findViewById(R.id.inside_stock)

        product = intent.getParcelableExtra("dataList")!!

        val imageList = product.images
        val slideModelList = ArrayList<SlideModel>()
        for (imageURL in imageList) {
            slideModelList.add(SlideModel(imageURL))
        }

        imageSlider.setImageList(slideModelList)

        title.text = product.title
        description.text = SpannableStringBuilder().bold { append(description.text) }.append(product.description)
        price.text = SpannableStringBuilder().bold { append(price.text) }.append(product.price.toString()).append(" $")
        discount.text = SpannableStringBuilder().bold { append(discount.text) }.append(product.discountPercentage.toString()).append(" %")
        rating.text = SpannableStringBuilder().bold { append(rating.text) }.append(product.rating.toString()).append(" / 5")
        brand.text = SpannableStringBuilder().bold { append(brand.text) }.append(product.brand)
        category.text = SpannableStringBuilder().bold { append(category.text) }.append(product.category)
        stock.text = SpannableStringBuilder().bold { append(stock.text) }.append(product.stock.toString())
    }
}