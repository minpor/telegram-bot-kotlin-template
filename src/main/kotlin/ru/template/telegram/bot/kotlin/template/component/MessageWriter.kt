package ru.template.telegram.bot.kotlin.template.component

import java.io.StringWriter
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class MessageWriter(
    private val freeMarkerConfigurer: FreeMarkerConfigurer
) {

    fun process(stepCode: StepCode, freemarkerData: Any? = null): String {
        val name = stepCode.name.lowercase()
        return processed(freemarkerData?.let { mapOf("data" to it) }?: emptyMap(), "$name.ftl")
    }

    private fun processed(data: Map<String, Any>, templateName: String): String {
        val template = freeMarkerConfigurer.configuration.getTemplate(templateName)
        val output = StringWriter()
        template.process(data, output)
        return output.toString()
    }

}
