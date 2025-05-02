package ru.template.telegram.bot.kotlin.template.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.enums.StepType.*
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.strategy.MarkupContext
import ru.template.telegram.bot.kotlin.template.strategy.MessageContext
import ru.template.telegram.bot.kotlin.template.strategy.NextStepContext

@Service
class MessageService(
    private val telegramClient: TelegramClient,
    private val messageContext: MessageContext,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val markupContext: MarkupContext<DataModel>,
    private val nextStepContext: NextStepContext
) {

    fun sendMessageToBot(
        chatId: Long,
        stepCode: StepCode
    ) {
        when (stepCode.type) {
            SIMPLE_TEXT -> telegramClient.execute(simpleTextMessage(chatId, stepCode))
            INLINE_KEYBOARD_MARKUP -> telegramClient.sendInlineKeyboardMarkup(chatId, stepCode)
        }

        if (!stepCode.botPause) {
            applicationEventPublisher.publishEvent(
                TelegramStepMessageEvent(
                    chatId = chatId,
                    stepCode = nextStepContext.next(chatId, stepCode)!!
                )
            )
        }
    }


    private fun simpleTextMessage(chatId: Long, stepCode: StepCode): SendMessage {
        val sendMessage = SendMessage(chatId.toString(),  messageContext.getMessage(chatId, stepCode))
        sendMessage.enableHtml(true)

        val replyKeyboardRemove = ReplyKeyboardRemove(true)

        sendMessage.replyMarkup = replyKeyboardRemove
        return sendMessage
    }

    private fun TelegramClient.sendInlineKeyboardMarkup(chatId: Long, stepCode: StepCode) {
        val inlineKeyboardMarkup: InlineKeyboardMarkup
        val messageText: String

        val inlineKeyboardMarkupDto = markupContext.getInlineKeyboardMarkupDto(chatId, stepCode)!!
        messageText = inlineKeyboardMarkupDto.message
        inlineKeyboardMarkup = inlineKeyboardMarkupDto.inlineButtons.getInlineKeyboardMarkup()

        this.execute(sendMessageWithMarkup(chatId, messageText, inlineKeyboardMarkup))
    }

    private fun sendMessageWithMarkup(
        chatId: Long, messageText: String, inlineKeyboardMarkup: InlineKeyboardMarkup
    ): SendMessage {
        val sendMessage = SendMessage(chatId.toString(), messageText)

        sendMessage.replyMarkup = inlineKeyboardMarkup
        sendMessage.parseMode = ParseMode.HTML
        return sendMessage
    }

    private fun List<MarkupDataDto>.getInlineKeyboardMarkup(): InlineKeyboardMarkup {

        val inlineKeyboardButtonsInner: MutableList<InlineKeyboardButton> = mutableListOf()
        val inlineKeyboardButtons: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        this.sortedBy { it.rowPos }.forEach { markupDataDto ->
            val button = InlineKeyboardButton(markupDataDto.text)
            button.callbackData = markupDataDto.text
            inlineKeyboardButtonsInner.add(button)
        }
        inlineKeyboardButtons.add(inlineKeyboardButtonsInner)
        val rows = inlineKeyboardButtons.map { InlineKeyboardRow(it) }
        return InlineKeyboardMarkup(rows)
    }
}