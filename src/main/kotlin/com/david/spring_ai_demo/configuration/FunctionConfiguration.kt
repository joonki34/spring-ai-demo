package com.david.spring_ai_demo.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import java.util.function.Function

private val log = KotlinLogging.logger {}

@Configuration
class FunctionConfiguration {
    @Bean
    fun objectMapper() = jacksonObjectMapper().apply {
        registerModule(Jdk8Module())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    }

    @Bean
    @Description("Add two integers")
    fun addTwoIntegers(): Function<AddTwoIntegersRequest, AddTwoIntegersResponse> {
        return Function<AddTwoIntegersRequest, AddTwoIntegersResponse> {
            AddTwoIntegersResponse(it.a + it.b)
        }
    }
}

data class AddTwoIntegersRequest(val a: Int, val b: Int) {
    // FIXME: somehow this is not working. Maybe a bug in spring-ai regarding jackson-module-kotlin?
    constructor() : this(0, 0)
}

data class AddTwoIntegersResponse(val result: Int)