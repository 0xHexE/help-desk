package com.f4erp.help_desk.utils.validators

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken

class Authenticator(private val firebaseAuth: FirebaseAuth) {
    fun checkIsAuthenticate(token: String): FirebaseToken? {
        if (!token.startsWith("Bearer ")) {
            return null
        }
        if (token.split(" ").count() != 2) {
            return null
        }
        return try {
            firebaseAuth.verifyIdToken(token.split(" ")[1])
        } catch (e: FirebaseAuthException) {
            null
        }
    }
}