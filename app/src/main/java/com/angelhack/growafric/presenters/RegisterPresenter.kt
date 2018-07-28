package com.angelhack.growafric.presenters

import com.angelhack.growafric.views.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterPresenter(val registerView: RegisterView) {
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signUp(emailAddress : String, password : String, name : String){
        registerView.onProcessing()
        firebaseAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnSuccessListener {authResult ->
                    val userId = authResult.user.uid
                    val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(name).build()

                    authResult.user.updateProfile(profileUpdate).addOnSuccessListener {
                        registerView.onRegisterSuccess(authResult.user)
                    }.addOnFailureListener {
                        registerView.onRegisterFailed(it)
                    }
                }
                .addOnFailureListener{
                    registerView.onRegisterFailed(it)
                }
    }



}