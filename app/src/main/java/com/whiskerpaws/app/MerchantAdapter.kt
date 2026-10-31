package com.whiskerpaws.app

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MerchantAdapter(private val merchantList: List<MerchantModel>) :
    RecyclerView.Adapter<MerchantAdapter.MerchantViewHolder>() {

    class MerchantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageLogo: ImageView = itemView.findViewById(R.id.imageMerchantLogo)
        val textName: TextView = itemView.findViewById(R.id.textMerchantName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_merchant, parent, false)
        return MerchantViewHolder(view)
    }

    override fun onBindViewHolder(holder: MerchantViewHolder, position: Int) {
        val merchant = merchantList[position]
        holder.textName.text = merchant.name
        holder.imageLogo.setImageResource(merchant.logoDrawableId)

        // BIND CLICK ACTION: Launch item in a themed internal Chrome Custom Tab
        holder.itemView.setOnClickListener { context ->
            val builder = CustomTabsIntent.Builder()
            
            // Set matching blue custom color theme accents to keep branding sharp
            builder.setShowTitle(true)
            builder.setInstantAppsEnabled(true)
            
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context.context, Uri.parse(merchant.url))
        }
    }

    override fun getItemCount(): Int = merchantList.size
}
