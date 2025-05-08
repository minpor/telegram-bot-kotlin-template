package ru.template.telegram.bot.kotlin.template.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.enums.StepType.INLINE_KEYBOARD_MARKUP
import ru.template.telegram.bot.kotlin.template.enums.StepType.PHOTO_TEXT
import ru.template.telegram.bot.kotlin.template.enums.StepType.SIMPLE_TEXT
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.strategy.MessageContext
import ru.template.telegram.bot.kotlin.template.strategy.StepContext
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

@Service
class MessageService(
    private val telegramClient: TelegramClient,
    private val messageContext: MessageContext<DataModel>,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val stepContext: StepContext
) {

    fun sendMessageToBot(
        chatId: Long,
        stepCode: StepCode
    ) {
        when (stepCode.type) {
            SIMPLE_TEXT -> telegramClient.execute(simpleTextMessage(chatId, stepCode))
            INLINE_KEYBOARD_MARKUP -> telegramClient.execute(inlineKeyboardMarkup(chatId, stepCode))
            PHOTO_TEXT -> telegramClient.execute(photo(chatId, stepCode))
        }

        if (!stepCode.botPause) {
            applicationEventPublisher.publishEvent(
                TelegramStepMessageEvent(
                    chatId = chatId,
                    stepCode = stepContext.next(chatId, stepCode)!!
                )
            )
        }
    }

    private fun photo(chatId: Long, stepCode: StepCode): SendPhoto {
        val photoMessage =
            messageContext.getPhotoMessage(chatId, stepCode) ?: throw IllegalArgumentException("photo data is empty")

        return SendPhoto.builder()
                .chatId(chatId)
                .caption(photoMessage.message)
                .photo(InputFile(photoMessage.file, "file"))
                .replyMarkup(photoMessage.inlineButtons.getInlineKeyboardMarkup())
                .build()
    }

    private fun simpleTextMessage(chatId: Long, stepCode: StepCode): SendMessage {
        val sendMessage = SendMessage(chatId.toString(), messageContext.getMessage(chatId, stepCode))
        sendMessage.enableHtml(true)

        val replyKeyboardRemove = ReplyKeyboardRemove(true)

        sendMessage.replyMarkup = replyKeyboardRemove
        return sendMessage
    }

    private fun inlineKeyboardMarkup(chatId: Long, stepCode: StepCode): SendMessage {
        val inlineKeyboardMarkupDto =
            messageContext.getInlineKeyboardMarkupDto(chatId, stepCode)
                ?: return simpleTextMessage(chatId, stepCode)

        val messageText = inlineKeyboardMarkupDto.message
        val inlineKeyboardMarkup = inlineKeyboardMarkupDto.inlineButtons.getInlineKeyboardMarkup()

        return sendMessageWithMarkup(chatId, messageText, inlineKeyboardMarkup)
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

        var inlineKeyboardButtonsInner: MutableList<InlineKeyboardButton>
        val inlineKeyboardButtons: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()

        this.groupBy { it.rowPos }.toSortedMap().forEach { entry: Map.Entry<Int, List<MarkupDataDto>> ->
            inlineKeyboardButtonsInner = mutableListOf()
            entry.value.forEach { markup: MarkupDataDto ->
                val button = InlineKeyboardButton(markup.text)
                button.callbackData = markup.text
                inlineKeyboardButtonsInner.add(button)
            }
            inlineKeyboardButtons.add(inlineKeyboardButtonsInner.toMutableList())
        }
        val rows = inlineKeyboardButtons.map { InlineKeyboardRow(it) }
        return InlineKeyboardMarkup(rows)
    }
}