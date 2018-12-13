package com.example.vitalii.myapplication.fragments


import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.vitalii.myapplication.ClickListener
import com.example.vitalii.myapplication.PostsAdapter
import com.example.vitalii.myapplication.R
import com.example.vitalii.myapplication.api.GitHubPOJO
import com.example.vitalii.myapplication.api.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainFragment : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var name:String
    lateinit var btn:Button
    private var service: GitHubService? = null
    private var obj: Callback<List<GitHubPOJO>>? = null
    private var retrofit:Retrofit? = null
    private var repos: Call<List<GitHubPOJO>>? = null
    var layoutManager:LinearLayoutManager? = null
    var posts: MutableList<GitHubPOJO> = ArrayList()
    val adapter = PostsAdapter(posts)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mRecyclerView = view!!.findViewById(R.id.posts_recycle_view)
        layoutManager = LinearLayoutManager(activity!!)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter

        btn = view.findViewById(R.id.button)
        btn.setOnClickListener (onClick)

        return view
    }

    val onClick = View.OnClickListener {

        hideKeyboard()
        name = view!!.findViewById<EditText>(R.id.txt_user_name).text.toString()
        when {
            name.isEmpty() -> Toast.makeText(activity!!,"You do not entered User's name",Toast.LENGTH_LONG).show()
            isInternet() -> {
                serverConnect()
                recyclerClick()
            }
            else -> Toast.makeText(activity!!,"Please, check your internet connection!",Toast.LENGTH_LONG).show()
        }
    }

    private fun serverConnect(){
        obj = object :Callback<List<GitHubPOJO>>{
            override fun onResponse(call: Call<List<GitHubPOJO>>, response: Response<List<GitHubPOJO>>) {
                adapter.updateAdapterList(response.body()!!)
                response.body()?.forEach { println("TAG_: $it") }
            }

            override fun onFailure(call: Call<List<GitHubPOJO>>, t: Throwable) {

            }
        }
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/") // CHANGE API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit?.create(GitHubService::class.java)
        repos = service?.retrieveRepositories(name)
        repos?.enqueue(obj)
    }

    private fun recyclerClick(){
        mRecyclerView = view!!.findViewById(R.id.posts_recycle_view)
        mRecyclerView.addOnItemTouchListener(ClickListener(this.activity!!, mRecyclerView, object : ClickListener.OnItemClickListener {
            override fun onLongItemClick(view: View?, position: Int) {
            }

            override fun onItemClick(view: View, position: Int) {
                val url = posts[position].htmlUrl
                println("URL =  $url")
                view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToWebFragment(url))
//                    OPEN LINK IN SYSTEM DEFAULT BROWSER
//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    startActivity(browserIntent)
                    }
                }
            )
        )
    }

    private fun isInternet():Boolean{
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork!=null
    }

    private fun hideKeyboard(){
        val inputMethodManager = this.activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity!!.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(this.activity!!.currentFocus!!.windowToken, 0)
        }
    }
    override fun onResume() {
        super.onResume()
        repos?.clone()?.enqueue(obj)
        recyclerClick()
    }
}

