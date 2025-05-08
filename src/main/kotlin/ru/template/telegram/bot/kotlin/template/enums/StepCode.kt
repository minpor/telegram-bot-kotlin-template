package ru.template.telegram.bot.kotlin.template.enums

enum class StepCode(val type: StepType, val botPause: Boolean) {
    START(StepType.SIMPLE_TEXT, false),
    USER_INFO(StepType.SIMPLE_TEXT, true),
    BUTTON_REQUEST(StepType.INLINE_KEYBOARD_MARKUP, true),
    BUTTON_RESPONSE(StepType.SIMPLE_TEXT, true),
    ACCESS(StepType.SIMPLE_TEXT, true),
    PHOTO(StepType.PHOTO_TEXT, true),
    PHOTO_BUTTON(StepType.PHOTO_TEXT, true)
}

enum class StepType {
    SIMPLE_TEXT,
    PHOTO_TEXT,
    INLINE_KEYBOARD_MARKUP
}