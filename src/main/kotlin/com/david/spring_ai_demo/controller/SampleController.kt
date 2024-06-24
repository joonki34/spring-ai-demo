package com.david.spring_ai_demo.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ai.openai.OpenAiChatModel

@RestController
@RequestMapping("/api")
class ChatController(private val chatModel: OpenAiChatModel) {
    @GetMapping("/generate")
    fun generate(@RequestParam("message") message: String): String {
        return chatModel.call(message)
    }

    @GetMapping("/ping")
    fun ping(): String = "pong"
}