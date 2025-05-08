package ru.template.telegram.bot.kotlin.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.properties.BotProperty
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.message.Message
import ru.template.telegram.bot.kotlin.template.strategy.message.Photo

@Configuration
class TelegramConfig<T : DataModel>(
    private val botProperty: BotProperty,
    private val messages: List<Message<T>>,
    private val photo: List<Photo<T>>,
) {

    @Bean
    fun telegramClient(): TelegramClient {
        return OkHttpTelegramClient(botProperty.token)
    }

    @Bean
    fun telegramMessage(): Map<StepCode, Message<T>> {
        val associate = messages.associateBy { it.classStepCode() }
        return associate
    }

    @Bean
    fun telegramPhoto(): Map<StepCode, Photo<T>> {
        val associate = photo.associateBy { it.classStepCode() }
        return associate
    }

}