package chap6.comparestyle

import java.time.LocalDate

class Article {
    private val _comments = mutableListOf<Comment>()
    val comments: List<Comment> = _comments

    fun addComment(text: String, author: String, now: LocalDate) {
        _comments.add(Comment(text, author, now))
    }

    fun shouldContainNumberOfComments(i :Int): Article {
        return this
    }

}