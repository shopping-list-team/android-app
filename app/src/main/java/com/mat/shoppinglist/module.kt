package com.mat.shoppinglist

import com.google.gson.GsonBuilder
import com.mat.shoppinglist.model.Repository
import com.mat.shoppinglist.model.Webservice
import com.mat.shoppinglist.viewmodel.AccessViewModel
import com.mat.shoppinglist.viewmodel.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "http://30485e7a6e6b.eu.ngrok.io/"

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