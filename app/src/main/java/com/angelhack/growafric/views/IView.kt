package com.angelhack.growafric.views

import com.angelhack.growafric.models.BusinessModel

interface IView {
    fun onPostSuccessful(model : BusinessModel)
    fun onPostError(model : BusinessModel, error : String)

    fun onProcessing(percentage : Int)

    fun onRetrievalSuccess(businessModels : ArrayList<BusinessModel>)
}