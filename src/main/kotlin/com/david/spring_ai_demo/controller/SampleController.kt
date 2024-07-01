package com.david.spring_ai_demo.controller

import com.david.spring_ai_demo.configuration.AddTwoIntegersRequest
import com.david.spring_ai_demo.configuration.AddTwoIntegersResponse
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ChatController(private val chatModel: OpenAiChatModel, private val chatClientBuilder: ChatClient.Builder) {
    private val chatClient = chatClientBuilder
        .defaultAdvisors(SimpleLoggerAdvisor())
        .build()

    @PostMapping("/generate")
    fun generate(@RequestBody request: ChatRequest): String {
        return chatModel.call(request.input)
    }

    @GetMapping("/generate2")
    fun generate2(): String {
        val input = "What is 2 + 2?"

        val response: ChatResponse = chatClient.prompt().user(input).call().chatResponse()
        return response.result.output.content
    }

    @GetMapping("/function-calling")
    fun functionCalling(): String {
        val input = "What is 2 + 2?"

        return chatClient.prompt()
            .user(input)
            .options(OpenAiChatOptions.builder().withFunction("addTwoIntegers").build())
            .call()
            .content()
    }

    @GetMapping("/ping")
    fun ping(): String = "pong"

    @PostMapping("/add")
    fun add(@RequestBody request: AddTwoIntegersRequest): AddTwoIntegersResponse {
        return AddTwoIntegersResponse(request.a + request.b)
    }
}

data class ChatRequest(val input: String)