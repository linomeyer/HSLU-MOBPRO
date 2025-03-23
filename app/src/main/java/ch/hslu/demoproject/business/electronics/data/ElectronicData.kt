package ch.hslu.demoproject.business.electronics.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ElectronicData(
    val year: Int? = null,
    val price: Double? = null,
    @SerialName("CPU model")
    val cpuModel: String? = null,
    val capacity: String? = null,
    val color: String? = null,
    val generation: String? = null,
    @SerialName("capacity GB")
    val capacityGB: Int? = null,
    @SerialName("Hard disk size")
    val hardDiskSize: String? = null,
    @SerialName("Strap Colour")
    val strapColor: String? = null,
    @SerialName("Case Size")
    val caseSize: String? = null,
    @SerialName("Description")
    val description: String? = null,
    @SerialName("Screen size")
    val screenSize: Double? = null,
    @SerialName("Color")
    val capColor: String? = null,
    @SerialName("Generation")
    val capGeneration: String? = null,
    @SerialName("Price")
    val capPrice: String? = null,
    @SerialName("Capacity")
    val capCapacity: String? = null
)
