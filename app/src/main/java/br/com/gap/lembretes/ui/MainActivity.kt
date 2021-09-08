package br.com.gap.lembretes.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.gap.lembretes.databinding.ActivityMainBinding
import br.com.gap.lembretes.datasource.TarefaDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {TarefaListaAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTarefas.adapter = adapter
        updateList()

        insertListerners()
        //Data STORE
        //ROOM
    }

    private fun insertListerners() {
        binding.btnInicio.setOnClickListener{
            startActivityForResult(Intent(this,addTarefaActivity::class.java), CREATE_NEW_TASK)

        adapter.listenerEdit = {
           //para teste
            //Log.e("TAG","listenerEdit${it}")

            val intent = Intent(this, addTarefaActivity::class.java)
            intent.putExtra(addTarefaActivity.TAREFA_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

            adapter.listenerDelete = {
              //  Log.e("TAG","listenerDelete${it}")
                TarefaDataSource.deleteTarefa(it)
                updateList()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList(){

        val list = TarefaDataSource.getList()
           if (list.isEmpty()){
              binding.includeEmpty.emptyState.visibility = View.VISIBLE
           }else{
               binding.includeEmpty.emptyState.visibility = View.GONE
           }
        adapter.submitList(list)
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}