package com.example.latihan1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.latihan1.databinding.ActivityCreateTodooBinding
import com.example.latihan1.entity.Todo
import com.example.latihan1.usecase.TodoUseCase
import kotlinx.coroutines.launch

class CreateTodooActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTodooBinding;
    private lateinit var todoUseCase: TodoUseCase;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateTodooBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoUseCase = TodoUseCase()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        registerEvents()
    }
    fun registerEvents() {
        binding.tombolTambah.setOnClickListener {
            saveDataToFirestore()
        }
    }

    fun saveDataToFirestore() {
        val todo = Todo(
            id = "",
            title = binding.title.text.toString(),
            description = binding.description.text.toString(),
        )

        lifecycleScope.launch {
            try {
            todoUseCase.createTodo(todo)
            Toast.makeText(this@CreateTodooActivity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
        } catch (exc: Exception) {
                Toast.makeText(this@CreateTodooActivity, exc.message, Toast.LENGTH_SHORT).show()


            }
        }

        toTodoListActivity()
    }

    private fun toTodoListActivity() {
        val intent = Intent(this, DindaActivity::class.java)
        startActivity(intent)
        finish()
    }
}
