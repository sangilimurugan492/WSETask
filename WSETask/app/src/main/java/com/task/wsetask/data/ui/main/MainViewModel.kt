package com.task.wsetask.data.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.task.wsetask.data.repository.MainRepository
import com.task.wsetask.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getArticles(author : String, page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getArticles(author, page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}