package com.example.bibliotheque

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityEvaluation : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var editTextComment: EditText
    private lateinit var btnAjouter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        ratingBar = findViewById(R.id.ratingBar)
        editTextComment = findViewById(R.id.editTextComment)
        btnAjouter = findViewById(R.id.btnAjouterEval)

        val bookId = intent.getIntExtra("Id", -1)

        btnAjouter.setOnClickListener {
            val note = ratingBar.rating
            val commentaire = editTextComment.text.toString().trim()

            if (note == 0f) {
                Toast.makeText(this, "Veuillez donner une note.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            BookService.ajouterEvaluation(bookId, note, commentaire)
            Toast.makeText(this, "Évaluation ajoutée!", Toast.LENGTH_SHORT).show()
            finish() // Retour à ActivityDetails
        }
    }
}
