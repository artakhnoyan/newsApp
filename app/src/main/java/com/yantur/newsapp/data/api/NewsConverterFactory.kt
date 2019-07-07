@file:Suppress("DEPRECATION")

package com.yantur.newsapp.data.api

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.reflect.Type

class NewsConverterFactory : Converter.Factory() {

    private val xmlConverter by lazy { SimpleXmlConverterFactory.create() }
    private val jsonConverter by lazy { GsonConverterFactory.create() }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        annotations.forEach {
            return when (it) {
                is Xml -> xmlConverter.responseBodyConverter(type, annotations, retrofit)
                is Json -> jsonConverter.responseBodyConverter(type, annotations, retrofit)
                else -> throw IllegalArgumentException("Converter not supported")
            }
        }

        return null
    }
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Xml

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Json