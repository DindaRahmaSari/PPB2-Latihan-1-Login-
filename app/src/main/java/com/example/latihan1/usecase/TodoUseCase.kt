package com.example.latihan1.usecase

import com.example.latihan1.entity.Todo
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class TodoUseCase {
    val db: FirebaseFirestore = Firebase.firestore


    suspend fun getTodo(): List<Todo> {
        return try {
            val snapshot = db.collection("Tododinda")
                .get()
                .await()

            snapshot.documents.map { document ->
                Todo(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: ""
                )
            }
        } catch (exc: Exception) {
            throw Exception("Gagal mengambil data Todo: ${exc.message}")
        }
    }

    suspend fun createTodo(todo: Todo): Todo {
        val data = hashMapOf(
            "title" to todo.title,
            "description" to todo.description
        )

        return try {
            val docRef = db.collection("Tododinda").add(data).await()

            todo.copy(id = docRef.id)
        } catch (exc: Exception) {
            throw Exception("Gagal membuat Todo: ${exc.message}") // Improved error message
        }
    }

    suspend fun deleteTodo(id: String) {
        try {
            db.collection("Tododinda")
                .document(id)
                .delete()
                .await()

        } catch (exc: Exception) {
            throw  Exception(exc.message)
        }
    }
}