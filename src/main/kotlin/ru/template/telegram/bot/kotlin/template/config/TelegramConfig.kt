package ru.template.telegram.bot.kotlin.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.properties.BotProperty
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.logic.CallbackChooser
import ru.template.telegram.bot.kotlin.template.strategy.logic.MessageChooser
import ru.template.telegram.bot.kotlin.template.strategy.message.Message
import ru.template.telegram.bot.kotlin.template.strategy.message.Photo

@Configuration
class TelegramConfig<T : DataModel>(
    private val botProperty: BotProperty,
    private val messages: List<Message<T>>,
    private val photo: List<Photo<T>>,
    private val callbackChooser: List<CallbackChooser>,
    private val messageChooser: List<MessageChooser>
) {

    @Bean
    fun telegramClient(): TelegramClient {
        return OkHttpTelegramClient(botProperty.token)
    }

    @Bean
    fun telegramMessage(): Map<StepCode, Message<T>> {
        return messages.associateBy { it.classStepCode() }
    }

    @Bean
    fun telegramPhoto(): Map<StepCode, Photo<T>> {
        return photo.associateBy { it.classStepCode() }
    }

    @Bean
    fun telegramMessageChooser(): Map<StepCode, MessageChooser> {
        return messageChooser.associateBy { it.classStepCode() }
    }

    @Bean
    fun telegramCallbackChooser(): Map<StepCode, CallbackChooser> {
        return callbackChooser.associateBy { it.classStepCode() }
    }

}