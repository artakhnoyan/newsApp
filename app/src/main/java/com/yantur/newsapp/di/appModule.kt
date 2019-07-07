package com.yantur.newsapp.di

import android.os.Environment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yantur.newsapp.data.api.APIClient
import com.yantur.newsapp.data.api.NewsService
import com.yantur.newsapp.data.api.RetrofitClient
import com.yantur.newsapp.data.export.ExportRepositoryImpl
import com.yantur.newsapp.data.export.JsonExportService
import com.yantur.newsapp.data.export.XmlExportService
import com.yantur.newsapp.data.export.directory.DirectoryService
import com.yantur.newsapp.data.export.directory.DirectoryServiceImpl
import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.NewsDataRepository
import com.yantur.newsapp.data.news.NewsDataSource
import com.yantur.newsapp.data.news.apinews.ApiNews
import com.yantur.newsapp.data.news.apinews.ApiNewsDataSource
import com.yantur.newsapp.data.news.apinews.ApiNewsItem
import com.yantur.newsapp.data.news.apinews.ApiNewsRepository
import com.yantur.newsapp.data.news.bbc.BBCDataSource
import com.yantur.newsapp.data.news.bbc.BBCNews
import com.yantur.newsapp.data.news.bbc.BBCNewsRepository
import com.yantur.newsapp.domain.DataStoreRepository
import com.yantur.newsapp.domain.DataStoreUseCase
import com.yantur.newsapp.domain.export.ExportUseCase
import com.yantur.newsapp.domain.news.ApiNewsUseCase
import com.yantur.newsapp.domain.news.BBCNewsUseCase
import com.yantur.newsapp.domain.news.NewsDataStoreUseCase
import com.yantur.newsapp.presenter.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.simpleframework.xml.core.Persister

const val JSON_REPO: String = "json_repo"
const val XML_REPO: String = "xml_repo"
const val JSON_USE_CASE: String = "json_use_case"
const val XML_USE_CASE: String = "xml_use_case"
const val BBC_DATA_SOURCE: String = "bbc_data_source"
const val API_DATA_SOURCE: String = "api_data_source"


val appModule: Module = module {

    single<Gson> { GsonBuilder().setLenient().create() }

    single<APIClient> { RetrofitClient() }
    factory { get<APIClient>().createService(NewsService::class.java) }

    factory<NewsDataSource<BBCNews>>(named(BBC_DATA_SOURCE)) { BBCDataSource(get()) }
    factory { BBCNewsRepository(get(named(BBC_DATA_SOURCE))) }
    factory { BBCNewsUseCase(get()) }

    factory<NewsDataSource<List<ApiNewsItem>>>(named(API_DATA_SOURCE)) { ApiNewsDataSource(get()) }
    factory { ApiNewsRepository(get(named(API_DATA_SOURCE))) }
    factory { ApiNewsUseCase(get()) }

    factory<DirectoryService> { DirectoryServiceImpl(Environment.getExternalStorageDirectory()) }
    factory { JsonExportService(get(), get()) }
    factory { XmlExportService(Persister(), get()) }

    factory(named(JSON_REPO)) { ExportRepositoryImpl(get<JsonExportService>()) }
    factory(named(XML_REPO)) { ExportRepositoryImpl(get<XmlExportService>()) }

    factory(named(JSON_USE_CASE)) { ExportUseCase(get(named(JSON_REPO))) }
    factory(named(XML_USE_CASE)) { ExportUseCase(get(named(XML_REPO))) }

    factory<DataStoreRepository<List<News>>> { NewsDataRepository() }
    factory<DataStoreUseCase<List<News>>> { NewsDataStoreUseCase(get()) }

    viewModel {
        NewsViewModel(
            listOf(get<BBCNewsUseCase>(), get<ApiNewsUseCase>()),
            get(named(JSON_USE_CASE)),
            get(named(XML_USE_CASE)),
            get()
        )
    }

}