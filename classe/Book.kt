data class Book(
    var id: Int,
    var titre: String,
    var auteur: String,
    var page: Int,
    var genre: String,
    var photo: Int,
    var description: String,
    var evaluations: MutableList<Pair<Float, String>> = mutableListOf()
)


