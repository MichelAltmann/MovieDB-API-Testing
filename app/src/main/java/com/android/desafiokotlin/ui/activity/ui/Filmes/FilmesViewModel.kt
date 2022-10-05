package com.android.desafiokotlin.ui.activity.ui.Filmes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.desafiokotlin.model.Filme

class FilmesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val arrayList = MutableLiveData<ArrayList<Filme>>().apply {
        arrayListOf<Filme>()
    }
}