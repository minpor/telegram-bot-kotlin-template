package ru.template.telegram.bot.kotlin.template.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan("ru.template.telegram.bot.kotlin.template.properties")
class PropertyConfig {
}
