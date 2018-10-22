package com.flashapps.smartgate.extensions


import io.realm.Realm

/**
 * Created by dietervaesen on 7/02/18.
 */


fun Realm.inTransaction(func : (Realm) -> Unit){
    this.executeTransaction{
        func(this)
    }
}






