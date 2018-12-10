package com.example.vitalii.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vitalii.myapplication.api.GitHubPOJO
import java.util.ArrayList

class PostsAdapter(private var posts: MutableList<GitHubPOJO>? = ArrayList()) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts!![position]
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.post.text = post.name
//        } else {
//            holder.post.text = GitHubServicej().id.toString()
//        }
        holder.site.text = post.fullName     // Change what you wanna see
    }
    fun updateAdapterList(newList: MutableList<GitHubPOJO>) {
        posts!!.clear()
        posts!!.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return posts?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: TextView = itemView.findViewById<View>(R.id.postitem_post) as TextView
        var site: TextView = itemView.findViewById<View>(R.id.postitem_site) as TextView
    }
}