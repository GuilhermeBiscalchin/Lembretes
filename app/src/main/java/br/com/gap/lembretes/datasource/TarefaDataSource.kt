package br.com.gap.lembretes.datasource

import br.com.gap.lembretes.model.Tarefa

object TarefaDataSource {
private val list = arrayListOf<Tarefa>()
    fun getList() = list.toList()

    fun  insertTarefa(tarefa: Tarefa){
        if(tarefa.id == 0){
            list.add(tarefa.copy(id = list.size + 1))
        }else{
            list.remove(tarefa)
            list.add(tarefa)
        }

    }

    fun findById(tarefaId: Int) = list.find {it.id == tarefaId }

    fun deleteTarefa(tarefa: Tarefa) {
        list.remove(tarefa)
    }
}