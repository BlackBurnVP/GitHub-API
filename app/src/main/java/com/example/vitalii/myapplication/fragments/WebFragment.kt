package com.example.vitalii.myapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.vitalii.myapplication.R
import com.example.vitalii.myapplication.fragments.WebFragmentArgs

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WebFragment : Fragment() {

    val URL:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        val webView = view!!.findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        (WebViewClient())
        val arg = WebFragmentArgs.fromBundle(arguments)
        webView.loadUrl(arg.navUrl)
        return view
    }


}
