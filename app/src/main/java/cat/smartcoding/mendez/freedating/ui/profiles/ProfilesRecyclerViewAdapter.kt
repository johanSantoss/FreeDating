package cat.smartcoding.mendez.freedating.ui.profiles

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.smartcoding.mendez.freedating.databinding.ProfilesFragmentItemBinding
import cat.smartcoding.mendez.freedating.ui.profiles.placeholder.PlaceholderContent.PlaceholderItem
import com.bumptech.glide.Glide

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProfilesRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<ProfilesRecyclerViewAdapter.ViewHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        return ViewHolder(ProfilesFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        mContext?.let {
            Glide.with(it)
                .load(Uri.parse(item.img))
                .fitCenter()
                .centerCrop()
                .into(holder.imgView)
        }
        holder.edadView.text = item.edat
        holder.nameView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ProfilesFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val edadView: TextView = binding.edad
        val nameView: TextView = binding.content
        val imgView: ImageView = binding.imageView8

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }

    }

}