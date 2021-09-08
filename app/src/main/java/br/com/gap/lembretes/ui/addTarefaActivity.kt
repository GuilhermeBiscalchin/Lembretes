package br.com.gap.lembretes.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.gap.lembretes.databinding.ActivityAddTarefaBinding
import br.com.gap.lembretes.datasource.TarefaDataSource
import br.com.gap.lembretes.extensions.format
import br.com.gap.lembretes.extensions.text
import br.com.gap.lembretes.model.Tarefa
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class addTarefaActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTarefaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TAREFA_ID)){
            val tarefaId = intent.getIntExtra(TAREFA_ID,0)
            TarefaDataSource.findById(tarefaId)?.let {
                binding.titulo.text = it.title
                binding.data.text = it.date
                binding.hour.text = it.hour
                binding.descricao.text = it.descricao
            }
        }

        insertListener()
    }

        private fun insertListener(){
            binding.data.editText?.setOnClickListener{
              //apenas teste,para visualizar se foi o click
                //Log.e("TAG","insertListeners:")
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.addOnPositiveButtonClickListener {
                   val timeZone = TimeZone.getDefault()
                    val offset = timeZone.getOffset(Date().time) * -1
                    binding.data.text = Date(it + offset).format()
                }
                datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
            }

            binding.hour.editText?.setOnClickListener{
               val timePicker = MaterialTimePicker.Builder()
                   .setTimeFormat(TimeFormat.CLOCK_24H)
                   .build()
                timePicker.addOnPositiveButtonClickListener{
                  val minute =  if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                    val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                    binding.hour.text = "${hour}:${minute}"
                }

                timePicker.show(supportFragmentManager,null)
            }

            binding.btncancelar.setOnClickListener{
                finish()
            }

            binding.btnTarefa.setOnClickListener{
                val tarefa = Tarefa(
                    title = binding.titulo.text,
                    date = binding.data.text,
                    hour = binding.hour.text,
                    descricao = binding.descricao.text,
                    id = intent.getIntExtra(TAREFA_ID,0)
                )
                TarefaDataSource.insertTarefa(tarefa)
                setResult(Activity.RESULT_OK)
                finish()
            }

        }

        companion object{
            const val TAREFA_ID = "tarefa_id"
        }
    }
