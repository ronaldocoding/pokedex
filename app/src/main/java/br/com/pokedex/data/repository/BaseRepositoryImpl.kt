package br.com.pokedex.data.repository

import br.com.pokedex.data.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepositoryImpl {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiToBeCalled()

                if(response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse = JSONObject(response.errorBody().toString()).toString()
                    Resource.Error(errorMessage = errorResponse)
                }
            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message() ?: "Something went wrong")
            } catch (e: IOException) {
                Resource.Error(errorMessage = "Network connection error")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }
}