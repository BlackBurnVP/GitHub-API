package com.example.vitalii.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.NavDestination


class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    private lateinit var mNavView:NavigationView
    private lateinit var mDrawerLayout:DrawerLayout
//    lateinit var recyclerView: RecyclerView
//    lateinit var response2:List<GitHubPOJO>
//    var posts: MutableList<GitHubPOJO> = ArrayList()

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
        addIntent.putExtra("duplicate", false)  //may it's already there so don't duplicate
        applicationContext.sendBroadcast(addIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavView = findViewById(R.id.nav_view)

        navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController,mDrawerLayout)
        NavigationUI.setupWithNavController(mNavView, navController)

        navController.addOnNavigatedListener { nc: NavController, nd: NavDestination ->
            if (nd.id == nc.graph.startDestination) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(mDrawerLayout,navController)
    }

//    fun onClick(view:View){
//        posts = ArrayList()
//
//        val name:String = findViewById<EditText>(R.id.editText).text.toString()
//        recyclerView = findViewById(R.id.posts_recycle_view)
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//
//        val adapter = PostsAdapter(posts)
//        recyclerView.adapter = adapter
//        //HIDE KEYBOARD
//        val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
//
//        val service = Retrofit.Builder()
//            .baseUrl("https://api.github.com/") // CHANGE API
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(GitHubService::class.java)
//        service.retrieveRepositories(name)
//            .enqueue(object : Callback<List<GitHubPOJO>> {
//                override fun onResponse(call: Call<List<GitHubPOJO>>, response: Response<List<GitHubPOJO>>) {
//                    response2 = response.body()!!
//
//                    posts.addAll(response.body()!!)
//                    response.body()?.forEach { println ("TAG_: $it")}
//                    //recyclerView.adapter?.notifyDataSetChanged()
//                }
//
//                override fun onFailure(call: Call<List<GitHubPOJO>>, t: Throwable) {
//                    Toast.makeText(this@MainActivity, "Error occured while networking", Toast.LENGTH_SHORT).show()
//                }
//            })
//        recyclerView.addOnItemTouchListener(
//            ClickListener(this, recyclerView, object : ClickListener.OnItemClickListener {
//                override fun onLongItemClick(view: View?, position: Int) {
//                }
//
//                override fun onItemClick(view: View, position: Int) {
//                    val url = posts!![position].htmlUrl
//                    println("URL =  $url")
//                    view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToWebFragment(url))
//
//                    //OPEN LINK IN SYSTEM DEFAULT BROWSER
////                  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
////                  startActivity(browserIntent)
//                }
//            })
//        )
//    }
}

