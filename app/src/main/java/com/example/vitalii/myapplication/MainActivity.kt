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
import android.content.Intent
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout
    lateinit var recyclerView: RecyclerView
    var posts: MutableList<GitHubPOJO> = ArrayList()

    private fun addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        val shortcutIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )
        shortcutIntent.action = Intent.ACTION_MAIN

        val addIntent = Intent()
            Intent.ShortcutIconResource.fromContext(
                applicationContext,
                R.mipmap.ic_launcher
            )

        addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        addIntent.putExtra("duplicate", false)
        applicationContext.sendBroadcast(addIntent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }
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

                override fun onFailure(call: Call<List<GitHubPOJO>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error occured while networking", Toast.LENGTH_SHORT).show()
                }
            })
        recyclerView.addOnItemTouchListener(
            ClickListener(this, recyclerView, object : ClickListener.OnItemClickListener {
                override fun onLongItemClick(view: View?, position: Int) {
                }

                override fun onItemClick(view: View, position: Int) {
                    val url = posts!![position].htmlUrl
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }
            })
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
