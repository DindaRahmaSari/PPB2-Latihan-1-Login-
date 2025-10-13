package com.example.latihan1.usecase

import com.example.latihan1.entity.Todo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class TodoUseCase {
    val db = Firebase.firestore

    suspend fun getTodo(): List<Todo> {
        return try {
            val snapshot = db.collection("Tododinda")
                .get()
                .await()

            // ✅ PERBAIKAN: Lakukan mapping dari dokumen ke objek Todo
            // .map akan otomatis mengembalikan daftar kosong jika snapshot.isEmpty benar.
            snapshot.documents.map { document ->
                // Pastikan nama field ("title", "description") sesuai dengan di Firebase
                Todo(
                    id = document.id,
                    title = document.getString("title") ?: "", // Gunakan null-check yang aman
                    description = document.getString("Description") ?: ""
                )
            }
        } catch (exc: Exception) {
            // Sebaiknya log error di sini
            // Log.e("TodoUseCase", "Error fetching todos", exc)

            // Atau tangani error spesifik, tapi untuk saat ini, lempar Exception
            throw Exception("Gagal mengambil data Todo: ${exc.message}")
        }
    }
}