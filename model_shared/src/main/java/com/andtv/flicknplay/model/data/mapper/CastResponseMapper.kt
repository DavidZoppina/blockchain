/*
 *
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

package com.andtv.flicknplay.model.data.mapper

import com.andtv.flicknplay.model.data.remote.CastResponse
import com.andtv.flicknplay.model.data.remote.CastResponseWrapper
import com.andtv.flicknplay.model.data.remote.MovieCredits
import com.andtv.flicknplay.model.data.remote.Person
import com.andtv.flicknplay.model.domain.CastDomainModel

fun CastResponse.toDomainModel(source: String) = CastDomainModel(
        castId = castId,
        creditId = creditId,
        gender = gender.toString(),
        id = id,
        order = order,
        name = name,
        character = character,
        profilePath = profilePath,
        birthday = birthday,
        deathDay = deathDay,
        biography = biography,
        source = source,
        popularity = popularity,
        placeOfBirth = placeOfBirth
)

fun MovieCredits.toDomainModel(source: String) = CastDomainModel(
        id = id ?: 0,
        name = name,
        character = pivot?.character,
        source = source
)

fun Person.toDomainModel(source: String) = CastDomainModel(
        gender = gender,
        id = id,
        order = order,
        name = name,
        character = character,
        profilePath = poster,
        birthday = birthDate,
        deathDay = deathDate,
        biography = description,
        source = source,
        popularity = popularity,
        placeOfBirth = birthPlace
)

fun CastResponseWrapper.toDomainModel(source: String) = person?.toDomainModel(source)
