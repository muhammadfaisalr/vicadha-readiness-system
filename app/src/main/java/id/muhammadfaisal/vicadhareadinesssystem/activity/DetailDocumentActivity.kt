package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailDocumentBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.DocumentFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant

class DetailDocumentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailDocumentBinding

    private lateinit var document: DocumentFirebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailDocumentBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()
        this.binding.apply {
            val document = document
            this.textTitle.text = document.title
            this.webView.loadUrl(document.url)
            GeneralHelper.makeClickable(this@DetailDocumentActivity, this.imageBack, this.exfabDownload)
        }

        this.configWebview()
    }

    private fun configWebview() {
        this.binding.apply {
            this.webView.settings.javaScriptEnabled = true
            this.webView.webChromeClient = WebChromeClient()
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)
        this.document = bundle!!.getSerializable(Constant.Key.DOCUMENT) as DocumentFirebase
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageBack) {
            this.finish()
        }

        if (p0 == this.binding.exfabDownload) {
            this.processDownload()
        }
    }

    private fun processDownload() {

    }
}