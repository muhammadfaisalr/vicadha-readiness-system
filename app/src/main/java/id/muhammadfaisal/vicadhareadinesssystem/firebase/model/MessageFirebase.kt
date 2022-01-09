package id.muhammadfaisal.vicadhareadinesssystem.firebase.model

data class MessageFirebase(
    var title: String = "",
    var message: String = "",
    var sender: String = "",
    var date: Long = 0,
    var messageType: Int = 0,
) {}