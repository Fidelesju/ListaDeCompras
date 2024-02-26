package com.example.listacompras

import MainDispatcherRule
import com.example.listacompras.data.dao.ProductDao
import com.example.listacompras.data.entity.Products
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.ProductAction
import com.example.listacompras.presentation.viewModel.ProductDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest : TestWatcher() {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val productDao: ProductDao = mock()

    private val underTest: ProductDetailViewModel by lazy {
        ProductDetailViewModel(
            productDao
        )
    }

    @Test
    fun update_task() = runTest {
        val taskInput = Products(
            1,
            "Title",
            "Description"
        )

        val taskAction = ProductAction(
            products = taskInput,
            actionType = ActionType.UPDATE.name
        )

        underTest.execute(taskAction)
        //Then
        verify(productDao).update(taskInput)
    }

    @Test
    fun delete_task() = runTest {
        val taskInput = Products(
            1,
            "Title",
            "Description"
        )

        val taskAction = ProductAction(
            products = taskInput,
            actionType = ActionType.DELETE.name
        )

        underTest.execute(taskAction)
        //Then
        verify(productDao).delete(taskInput)
    }

    @Test
    fun insert_task() = runTest {
        val taskInput = Products(
            1,
            "Title",
            "Description"
        )

        val taskAction = ProductAction(
            products = taskInput,
            actionType = ActionType.CREATE.name
        )

        underTest.execute(taskAction)
        //Then
        verify(productDao).insert(taskInput)
    }

}


