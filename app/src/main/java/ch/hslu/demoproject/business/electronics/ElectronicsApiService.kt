package ch.hslu.demoproject.business.electronics

import ch.hslu.demoproject.business.electronics.data.Electronic
import retrofit2.Response
import retrofit2.http.GET

interface ElectronicsApiService {
    @GET("objects")
    suspend fun getElectronics(): Response<List<Electronic>>
}