package ru.template.telegram.bot.kotlin.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer


@Configuration
class FreemarkerConfig {

    @Bean
    fun freeMarkerConfigurer(): FreeMarkerConfigurer {
        val freeMarkerConfigurer = FreeMarkerConfigurer()
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/message/template")
        freeMarkerConfigurer.setDefaultEncoding("UTF-8")
        return freeMarkerConfigurer
    }

}
