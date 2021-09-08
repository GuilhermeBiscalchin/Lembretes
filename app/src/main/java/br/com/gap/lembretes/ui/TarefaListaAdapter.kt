package br.com.gap.lembretes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.gap.lembretes.R
import br.com.gap.lembretes.databinding.ItemTarefaBinding
import br.com.gap.lembretes.model.Tarefa

class TarefaListaAdapter: ListAdapter<Tarefa,TarefaListaAdapter.TarefaViewHolder>(DiffCallback()) {

    var listenerEdit : (Tarefa) -> Unit = {}
    var listenerDelete : (Tarefa) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val binding= ItemTarefaBinding.inflate(inflater,parent,false)
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
       holder.bind(getItem(position))
    }


    inner class TarefaViewHolder(
        private val binding: ItemTarefaBinding):
        RecyclerView.ViewHolder(binding.root){


        fun bind(item: Tarefa) {
            binding.title.text = item.title
            binding.date.text = "${item.date} ${item.hour}"
            binding.ivMore.setOnClickListener{
               showPopup(item)
            }

        }

        private fun showPopup(item: Tarefa) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context,ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }
}

class  DiffCallback : DiffUtil.ItemCallback<Tarefa>(){
    override fun areItemsTheSame(oldItem: Tarefa, newItem: Tarefa) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Tarefa, newItem: Tarefa) = oldItem.id == newItem.id

}