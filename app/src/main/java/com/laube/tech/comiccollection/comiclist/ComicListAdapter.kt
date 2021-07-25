package com.laube.tech.comiccollection.comiclist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.laube.tech.comiccollection.R
import com.laube.tech.comiccollection.databinding.ComicListItemViewholderBinding
import com.laube.tech.comiccollection.models.response.Result


class ComicListAdapter(var comics: ArrayList<Result>) :
    RecyclerView.Adapter<ComicListAdapter.ComicListViewHolder>() {

    fun updateList(newList: List<Result>) {
        comics.clear()
        comics.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_list_item_viewholder, parent, false)
        return ComicListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicListViewHolder, position: Int) {
        with(holder) {
            binding.issueTitleTextView.text = comics[position].title
            binding.comicListItemLayout.setOnClickListener {
                val id = comics[position].id
                val action =
                    ComicListFragmentDirections.actionMainFragmentToComicDetailFragment(id.toString())
                Navigation.findNavController(holder.view).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    class ComicListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val binding = ComicListItemViewholderBinding.bind(view)
    }

    interface ComicListListener {
        fun onSelected(item: Result)
    }
}