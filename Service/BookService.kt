package com.example.bibliotheque

import Book



object BookService : IDao<Book> {
   var books = ArrayList<Book>()

   override fun Ajouter(b: Book): Boolean {
      return books.add(b)
   }

   override fun Modifier(b: Book): Boolean {
      for (i in books) {
         if (i.id == b.id) {
            i.titre = b.titre
            i.auteur = b.auteur
            i.page = b.page
            i.genre = b.genre
            i.photo = b.photo
            i.description = b.description
            return true
         }
      }
      return false
   }

   override fun supprimer(id: Int): Boolean {
      val iterator = books.iterator()
      while (iterator.hasNext()) {
         val b = iterator.next()
         if (b.id == id) {
            iterator.remove()
            return true
         }
      }
      return false
   }

   override fun afficherAll(): ArrayList<Book> {
      return books
   }

   override fun afficherParId(id: Int): Book? {
      for (b in books) {
         if (b.id == id) return b
      }
      return null
   }

   fun ajouterEvaluation(bookId: Int, note: Float, commentaire: String) {
      val book = afficherParId(bookId)
      book?.evaluations?.add(note to commentaire)
   }


}



