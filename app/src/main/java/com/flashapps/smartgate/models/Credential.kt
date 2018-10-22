package com.flashapps.smartgate.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by dietervaesen on 20/04/18.
 */
open class Credential(var username : String =" no username ",var password : String =" no password",var simple_url : String = "No simple url found",var url : String = "No url found", @PrimaryKey var gate_id : String ="No gate id found", var gate_name : String = "No gate name found", var order : Int = 0) : RealmObject() {

}