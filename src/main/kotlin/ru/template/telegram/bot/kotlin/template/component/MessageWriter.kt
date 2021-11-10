package ru.template.telegram.bot.kotlin.template.component

import java.io.InputStreamReader
import java.io.StringWriter
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class MessageWriter(
    private val freeMarkerConfigurer: FreeMarkerConfigurer,
    private val resources: ResourceLoader
) {

    fun process(stepCode: StepCode, freemarkerData: Any? = null): String {
        val name = stepCode.name.lowercase().replace("_", "-")
        return when (freemarkerData) {
            null -> getMessage("$name.html")
            else -> processed(mapOf("data" to freemarkerData), "$name.ftl")
        }
    }

    private fun processed(data: Map<String, Any>, templateName: String): String {
        val template = freeMarkerConfigurer.configuration.getTemplate(templateName)
        val output = StringWriter()
        template.process(data, output)
        return output.toString()
    }

    private fun getMessage(htmlFileName: String): String {
        val resource = resources.getResource("classpath:message/simple/$htmlFileName")
        return InputStreamReader(resource.inputStream).use { it.readText() }
    }
}
