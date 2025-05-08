package ru.template.telegram.bot.kotlin.template.service

import java.io.ByteArrayInputStream
import java.net.URI
import org.springframework.stereotype.Service


@Service
class FileService {

    fun getFileFromUrl(url: String): ByteArrayInputStream {
        val steam = URI(url).toURL().openStream()
        steam.use {
            return ByteArrayInputStream(it.readBytes())
        }
    }
}