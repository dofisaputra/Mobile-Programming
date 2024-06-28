package com.dofi.tb1.di

import com.dofi.tb1.view.model.DummyApiViewModel
import com.dofi.tb1.view.model.UserApiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DummyApiViewModel(get()) }
    viewModel { UserApiViewModel(get()) }
}