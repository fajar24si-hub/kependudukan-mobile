package com.example.fajar_time.Pertemuan_4

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fajar_time.R
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors

class NewsAdapter(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    // Thread pool bersama agar tidak membuat terlalu banyak thread
    private val executor = Executors.newFixedThreadPool(4)
    private val handler = Handler(Looper.getMainLooper())

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivNewsThumbnail)
        val tvTitle: TextView = itemView.findViewById(R.id.tvNewsTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvNewsDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvNewsCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]

        holder.tvTitle.text = news.title
        holder.tvCategory.text = "Nasional"

        // Reset thumbnail ke placeholder
        holder.ivThumbnail.setImageResource(android.R.drawable.ic_menu_gallery)
        holder.ivThumbnail.tag = news.thumbnail

        // Format tanggal
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMM yyyy", Locale("id", "ID"))
            val date = inputFormat.parse(news.pubDate)
            holder.tvDate.text = if (date != null) outputFormat.format(date) else news.pubDate
        } catch (e: Exception) {
            holder.tvDate.text = news.pubDate
        }

        // Load gambar secara async jika ada URL
        if (news.thumbnail.isNotEmpty()) {
            executor.execute {
                val bitmap = loadBitmapFromUrl(news.thumbnail)
                handler.post {
                    // Cek apakah ViewHolder masih menampilkan item yang sama (tidak di-recycle)
                    if (holder.ivThumbnail.tag == news.thumbnail) {
                        if (bitmap != null) {
                            holder.ivThumbnail.setImageBitmap(bitmap)
                            holder.ivThumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
                        } else {
                            holder.ivThumbnail.setImageResource(android.R.drawable.ic_menu_gallery)
                        }
                    }
                }
            }
        }

        // Klik berita → buka di browser
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = newsList.size

    private fun loadBitmapFromUrl(urlString: String): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 8000
            connection.readTimeout = 8000
            connection.setRequestProperty("User-Agent", "Mozilla/5.0")
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                BitmapFactory.decodeStream(connection.inputStream)
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
