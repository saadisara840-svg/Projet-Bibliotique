package com.example.bibliotheque

interface IDao<T> {
    fun Ajouter(b: T): Boolean
    fun Modifier(b: T): Boolean
    fun supprimer(id: Int): Boolean
    fun afficherAll(): ArrayList<T>
    fun afficherParId(id: Int): T?
}