package com.a1developers.whatsappstatussaver.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1developers.whatsappstatussaver.data.StatusRepo

class StatusViewModelFactory(private val repo : StatusRepo): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatusViewModel(repo) as T
    }
}