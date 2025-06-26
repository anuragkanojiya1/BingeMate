package com.example.mindsdbkb.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindsdbkb.api.ApiInstance
import com.example.mindsdbkb.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BingeViewModel : ViewModel() {

    private val _bingeList = MutableStateFlow<List<Result>>(emptyList())
    val bingeList: MutableStateFlow<List<Result>> = _bingeList

    private val _agentResponse = MutableStateFlow<String>("")
    val agentResponse: MutableStateFlow<String> = _agentResponse

    fun getList(query: String, min_relevance: Float, metaData: Map<String, String>) {
        viewModelScope.launch {
            try {
                val safeMeta = metaData.ifEmpty { emptyMap() }

                Log.d("BingeViewModel", "Query: $query, Min Relevance: $min_relevance, MetaData: ${safeMeta.forEach { k, v -> "$k: $v" }}")
                val response = ApiInstance.api.apiCall(
                    request = query,
                    minRelevance = min_relevance,
                    metadata = safeMeta
                )
                _bingeList.value = response.results
                Log.d("BingeViewModel", "Fetched: ${response.results}")
            } catch (e: Exception) {
                getList(query, min_relevance, metaData)
                Log.e("BingeViewModel", "Error: ${e.message}", e)
            }
        }
    }

    fun getAgentResponse(query: String){
        viewModelScope.launch {
            try {
                val response = ApiInstance.api.agentCall(query)

                _agentResponse.value = response.result.answer.`0`
            } catch (e: Exception){
                getAgentResponse(query)
                Log.e("BingeViewModel", "Error: ${e.message}", e)
            }
        }
    }
}
