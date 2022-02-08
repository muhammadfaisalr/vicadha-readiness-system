package id.muhammadfaisal.vicadhareadinesssystem.firebase.model

import java.io.Serializable

data class DocumentFirebase(
   var url: String = "",
   var title: String = "",
   var sender: String = "",
) : Serializable