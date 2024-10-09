package edu.uvg.uvgchatejemplo.data

data class Message(
    val senderFirstName: String = "",
    val senderId:String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false

)
