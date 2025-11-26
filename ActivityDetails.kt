package com.example.bibliotheque

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ActivityDetails : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var auteur: TextView
    private lateinit var genre: TextView
    private lateinit var page: TextView
    private lateinit var description: TextView
    private lateinit var moyenneNote: TextView
    private lateinit var nombreCommentaires: TextView
    private lateinit var btnSupprimer: Button
    private lateinit var btnModifier: Button
    private lateinit var btnRetour: Button
    private lateinit var btnEval: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getIntExtra("bookId", -1)
        val book = BookService.afficherParId(id)

        image = findViewById(R.id.img)
        title = findViewById(R.id.title)
        auteur = findViewById(R.id.auteur)
        genre = findViewById(R.id.genre)
        page = findViewById(R.id.page)
        description = findViewById(R.id.descP)
        btnSupprimer = findViewById(R.id.btnS)
        btnModifier = findViewById(R.id.btnM)
        btnRetour = findViewById(R.id.btnR)
        btnEval = findViewById(R.id.btnEval)

        moyenneNote = TextView(this)
        nombreCommentaires = TextView(this)

        book?.let {
            image.setImageResource(it.photo)
            title.text = "Titre : ${it.titre}"
            auteur.text = "Auteur : ${it.auteur}"
            genre.text = "Genre : ${it.genre}"
            page.text = "Pages : ${it.page}"
            description.text = "Description : ${it.description}"

            val moyenne = if (it.evaluations.isNotEmpty())
                it.evaluations.map { pair -> pair.first }.average()
            else 0.0

            val nbComments = it.evaluations.size

            moyenneNote.text = "Moyenne : ${String.format("%.1f", moyenne)} / 5"
            moyenneNote.setTextColor(Color.parseColor("#00008B"))

            nombreCommentaires.text = "Commentaires : $nbComments"
            nombreCommentaires.setTextColor(Color.parseColor("#00008B"))


            val layout = findViewById<LinearLayout>(R.id.rootLayout)
            layout.addView(moyenneNote)
            layout.addView(nombreCommentaires)
        }

        btnSupprimer.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Supprimer Livre")
                .setMessage("Voulez-vous vraiment supprimer ce livre ?")
                .setPositiveButton("Oui") { dialog, _ ->
                    BookService.supprimer(id)
                    Toast.makeText(this, "Livre supprimé", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    finish()
                }
                .setNegativeButton("Non") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        btnModifier.setOnClickListener {
            val intent = Intent(this, ActivityFormulaire::class.java)
            intent.putExtra("bookId", id)
            startActivity(intent)
        }


        btnRetour.setOnClickListener { finish() }

        btnEval.setOnClickListener {
            val intentEval = Intent(this, ActivityEvaluation::class.java)
            intentEval.putExtra("Id", id)
            startActivity(intentEval)
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("bookId", -1)
        val book = BookService.afficherParId(id)
        book?.let {
            val moyenne = if (it.evaluations.isNotEmpty())
                it.evaluations.map { pair -> pair.first }.average()
            else 0.0

            val nbComments = it.evaluations.size
            moyenneNote.text = "Moyenne : ${String.format("%.1f", moyenne)} / 5"
            nombreCommentaires.text = "Commentaires : $nbComments"
        }
    }
}
