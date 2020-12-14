package com.example.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.example.academy.data.CourseEntity
import com.example.academy.data.source.AcademyRepository
import com.example.academy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourses(): List<CourseEntity> = academyRepository.getAllCourses()

}