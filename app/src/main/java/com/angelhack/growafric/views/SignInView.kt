package com.angelhack.growafric.views

import com.google.firebase.auth.FirebaseUser

interface SignInView {
    fun onLoginSuccess(user : FirebaseUser)

    fun onLoginFailed(error : Exception)
}