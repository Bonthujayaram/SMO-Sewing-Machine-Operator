package com.example.smo1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class JobDashboardFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout with WebView
        return inflater.inflate(R.layout.fragment_job_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webview)

        val webSettings: WebSettings = webView.settings

        // Enable JavaScript and essential settings for fast loading
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.loadsImagesAutomatically = true
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                println("Started loading: $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                println("Finished loading: $url")
            }

            override fun onReceivedError(view: WebView?, request: android.webkit.WebResourceRequest?, error: android.webkit.WebResourceError?) {
                println("Error: ${error?.description}")
            }
        }

        // Load the web URL (Replace with your actual URL)
        webView.loadUrl("http://20.30.65.174:5173/overall")
    }
}
