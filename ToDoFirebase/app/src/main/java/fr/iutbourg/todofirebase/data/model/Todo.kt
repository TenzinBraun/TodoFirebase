package fr.iutbourg.todofirebase.data.model

import java.util.HashMap



data class Todo(var key: String?, var name: String?, var check: Boolean?) {
    constructor(key: String, hashMap: HashMap<String, Any>) : this(key,null, null) {
        name = hashMap["name"] as String
        check = hashMap["check"] as Boolean
    }
}
