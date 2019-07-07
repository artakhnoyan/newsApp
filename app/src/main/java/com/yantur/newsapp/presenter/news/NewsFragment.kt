package com.yantur.newsapp.presenter.news

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yantur.newsapp.R
import com.yantur.newsapp.databinding.FragmentNewsBinding
import com.yantur.newsapp.presenter.checkAndRequestPermission
import com.yantur.newsapp.presenter.news.NewsViewModel.Companion.JSON_PATH
import com.yantur.newsapp.presenter.news.NewsViewModel.Companion.XML_PATH
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModel()

    private val customTabsIntent by lazy { CustomTabsIntent.Builder().run { build() } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentNewsBinding = inflate(
            inflater, R.layout.fragment_news, container, false
        )

        binding.newsViewModel = newsViewModel

        newsViewModel.progressBar.observe(this, Observer {})

        setupAdapter(binding)

        setupExportListeners()

        return binding.root
    }

    private fun setupExportListeners() {
        newsViewModel.jsonExportComplete.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                val jsonText = getString(R.string.json_exported_at, JSON_PATH)
                Toast.makeText(context, jsonText, Toast.LENGTH_LONG).show()
            }
        })
        newsViewModel.xmlExportComplete.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                val xmlText = getString(R.string.xml_exported_at, XML_PATH)
                Toast.makeText(context, xmlText, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupAdapter(binding: FragmentNewsBinding) {
        val adapter = NewsAdapter(newsViewModel)

        binding.rvNews.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvNews.adapter = adapter

        newsViewModel.newsComplete.observe(this, Observer {
            it?.let { data ->
                adapter.setNews(data)
            }
        })

        newsViewModel.newsError.observe(this, Observer {
            it?.let {
                newsViewModel.isError = true
            }
        })

        newsViewModel.newsItem.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                customTabsIntent.launchUrl(context, Uri.parse(it.url))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(R.string.export_as_json).apply {
            setOnMenuItemClickListener {
                newsViewModel.lastExportClicked = ExportType.JSON
                if (checkAndRequestPermission(PERMISSION_REQUEST_CODE)) {
                    newsViewModel.exportAsJson()
                }
                true
            }
        }
        menu.add(R.string.export_as_xml).apply {
            setOnMenuItemClickListener {
                newsViewModel.lastExportClicked = ExportType.JSON
                if (checkAndRequestPermission(PERMISSION_REQUEST_CODE)) {
                    newsViewModel.exportAsXml()
                }
                true
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                newsViewModel.continueExport()
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE: Int = 125
    }

}