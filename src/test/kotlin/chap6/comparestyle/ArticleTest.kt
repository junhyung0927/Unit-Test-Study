package chap6.comparestyle

import java.time.LocalDate
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArticleTest {

    @Test
    fun adding_a_comment_to_an_article() {

        val sut = Article()
        val text = "Comment Text"
        val author = "John Doe"
        val now = LocalDate.of(2022, 10, 31)

        sut.addComment(text, author, now)

        assertEquals(1, sut.comments.count())
        assertEquals(text, sut.comments[0].text)
        assertEquals(author, sut.comments[0].author)
        assertEquals(now, sut.comments[0].dateCreated)

    }

    @Test
    fun adding_a_comment_to_an_article2() {

        val sut = Article()
        val text = "Comment Text"
        val author = "John Doe"
        val now = LocalDate.of(2022, 10 ,31)

        sut.addComment(text, author, now)

        sut.shouldContainNumberOfComments(sut, 1)
            .withComment(sut, text, author, now)

    }

    @Test
    fun adding_a_comment_to_an_article3() {

        val sut = Article()
        val comment = Comment(
            text = "Comment Text",
            author = "John Doe",
            dateCreated = LocalDate.of(2022, 10 ,31)
        )

        sut.addComment(comment.text, comment.author, comment.dateCreated)

        assertThat(comment, allOf(`is`(sut.comments[0])))

    }
}