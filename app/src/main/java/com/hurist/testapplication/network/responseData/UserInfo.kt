package com.hurist.wanandroid.data.responseData

data class UserInfo(
    val admin: Boolean,
    val chapterTops: Array<Any>,
    val collectIds: Array<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        if (admin != other.admin) return false
        if (!chapterTops.contentEquals(other.chapterTops)) return false
        if (!collectIds.contentEquals(other.collectIds)) return false
        if (email != other.email) return false
        if (icon != other.icon) return false
        if (id != other.id) return false
        if (nickname != other.nickname) return false
        if (password != other.password) return false
        if (publicName != other.publicName) return false
        if (token != other.token) return false
        if (type != other.type) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = admin.hashCode()
        result = 31 * result + chapterTops.contentHashCode()
        result = 31 * result + collectIds.contentHashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + id
        result = 31 * result + nickname.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + publicName.hashCode()
        result = 31 * result + token.hashCode()
        result = 31 * result + type
        result = 31 * result + username.hashCode()
        return result
    }
}