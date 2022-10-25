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

package com.andtv.flicknplay.workbrowse.presentation.ui.headeritem

import androidx.leanback.widget.HeaderItem
import com.andtv.flicknplay.workbrowse.presentation.model.TopWorkTypeViewModel

/**
 * Copyright (C) Flicknplay 11-02-2018.
 */
class WorkTypeHeaderItem(
    id: Long,
    name: String,
    val topWorkTypeViewModel: TopWorkTypeViewModel
) : HeaderItem(id, name)
