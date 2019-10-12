package com.f4erp.help_desk.restend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appointment")
class Appointments {
    @GetMapping
    fun ok(): String {
        return "Omkar"
    }
}
