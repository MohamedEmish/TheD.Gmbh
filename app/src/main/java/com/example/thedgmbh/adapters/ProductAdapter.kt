package com.example.thedgmbh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.thedgmbh.R
import com.example.thedgmbh.models.Product

class ProductAdapter(private val mContext: Context?) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    private var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (mContext == null) {
            return
        }

        val product = getItem(position)
        val data: String = product.imageDetail

        val phase1 = data.split("-")
        val link = phase1[0]

        Glide.with(holder.productImageView)
            .load(link)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().override(1000, 1000))
            .into(holder.productImageView)

        holder.productTitle.text = getItem(position).name
        holder.productPrice.text = (getItem(position).price).toString()

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var layout: ConstraintLayout = itemView.findViewById(R.id.item_product_layout)
        var productImageView: ImageView = itemView.findViewById(R.id.iv_product_image)
        var productTitle: TextView = itemView.findViewById(R.id.tv_product_title)
        var productPrice: TextView = itemView.findViewById(R.id.tv_product_price)

        init {

            layout.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(position))
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}