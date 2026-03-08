package com.mmulalic.languagelearner.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SerializableCookie(
    val name: String,
    val value: String,
    val expiresAt: Long,
    val domain: String,
    val path: String,
    val secure: Boolean,
    val httpOnly: Boolean
)