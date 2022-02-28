package com.example.nuevosimulacro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nuevosimulacro.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainActivityViewModel by viewModels()
    private lateinit var adapter: ElAdapter
    private var listaResultados = listOf<Usuario>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()

        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        viewModel.hacerLlamada()

        binding.bChicas.setOnClickListener {
            adapter.actualizarListaUsuarios(listaResultados.filter { it.gender == "female" })
        }
        binding.bChicos.setOnClickListener {
            adapter.actualizarListaUsuarios(listaResultados.filter { it.gender == "male" })
        }
        binding.bTodos.setOnClickListener {
            adapter.actualizarListaUsuarios(listaResultados)
        }
    }

    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible)
                setVisible()
            else
                setGone()
        }

        viewModel.responseText.observe(this) {
            val gson = Gson()
            val results = gson.fromJson(it, Results::class.java)
            listaResultados = results.results
            adapter = ElAdapter(listaResultados)
            binding.recyclerview.adapter = adapter
        }
    }

    private fun setVisible(){
        binding.pbDownloading.visibility = View.VISIBLE
    }
    private fun setGone(){
        binding.pbDownloading.visibility = View.GONE
    }

}