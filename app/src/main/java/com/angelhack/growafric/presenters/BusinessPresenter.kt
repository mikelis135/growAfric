package com.angelhack.growafric.presenters

import android.graphics.Bitmap
import android.util.Log
import com.angelhack.growafric.views.IView
import com.angelhack.growafric.models.BusinessModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class BusinessPresenter(private val view : IView) {

    private val firebaseDatabase by lazy {
        FirebaseDatabase.getInstance().reference
    }

    private val firebaseStorage by lazy {
        FirebaseStorage.getInstance().reference
    }


    fun signUp(name : String, emailAddress : String, password : String){

    }

    fun signIn(emailAddress : String, password : String){

    }

    fun startPostData(businessModel : BusinessModel, bitmap : Bitmap){
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = firebaseStorage.child(businessModel.id).putBytes(data)

        uploadTask.addOnProgressListener {
            val percentage = (it.bytesTransferred / it.totalByteCount) * 100
            view.onProcessing(percentage = percentage.toInt())

        }.addOnCompleteListener {
            if (it.isSuccessful){
                businessModel.image_url = it.result.downloadUrl.toString()
                firebaseDatabase.child(businessModel.id).setValue(businessModel){
                    error, reference ->
                    error?.let {
                        view.onPostError(businessModel, it.message)
                    }

                    reference?.let {
                        view.onPostSuccessful(businessModel)
                    }
                }
            } else {
                view.onPostError(businessModel, "Can not be processed currently")
            }
        }

    }

    fun retrieveData(){
        val businessModelArray = ArrayList<BusinessModel>()

        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Log.d("OkH", "Error in retrieval ${p0.toString()}")
            }

            override fun onDataChange(p0: DataSnapshot?) {
                p0?.let {
                    for (i in it.children){
                        val businessModel = i.getValue(BusinessModel::class.java)

                        businessModel?.let {
                            businessModelArray.add(it)
                        }
                    }

                    view.onRetrievalSuccess(businessModelArray)
                }
            }
        })

        /*firebaseDatabase.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                data?.let {
                    val businessModel = it.getValue(BusinessModel::class.java)
                    businessModel?.let {
                        businessModelArray.add(businessModel)
                        view.onRetrievalSuccess(businessModelArray)
                    } ?: kotlin.run {
                        //todo some error
                    }
                }

            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

        })*/
    }

    fun retrieveSpecificData(){

    }
}