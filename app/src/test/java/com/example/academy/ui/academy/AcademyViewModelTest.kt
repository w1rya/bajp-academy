package com.example.academy.ui.academy

import com.example.academy.data.source.AcademyRepository
import com.example.academy.utils.DataDummy
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest {

    private lateinit var viewModel: AcademyViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository)
    }

    @Test
    fun getCourses() {
        `when`(academyRepository.getAllCourses()).thenReturn(DataDummy.generateDummyCourses())
        val courseEntities = viewModel.getCourses()
        verify(academyRepository).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
    }

}