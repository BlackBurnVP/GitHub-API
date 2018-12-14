package com.example.vitalii.myapplication

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.NavDestination


class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    private lateinit var mNavView:NavigationView
    private lateinit var mDrawerLayout:DrawerLayout

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

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        println("BACK PRESSED")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("DESTROYED")
    }

    override fun onResume() {
        super.onResume()
        println("RESUMED")
    }

    override fun onPause() {
        super.onPause()
        println("PAUSED")
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null){
            val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
            currentFocus.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}

