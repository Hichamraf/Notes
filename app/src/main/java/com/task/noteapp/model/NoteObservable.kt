package com.task.noteapp.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class NoteObservable constructor(val note: Note?) : BaseObservable(){

    @get:Bindable
    var title : String =note?.title?:""
     set(value)  {
         field=value
         notifyPropertyChanged(BR.title)
     }

    @get:Bindable
    var description : String =note?.description?:""
        set(value)  {
            field=value
            notifyPropertyChanged(BR.description)
        }

    @get:Bindable
    var imageUrl : String =note?.imageUrl?:""
        set(value)  {
            field=value
            notifyPropertyChanged(BR.imageUrl)
        }
}