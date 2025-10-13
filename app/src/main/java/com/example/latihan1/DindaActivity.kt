package com.example.latihan1

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihan1.adapter.Todoadapter
import com.example.latihan1.databinding.ActivityDindaBinding
import com.example.latihan1.databinding.ItemTodoBinding
import com.example.latihan1.usecase.TodoUseCase
import kotlinx.coroutines.launch

class DindaActivity : AppCompatActivity() {
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
    }
    fun setupRecyclerView() {
        todoAdapter = Todoadapter(mutableListOf())
        activityBinding.container.adapter = todoAdapter
        activityBinding.container.layoutManager = LinearLayoutManager(this)
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
}