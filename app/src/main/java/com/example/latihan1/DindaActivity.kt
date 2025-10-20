package com.example.latihan1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihan1.adapter.Todoadapter
import com.example.latihan1.adapter.Todoadapter.TodoItemEvents
import com.example.latihan1.databinding.ActivityDindaBinding
import com.example.latihan1.entity.Todo
import com.example.latihan1.usecase.TodoUseCase
import kotlinx.coroutines.launch

class DindaActivity : AppCompatActivity(), TodoItemEvents {
    private lateinit var activityBinding: ActivityDindaBinding
    private  lateinit var todoAdapter: Todoadapter
    private lateinit var todoUseCase: TodoUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityBinding = ActivityDindaBinding.inflate(layoutInflater)
        todoUseCase = TodoUseCase()
        setContentView(activityBinding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        initializeData()
        registerEvents()
    }

    fun registerEvents() {
        activityBinding.tombolTambah.setOnClickListener{
            toCreateTodoPage()
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = Todoadapter(mutableListOf<Todo>(), this)
        activityBinding.container.adapter = todoAdapter
        activityBinding.container.layoutManager = LinearLayoutManager(this)

    }


    override fun onDelete(todo: Todo) {
        val builder = AlertDialog.Builder(this@DindaActivity)
        builder.setTitle("Konfirmasi hapus data")
        builder.setMessage("Apakah anda yakin ingin menghapus data?")

        builder.setPositiveButton("Ya") { _, _ -> // Fix lambda syntax
            lifecycleScope.launch {
                try {
                    todoUseCase.deleteTodo(todo.id)
                    displayMessage("Data berhasil dihapus!")
                } catch (exc: Exception) {
                    displayMessage("Gagal menghapus data: ${exc.message}")
                }
                initializeData()
            }
        }
        builder.setNegativeButton("Tidak", null)
        builder.show()
    }

    fun initializeData() {
        activityBinding.container.visibility = View.GONE
        activityBinding.loading.visibility = View.VISIBLE

        lifecycleScope.launch {
            val data = todoUseCase.getTodo()
            activityBinding.container.visibility = View.VISIBLE
            activityBinding.loading.visibility = View.GONE
            todoAdapter.updateDataset(data)
        }

    }

    fun toCreateTodoPage() {
        val intent = Intent(this, CreateTodooActivity::class.java)
        startActivity(intent)
    }

    fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}