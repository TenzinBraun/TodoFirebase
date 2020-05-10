package fr.iutbourg.todofirebase.data.model

import java.util.HashMap



data class Todo(var key: String?, var name: String?, var check: Boolean?, var createdAt: Long?) {
    constructor(key: String, hashMap: HashMap<String, Any>) : this(key,null, null, null) {
        name = hashMap["name"] as String
        check = hashMap["check"] as Boolean
        createdAt = hashMap["createdAt"] as Long
    }
}
