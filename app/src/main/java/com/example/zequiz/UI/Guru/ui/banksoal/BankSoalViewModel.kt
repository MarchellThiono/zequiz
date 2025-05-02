package com.example.zequiz.UI.Guru.ui.banksoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zequiz.model.Soal

class BankSoalViewModel : ViewModel() {

    private val _listSoal = MutableLiveData<MutableList<Soal>>(mutableListOf())
    val listSoal: LiveData<MutableList<Soal>> get() = _listSoal

    fun tambahSoal(soal: Soal): Boolean {
        val currentList = _listSoal.value ?: mutableListOf()
        return if (currentList.size < 15) {
            currentList.add(0, soal)
            _listSoal.value = currentList
            true
        } else {
            false
        }
    }

    fun hapusSoal(soal: Soal) {
        val currentList = _listSoal.value ?: return
        currentList.remove(soal)
        _listSoal.value = currentList
    }

    fun getSemuaSoal(): List<Soal> {
        return _listSoal.value ?: emptyList()
    }
}
