package com.mat.shoppinglist

import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "http://47c66b7ff47d.eu.ngrok.io/"

val module = module {
    single { Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(Webservice::class.java)
    }

    single { Repository(get()) }

    viewModel { AccessViewModel(get()) }
    viewModel { ListViewModel(get()) }
}