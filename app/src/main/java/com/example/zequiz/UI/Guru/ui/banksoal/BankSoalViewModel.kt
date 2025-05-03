package com.example.zequiz.UI.Guru.ui.banksoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zequiz.model.SoalRes

class BankSoalViewModel : ViewModel() {

    private val _listSoal = MutableLiveData<MutableList<SoalRes>>(mutableListOf())
    val listSoal: LiveData<MutableList<SoalRes>> get() = _listSoal

    fun tambahSoal(soal: SoalRes): Boolean {
        val currentList = _listSoal.value ?: mutableListOf()
        return if (currentList.size < 15) {
            currentList.add(0, soal)
            _listSoal.value = currentList
            true
        } else {
            false
        }
    }

    fun hapusSoal(soal: SoalRes) {
        val currentList = _listSoal.value ?: return
        currentList.remove(soal)
        _listSoal.value = currentList
    }

    fun getSemuaSoal(): List<SoalRes> {
        return _listSoal.value ?: emptyList()
    }
}
