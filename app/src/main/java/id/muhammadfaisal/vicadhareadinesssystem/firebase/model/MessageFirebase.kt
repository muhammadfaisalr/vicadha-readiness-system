package id.muhammadfaisal.vicadhareadinesssystem.firebase.model

import java.io.Serializable

data class MessageFirebase(
    var title: String = "",
    var message: String = "",
    var sender: String = "",
    var date: Long = 0,
    var messageType: Int = 0,
): Serializable {}