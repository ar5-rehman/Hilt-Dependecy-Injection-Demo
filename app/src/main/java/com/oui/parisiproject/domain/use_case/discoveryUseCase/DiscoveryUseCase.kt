package com.oui.parisiproject.domain.use_case.discoveryUseCase

import com.oui.parisiproject.common.Resource
import com.oui.parisiproject.data.model.discoveryModel.DiscoveryDTO
import com.oui.parisiproject.domain.repository.RepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiscoveryUseCase @Inject constructor(private val repository: RepositoryInterface) {

    operator fun invoke(position: String): Flow<Resource<DiscoveryDTO>> =
        flow {
            try {

                emit(Resource.Loading())

                val data = repository.discovery(position)

                if(data!=null){
                    emit(Resource.Success(data))
                }

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity") )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity") )
            }
        }

}