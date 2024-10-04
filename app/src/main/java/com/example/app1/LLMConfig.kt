package com.example.app1

class LLMConfig {
    private var uid: String = ""
    private var domain: String = ""
    private var auditing: String = ""
    private var chatID: String = ""
    private var temperature: Float = 0.0f
    private var topK: Int = 0
    private var maxToken: Int = 0
    private var url: String = ""

    fun uid(uid: String) = apply { this.uid = uid }
    fun domain(domain: String) = apply { this.domain = domain }
    fun auditing(auditing: String) = apply { this.auditing = auditing }
    fun chatID(chatID: String) = apply { this.chatID = chatID }
    fun temperature(temperature: Float) = apply { this.temperature = temperature }
    fun topK(topK: Int) = apply { this.topK = topK }
    fun maxToken(maxToken: Int) = apply { this.maxToken = maxToken }
    fun url(url: String) = apply { this.url = url }

    override fun toString(): String {
        return "LLMConfig(uid='$uid', domain='$domain', auditing='$auditing', chatID='$chatID', temperature=$temperature, topK=$topK, maxToken=$maxToken, url='$url')"
    }
}
