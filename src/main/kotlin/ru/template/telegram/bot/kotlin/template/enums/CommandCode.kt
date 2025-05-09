package ru.template.telegram.bot.kotlin.template.enums

enum class CommandCode(val command: String, val desc: String) {
    START("start", "Start work"),
    USER_INFO("user_info", "user info"),
    BUTTON("button", "button yes no"),
    ACCESS("access", "access check"),
    CONTACT("contact", "contact check"),
    PHOTO("photo", "photo test"),
    PHOTO_BUTTON("photo_button", "photo test with button")
}
