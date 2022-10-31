package chap6.comparestyle

import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


fun Article.shouldContainNumberOfComments(
    article: Article, commentCount: Int,
): Article {
    assertEquals(1, article.comments.count())
    return article
}

fun Article.withComment(
    article: Article,
    text: String,
    author: String,
    dateCreated: LocalDate,
): Article {
    val comment: Comment = article.comments.single { x ->
        x.text == text && x.author == author && x.dateCreated == dateCreated
    }
    assertNotNull(comment)
    return article
}