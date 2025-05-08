package ru.template.telegram.bot.kotlin.template.enums

enum class StepCode(val type: StepType, val botPause: Boolean) {
    START(StepType.SEND_MESSAGE, false),
    USER_INFO(StepType.SEND_MESSAGE, true),
    BUTTON_REQUEST(StepType.SEND_MESSAGE, true),
    BUTTON_RESPONSE(StepType.SEND_MESSAGE, true),
    ACCESS(StepType.SEND_MESSAGE, true),
    PHOTO(StepType.SEND_PHOTO, true),
    PHOTO_BUTTON(StepType.SEND_PHOTO, true),
    CONTACT(StepType.SEND_MESSAGE, true)
}

enum class StepType {
    SEND_MESSAGE,
    SEND_PHOTO
}