package com.example.vitalii.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.example.vitalii.myapplication.api.GitHubPOJO
import com.example.vitalii.myapplication.api.GitHubService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var posts: MutableList<GitHubPOJO> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    fun click(view: View){
        posts = ArrayList()

        val name:String = findViewById<EditText>(R.id.editText).text.toString()
        recyclerView = findViewById(R.id.posts_recycle_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = PostsAdapter(posts)
        recyclerView.adapter = adapter
        val service = Retrofit.Builder()
            .baseUrl("https://api.github.com/") // Change your api
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
        service.retrieveRepositories(name)
            .enqueue(object : Callback<List<GitHubPOJO>> {
                override fun onResponse(call: Call<List<GitHubPOJO>>, response: Response<List<GitHubPOJO>>) {
                    posts.addAll(response.body()!!)
                    response.body()?.forEach { println ("TAG_: $it")}
                    recyclerView.adapter?.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<GitHubPOJO>>, t: Throwable) = t.printStackTrace()
            })
    }
}
