package com.f4erp.help_desk

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfig() {
    private val firebaseApp: FirebaseApp;

    init {
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://dr-appoitment.firebaseio.com")
                .build()
        firebaseApp = FirebaseApp.initializeApp(options)
    }

    @Bean
    open fun firebase(): FirebaseApp {
        return this.firebaseApp
    }

    @Bean
    open fun firebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance(this.firebaseApp)
    }
}
