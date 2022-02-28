package com.example.nuevosimulacro

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nuevosimulacro.databinding.ItemResultBinding

class ElAdapter(var listaUsuarios: List<Usuario>) : RecyclerView.Adapter<ElAdapter.TextoViewHolder>(){

    class TextoViewHolder(var itemBinding : ItemResultBinding) : RecyclerView.ViewHolder(itemBinding.root)

    fun actualizarListaUsuarios(listaUsuarios : List<Usuario>) {
        this.listaUsuarios = listaUsuarios
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {

        holder.itemBinding.tvNombre.text = listaUsuarios[position].name.first
        holder.itemBinding.tvApellido.text = listaUsuarios[position].name.last
        holder.itemBinding.layoutPrincipal.setOnClickListener {
            Toast.makeText(holder.itemBinding.root.context, "Vivo en ${listaUsuarios[position].location.city}", Toast.LENGTH_LONG).show()
        }
    }
}