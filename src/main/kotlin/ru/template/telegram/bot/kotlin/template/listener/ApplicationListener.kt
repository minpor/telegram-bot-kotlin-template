package ru.template.telegram.bot.kotlin.template.listener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedCallbackEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedMessageEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository
import ru.template.telegram.bot.kotlin.template.service.MessageService
import ru.template.telegram.bot.kotlin.template.strategy.LogicContext
import ru.template.telegram.bot.kotlin.template.strategy.StepContext

@Component
class ApplicationListener(
    private val logicContext: LogicContext,
    private val stepContext: StepContext,
    private val usersRepository: UsersRepository,
    private val messageService: MessageService
) {

    inner class Message {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedMessageEvent) {
            logicContext.execute(chatId = event.chatId, message = event.message, stepCode = event.stepCode)
            val nextStepCode = stepContext.next(event.chatId, event.stepCode)
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    inner class StepMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramStepMessageEvent) {
            usersRepository.updateUserStep(event.chatId, event.stepCode)
            messageService.sendMessageToBot(event.chatId, event.stepCode)
        }
    }

    inner class CallbackMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedCallbackEvent) {
            val nextStepCode = when (logicContext.execute(event.chatId, event.callback, event.stepCode)) {
                ExecuteStatus.FINAL -> {
                    stepContext.next(event.chatId, event.stepCode)
                }
                ExecuteStatus.NOTHING -> throw IllegalStateException("Не поддерживается")
            }
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    @Bean
    @Lazy
    fun messageBean(): Message = Message()

    @Bean
    @Lazy
    fun stepMessageBean(): StepMessage = StepMessage()

    @Bean
    @Lazy
    fun callbackMessageBean(): CallbackMessage = CallbackMessage()

}