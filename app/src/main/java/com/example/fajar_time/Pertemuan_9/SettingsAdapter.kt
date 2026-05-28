package com.example.fajar_time.Pertemuan_9

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fajar_time.R

data class SettingsItem(
    val title: String,
    val description: String,
    val iconResId: Int
)

class SettingsAdapter(
    context: Context,
    private val settingsItems: List<SettingsItem>
) : ArrayAdapter<SettingsItem>(context, 0, settingsItems) {

    override fun getView(position: Int, convertView: android.view.View?, parent: ViewGroup): android.view.View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false)

        val item = getItem(position)

        if (item != null) {
            val ivIcon = view.findViewById<ImageView>(R.id.ivIcon)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

            ivIcon.setImageResource(item.iconResId)
            tvTitle.text = item.title
            tvDescription.text = item.description
        }

        return view
    }
}

