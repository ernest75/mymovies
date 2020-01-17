package com.example.mymovies.ui.detail

import com.example.mymovies.model.Movie

class DetailPresenter  {

    private  var view: View? = null

    interface View {
        fun updateUi(movie: Movie)
    }

    fun onCreate(view : View, movie: Movie){
        this.view = view
        view.updateUi(movie)
    }

    fun onDestroy(){
        this.view = null

    }


}