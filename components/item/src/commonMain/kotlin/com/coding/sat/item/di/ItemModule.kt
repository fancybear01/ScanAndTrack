package com.coding.sat.item.di

import com.coding.sat.item.data.repository.ItemRepositoryImpl
import com.coding.sat.item.domain.repository.ItemRepository
import com.coding.sat.item.domain.usecase.GetItemsUseCase
import org.koin.dsl.module

val itemModule
    get() = module {
        single<ItemRepository> { ItemRepositoryImpl(get()) }
        single { GetItemsUseCase(get()) }
    }