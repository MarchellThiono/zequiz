package com.example.zequiz.UI.Guru.ui.buatkuis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zequiz.api.ApiClient
import com.example.zequiz.model.Topik
import kotlinx.coroutines.launch

class BuatKuisViewModel : ViewModel() {

    private val _topikList = MutableLiveData<List<Topik>>()
    val topikList: LiveData<List<Topik>> = _topikList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchTopik() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getTopikList() // pastikan ada endpoint ini
                _topikList.value = response
            } catch (e: Exception) {
                _topikList.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
