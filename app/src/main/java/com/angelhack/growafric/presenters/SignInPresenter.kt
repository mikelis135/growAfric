package com.angelhack.growafric.presenters

import com.angelhack.growafric.views.SignInView
import com.google.firebase.auth.FirebaseAuth

class SignInPresenter(val signInView: SignInView) {

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    fun signIn(emailAddress: String, password : String){
        firebaseAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnSuccessListener {
                    signInView.onLoginSuccess(it.user)
                }
                .addOnFailureListener {
                    signInView.onLoginFailed(it)
                }
    }

}