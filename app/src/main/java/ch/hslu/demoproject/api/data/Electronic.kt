package ch.hslu.demoproject.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Electronic(
    val id: String,
    val name: String,
    val data: ElectronicData?
)
