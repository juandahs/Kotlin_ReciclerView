package com.example.reciclerview

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.reciclerview.databinding.ItemListBinding

//Un adaptador vincula los datos con la interfaz gráfica por ende recibe los usuarios y la vista.
class UserAdapter (private val users: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>()
{

    //Son las configuraciones base de la lógica que se vincula con la vista
    //el lateinit esta reservando el espacio en memoria de tipo Context
    private lateinit var  context: Context

    //Se crea una clase dentro de una clase para crear el viewHolder, el cual es el vinculo entre el adaptador y la vista
    //No se le coloca val ya que esta viene del padre.
    // ****** Esta clase es para hacer el vinculo entre la vista y la adaptador **** (Son las propiedades del bidngin de la vista)
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //El ItemList es la vista item_list
        val binding = ItemListBinding.bind(view)

    }

    //Crea las configuraciones de la vista reciclada y el xml y retorna a la etiqueta reciclerView (activitymain)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        //el parent viene de la vista vinculada en el viewHolder

        //Se le pasa las configuraciones. el false se le dice que no hay más configuraciones
        val view =LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        return  ViewHolder(view)
    }

    //El número de veces que se va a repetir la vista vinculada
    override fun getItemCount(): Int = users.size

    //Organiza la información con la vista (estructura la data)
    //el parametro holder hace referencia a la clase interna.
    //(Se asignan valores a la vista)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
            tvName.text = user.getFullName()
            //binding.tvOrder.text = (position + 1).toString()
            (position + 1).toString().also { tvOrder.text = it }

            //Necesita el conexto de la aplicacion
            //El contexto son las configuraciones basicas entre la vista y la logica
            Glide.with(context)
                .load(user.avatar)
                .centerCrop()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgPhoto)
        }
    }
}