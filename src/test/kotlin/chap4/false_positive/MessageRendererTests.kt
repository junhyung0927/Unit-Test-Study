package chap4.false_positive

import java.io.File
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageRendererTests {

    @Test
    @DisplayName("MessageRenderer 클래스의 하위 렌더링 클래스의 개수와 올바른 타입과 순서를 가지고 있는지 검증")
    fun messageRenderer_uses_correct_sub_renderers() {
        val messageRenderer = MessageRenderer()

        val renderers: List<IRenderer> = messageRenderer.subRenderers

        assertEquals(3, renderers.count())
        assert(renderers[0] is HeaderRenderer)
        assert(renderers[1] is BodyRenderer)
        assert(renderers[2] is FooterRenderer)
    }

    @Test
    @DisplayName("MessageRenderer 클래스의 소스 코드 검증")
    fun messageRenderer_is_implemented_correctly() {
        val sourceCode =
            File("/Users/junhyung/Unit-Test-Study/src/main/kotlin/chap4/false_positive/MessageRenderer.kt")
                .useLines { it.toMutableList() }
                .drop(2)
                .joinToString("\n")

        assertEquals(
            "class MessageRenderer : IRenderer {\n" +
                    "    val subRenderers: List<IRenderer> = listOf(\n" +
                    "        HeaderRenderer(),\n" +
                    "        BodyRenderer(),\n" +
                    "        FooterRenderer()\n" +
                    "    )\n" +
                    "\n" +
                    "    override fun render(message: Message): String {\n" +
                    "        return subRenderers\n" +
                    "            .map { x ->\n" +
                    "                x.render(message)\n" +
                    "            }.fold(\"\") { str1, str2 ->\n" +
                    "                str1 + str2\n" +
                    "            }\n" +
                    "    }\n" +
                    "}",
            sourceCode
        )
    }

    @Test
    @DisplayName("클라이언트와 결합도를 낮춘 버전에서의 올바른 타입과 순서를 가지고 있는지에 대한 검증")
    fun rendering_a_message() {
        val messageRenderer = MessageRenderer()
        val message = Message(
            header = "h",
            body = "b",
            footer = "f"
        )

        val html = messageRenderer.render(message)

        assertEquals("<h1>h</h1><b>b</b><i>f</i>", html)
    }
}