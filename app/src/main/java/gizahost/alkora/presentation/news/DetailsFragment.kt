package gizahost.alkora.presentation.news

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import gizahost.alkora.R
import gizahost.alkora.utils.Dialogs


class DetailsFragment : Fragment() {

    private var url:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()

        url = requireArguments().getString("url")

        val webView = view.findViewById(R.id.details_webView) as WebView

        webView.setWebViewClient(MyBrowser(view))

        webView.getSettings().setLoadsImagesAutomatically(true)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)

        webView.loadUrl(url.toString())

    }

    private class MyBrowser(view: View) : WebViewClient() {
        private var mProgress: Dialog = Dialogs.createProgressBarDialog(view.context, "")

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if(!mProgress.isShowing){
                mProgress.show()
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if(mProgress.isShowing){
                mProgress.cancel()
            }
        }



//        override fun onReceivedHttpError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            errorResponse: WebResourceResponse?
//        ) {
//            super.onReceivedHttpError(view, request, errorResponse)
//            if(mProgress.isShowing){
//                mProgress.cancel()
//            }
//        }
//
//        override fun onReceivedSslError(
//            view: WebView?,
//            handler: SslErrorHandler?,
//            error: SslError?
//        ) {
//            super.onReceivedSslError(view, handler, error)
//            if(mProgress.isShowing){
//                mProgress.cancel()
//            }
//        }

//        override fun onReceivedError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            error: WebResourceError?
//        ) {
//            super.onReceivedError(view, request, error)
//            if(mProgress.isShowing){
//                mProgress.cancel()
//            }
//        }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireView().findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

}