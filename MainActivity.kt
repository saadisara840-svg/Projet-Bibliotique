package com.example.bibliotheque

import Book
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var searchView: SearchView
    private lateinit var btnAjouter: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.recherche)
        btnAjouter = findViewById(R.id.btnAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)

        if (BookService.afficherAll().isEmpty()) {
            BookService.Ajouter(
                Book(
                    id = 0,
                    titre = "Harry Potter",
                    auteur = "J.K Rowling",
                    page = 300,
                    genre = "Fantastique",
                    photo = R.drawable.book1,
                    description = "Un roman magique"
                )
            )
        }

        adapter = BookAdapter(this, ArrayList(BookService.afficherAll()))
        recyclerView.adapter = adapter

        btnAjouter.setOnClickListener {
            val intent = Intent(this, ActivityFormulaire::class.java)
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterBook(newText ?: "")
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()

        adapter.updateList(ArrayList(BookService.afficherAll()))
    }
}
