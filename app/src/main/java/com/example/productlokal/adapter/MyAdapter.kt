package com.example.productlokal.adapter

import android.app.Activity
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.example.productlokal.InsideActivity
import com.example.productlokal.R
import com.example.productlokal.data.Product
import com.squareup.picasso.Picasso

class MyAdapter(private var context: Activity): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var productsList: ArrayList<Product>? = null

    fun setData(productsList: ArrayList<Product>?){
        this.productsList = productsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = productsList?.get(position)
        if (currItem != null) {
            holder.title.text =currItem.title
            holder.price.text = SpannableStringBuilder().bold { append("Price : ") }.append(currItem.price.toString()).append(" $")
            holder.rating.text = SpannableStringBuilder().bold { append("Rating : ") }.append(currItem.rating.toString()).append(" / 5")
            Picasso.get().load(currItem.thumbnail)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.image)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InsideActivity::class.java)
            intent.putExtra("dataList", currItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if(productsList == null) 0
        else productsList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView
        var title : TextView
        var price : TextView
        var rating : TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            title = itemView.findViewById(R.id.item_title)
            price = itemView.findViewById(R.id.item_price)
            rating = itemView.findViewById(R.id.item_rating)
        }
    }
}