/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.realestate.RealEstateService
import com.example.android.marsrealestate.network.realestate.PropertyTypeApiFilter
import com.example.android.marsrealestate.network.realestate.model.MarsProperty
import com.example.android.marsrealestate.network.realestate.response.MarsPropertiesResponse
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _marsProperties = MutableLiveData<MarsPropertiesResponse>(MarsPropertiesResponse.Loading)
    val marsProperties: LiveData<MarsPropertiesResponse> by ::_marsProperties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty> by ::_navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(PropertyTypeApiFilter.ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(propertyType: PropertyTypeApiFilter) {
        viewModelScope.launch {
            _marsProperties.value = MarsPropertiesResponse.Loading
            try {
                val properties = RealEstateService.getProperties(propertyType)
                if (properties.isNotEmpty()) {
                    _marsProperties.value = MarsPropertiesResponse.Success(properties)
                }
            } catch (e: Exception) {
                _marsProperties.value = MarsPropertiesResponse.Error(e)
            }
        }
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(propertyType: PropertyTypeApiFilter) {
        getMarsRealEstateProperties(propertyType)
    }
}
