package ch.hslu.demoproject.api.electronics

import ch.hslu.demoproject.api.electronics.data.Electronic
import retrofit2.Response
import retrofit2.http.GET

interface ElectronicsService {
    @GET("objects")
    suspend fun getElectronics(): Response<List<Electronic>>
}