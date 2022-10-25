/*
 * Copyright (C) 2021 Flicknplay
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *.
 */
data class Person(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("gender") var gender: String = "",
    @SerializedName("order") var order: Int = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("character") var character: String? = null,
    @SerializedName("poster") var poster: String? = null,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("death_date") var deathDate: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("birth_place") var birthPlace: String? = null
)
