package com.example.bibliotheque

import Book
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ActivityFormulaire : AppCompatActivity() {

    private var imgURI: Uri? = null
    private var editingBookId: Int? = null

    companion object {
        private const val PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)

        val titre = findViewById<EditText>(R.id.titre)
        val auteur = findViewById<EditText>(R.id.auteur)
        val page = findViewById<EditText>(R.id.page)
        val genre = findViewById<Spinner>(R.id.genre)
        val desc = findViewById<EditText>(R.id.desc)
        val imgView = findViewById<ImageView>(R.id.imgId)
        val btnImg = findViewById<Button>(R.id.btnImg)
        val btnAjouter = findViewById<Button>(R.id.btnA)
        val btnModifier = findViewById<Button>(R.id.btnM)
        val btnRetour = findViewById<Button>(R.id.btnR)

        val genres = listOf("Science", "Roman", "Historique", "Policier", "Philosophie")
        genre.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
            .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        val bookId = intent.getIntExtra("bookId", -1)
        if (bookId != -1) {
            editingBookId = bookId
            val book = BookService.afficherParId(bookId)
            book?.let {
                titre.setText(it.titre)
                auteur.setText(it.auteur)
                page.setText(it.page.toString())
                desc.setText(it.description)
                val indexG = genres.indexOf(it.genre)
                if (indexG >= 0) genre.setSelection(indexG)
                imgView.setImageResource(it.photo)
            }
            btnAjouter.visibility = View.GONE
            btnModifier.visibility = View.VISIBLE
        } else {
            btnAjouter.visibility = View.VISIBLE
            btnModifier.visibility = View.GONE
        }

        btnImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        btnAjouter.setOnClickListener {
            if (titre.text.isEmpty()) { titre.error = "Le titre est obligatoire"; return@setOnClickListener }
            if (auteur.text.isEmpty()) { auteur.error = "L'auteur est obligatoire"; return@setOnClickListener }
            if (page.text.isEmpty()) { page.error = "Nombre de pages obligatoire"; return@setOnClickListener }
            if (desc.text.isEmpty()) { desc.error = "Description obligatoire"; return@setOnClickListener }

            val newId = if (BookService.books.isEmpty()) 1 else BookService.books.maxOf { it.id } + 1
            val photoRes = imgURI?.let { R.drawable.ic_launcher_foreground } ?: R.drawable.ic_launcher_background

            BookService.Ajouter(Book(newId,
                titre.text.toString(),
                auteur.text.toString(),
                page.text.toString().toIntOrNull() ?: 0,
                genre.selectedItem.toString(),
                photoRes,
                desc.text.toString()
            ))

            Toast.makeText(this, "Livre ajouté", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnModifier.setOnClickListener {
            editingBookId?.let { id ->
                val photoRes = imgURI?.let { R.drawable.ic_launcher_foreground } ?: R.drawable.ic_launcher_background
                BookService.Modifier(Book(
                    id,
                    titre.text.toString(),
                    auteur.text.toString(),
                    page.text.toString().toIntOrNull() ?: 0,
                    genre.selectedItem.toString(),
                    photoRes,
                    desc.text.toString()
                ))
                Toast.makeText(this, "Livre modifié", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnRetour.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imgURI = data?.data
            findViewById<ImageView>(R.id.imgId).setImageURI(imgURI)
        }
    }
}
