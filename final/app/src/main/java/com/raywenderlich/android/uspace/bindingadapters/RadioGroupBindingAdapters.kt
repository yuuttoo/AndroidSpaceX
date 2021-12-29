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

package com.raywenderlich.android.uspace.bindingadapters

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.uspace.R
import com.raywenderlich.android.uspace.ui.models.CrewAgency

@BindingAdapter("crewFilter")
fun RadioGroup.setCheckedButton(crewAgency: MutableLiveData<CrewAgency>?) {
  val selectedId = when (crewAgency?.value) {
    CrewAgency.SPACEX -> R.id.spacex
    CrewAgency.NASA -> R.id.nasa
    CrewAgency.JAXA -> R.id.jaxa
    CrewAgency.ESA -> R.id.esa
    else -> null
  }

  if (selectedId != null && selectedId != checkedRadioButtonId) {
    check(selectedId)
  }
}

@InverseBindingAdapter(attribute = "crewFilter")
fun RadioGroup.getCheckedButton(): CrewAgency? {
  return when (checkedRadioButtonId) {
    R.id.spacex -> CrewAgency.SPACEX
    R.id.nasa -> CrewAgency.NASA
    R.id.jaxa -> CrewAgency.JAXA
    R.id.esa -> CrewAgency.ESA
    else -> null
  }
}

@BindingAdapter("app:crewFilterAttrChanged")
fun RadioGroup.setListeners(listener: InverseBindingListener?) {
  listener?.let {
    setOnCheckedChangeListener { radioGroup, id ->
      listener.onChange()
    }
  }
}
