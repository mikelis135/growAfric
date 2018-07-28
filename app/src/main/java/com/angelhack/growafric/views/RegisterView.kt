package com.angelhack.growafric.views

import com.google.firebase.auth.FirebaseUser

interface RegisterView {
    fun onProcessing()
    fun onRegisterSuccess(firebaseUser: FirebaseUser)

    fun onRegisterFailed(error : Exception)
}