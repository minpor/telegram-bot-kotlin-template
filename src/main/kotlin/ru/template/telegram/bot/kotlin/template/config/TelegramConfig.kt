package ru.template.telegram.bot.kotlin.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.properties.BotProperty
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.message.Button
import ru.template.telegram.bot.kotlin.template.strategy.message.Message

@Configuration
class TelegramConfig<T : DataModel>(
    private val botProperty: BotProperty,
    private val message: List<Message<T>>,
    private val button: List<Button<T>>
) {

    @Bean
    fun telegramClient(): TelegramClient {
        return OkHttpTelegramClient(botProperty.token)
    }

    @Bean("message")
    fun telegramMessage(): Map<StepCode, Message<T>> {
        val associate = message.associateBy { it.classStepCode() }
        return associate
    }

    @Bean("button")
    fun telegramButton(): Map<StepCode, Button<T>> {
        val associate = button.associateBy { it.classStepCode() }
        return associate
    }

}