package com.angelhack.growafric

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.angelhack.growafric.models.BusinessModel
import com.angelhack.growafric.presenters.BusinessPresenter
import com.angelhack.growafric.presenters.RegisterPresenter
import com.angelhack.growafric.presenters.SignInPresenter
import com.angelhack.growafric.views.IView
import com.angelhack.growafric.views.RegisterView
import com.angelhack.growafric.views.SignInView
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast

class TestActivity : AppCompatActivity(), IView, SignInView, RegisterView {
    //IView is an interface that your registerBusiness activity must implement
    //SignInView is an interface that your SignIn class must implement - Handle your actions in the callback
    //RegisterView is an interface that your RegisterView must implement also

    private lateinit var firebaseUser: FirebaseUser

    //This is a unique identifier for each user
    private lateinit var userId : String
    override fun onProcessing() {
        toast("Processing")
    }

    override fun onLoginSuccess(user: FirebaseUser) {
        this.firebaseUser = user
    }

    override fun onLoginFailed(error: Exception) {
        toast(error.toString())
    }

    override fun onRegisterSuccess(firebaseUser : FirebaseUser) {
        this.firebaseUser = firebaseUser
    }

    override fun onRegisterFailed(error: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrievalSuccess(businessModels: ArrayList<BusinessModel>) {
        businessModels.forEachIndexed { _, businessModel ->
            Log.d("OkH", businessModel.toString())
        }
    }

    private val progressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onPostSuccessful(model: BusinessModel) {
        toast("Successful")
    }

    override fun onPostError(model: BusinessModel, error: String) {
        toast("Error")
    }

    override fun onProcessing(percentage: Int) {
        toast("Processing")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        progressDialog.setMessage("Loading")


        signIn.setOnClickListener {
            //How you would make your network call
            SignInPresenter(this).signIn("testtest@test.com", "testtest")
        }

        register.setOnClickListener {
            //How you would register a user
            RegisterPresenter(this).signUp("testtest@test.com", "testtest", "Boss Tai")
        }

        registerBusiness.setOnClickListener {
            //You pass in the automatically generated key as id - passing the user id gotten from Firebase
            val businessModel = BusinessModel(id = firebaseUser.uid, name = "Ace Ventures", address = "23, Sinari, VI", revenue_generated = "1400", amountNeeded = "400", founder_name = "Boss Tai", skills = "Skills", previous_business_revenue = "14.5", accountBvn = "1234512345", email = "boss@tai.com", socialMediaLinks = "facebook.com/hello")

            //Make the call
            BusinessPresenter(this).startPostData(businessModel, Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_4444))

        }

        retrieveEntries.setOnClickListener {
            //Retrieve all items in the list
            BusinessPresenter(this).retrieveData()
        }




    }


}
