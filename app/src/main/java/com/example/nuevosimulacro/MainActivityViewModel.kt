package com.example.nuevosimulacro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel : ViewModel() {

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText : LiveData<String>
        get() = _responseText

    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseTextInMainThread(value : String) = withContext(Dispatchers.Main){
        _responseText.value = value
    }

    fun hacerLlamada() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)

                val client = OkHttpClient()
                val request = Request.Builder()
                request.url("https://randomuser.me/api/?results=100")

                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        CoroutineScope(Dispatchers.Main).launch {
                            println("Ha fallado la llamada.")
                            setResponseTextInMainThread("Fallo en la request.")
                            setIsVisibleInMainThread(false)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            CoroutineScope(Dispatchers.Main).launch {
                                setIsVisibleInMainThread(false)
                                setResponseTextInMainThread(body)
                            }
                        }
                    }
                })
            }
        }
    }
}