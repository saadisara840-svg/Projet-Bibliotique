package com.example.bibliotheque

import Book
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private val context: Context,
    private var books: ArrayList<Book>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var fullList: ArrayList<Book> = ArrayList(books)

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageId)
        val titre: TextView = view.findViewById(R.id.titre)
        val auteur: TextView = view.findViewById(R.id.auteur)
        val page: TextView = view.findViewById(R.id.page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titre.text = book.titre
        holder.auteur.text = book.auteur
        holder.page.text = "${book.page}p"
        holder.image.setImageResource(book.photo)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ActivityDetails::class.java)
            intent.putExtra("bookId", book.id)
            context.startActivity(intent)
        }
    }
    fun filterBook(query: String) {
        val lowerQuery = query.lowercase()
        books = if (lowerQuery.isEmpty()) {
            ArrayList(fullList)
        } else {
            ArrayList(fullList.filter { it.titre.lowercase().contains(lowerQuery) })
        }
        notifyDataSetChanged()
    }

    fun updateList(newBooks: ArrayList<Book>) {
        books = newBooks
        fullList = ArrayList(newBooks)
        notifyDataSetChanged()
    }
}
