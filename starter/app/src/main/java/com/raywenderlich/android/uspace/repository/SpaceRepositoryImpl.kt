/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.uspace.repository

import com.raywenderlich.android.uspace.network.services.SpaceService
import com.raywenderlich.android.uspace.ui.models.toCapsule
import com.raywenderlich.android.uspace.ui.models.toCrew
import com.raywenderlich.android.uspace.ui.models.toDragon
import com.raywenderlich.android.uspace.ui.models.toRocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpaceRepositoryImpl(
  private val service: SpaceService
) : SpaceRepository {

  override suspend fun getDragons(): Flow<SpaceResult> = flow {
    try {
      val response = service.getDragons()

      if (response.isSuccessful) {
        emit(SpaceResult.DragonResult(response.body()?.map { it.toDragon() } ?: emptyList()))
      } else {
        emit(SpaceResult.Error)
      }
    } catch(exception: Exception) {
      emit(SpaceResult.Error)
    }
  }

  override suspend fun getRockets(): Flow<SpaceResult> = flow {
    try {
      val response = service.getRockets()

      if (response.isSuccessful) {
        emit(SpaceResult.RocketResult(response.body()?.map { it.toRocket() } ?: emptyList()))
      } else {
        emit(SpaceResult.Error)
      }
    } catch(exception: Exception) {
      emit(SpaceResult.Error)
    }
  }

  override suspend fun getCrews(): Flow<SpaceResult> = flow {
    try {
      val response = service.getCrews()

      if (response.isSuccessful) {
        emit(SpaceResult.CrewResult(response.body()?.map { it.toCrew() } ?: emptyList()))
      } else {
        emit(SpaceResult.Error)
      }
    } catch(exception: Exception) {
      emit(SpaceResult.Error)
    }
  }

  override suspend fun getCapsules(): Flow<SpaceResult> = flow {
    try {
      val response = service.getCapsules()

      if (response.isSuccessful) {
        emit(SpaceResult.CapsuleResult(response.body()?.map { it.toCapsule() } ?: emptyList()))
      } else {
        emit(SpaceResult.Error)
      }
    } catch(exception: Exception) {
      emit(SpaceResult.Error)
    }
  }
}
