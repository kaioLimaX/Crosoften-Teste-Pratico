package com.skymob.crosoftenteste.di

import com.google.gson.Gson
import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.api.interceptors.AuthInterceptor
import com.skymob.crosoftenteste.data.remote.repository.BookRepositoryImpl
import com.skymob.crosoftenteste.data.remote.repository.FileRepositoryImpl
import com.skymob.crosoftenteste.data.remote.repository.UserRepositoryImpl
import com.skymob.crosoftenteste.domain.repository.BookRepository
import com.skymob.crosoftenteste.domain.repository.FileRepository
import com.skymob.crosoftenteste.domain.repository.UserRepository
import com.skymob.crosoftenteste.domain.usecases.auth.LoginUseCase
import com.skymob.crosoftenteste.domain.usecases.auth.RegisterUseCase
import com.skymob.crosoftenteste.domain.usecases.book.AddBookUseCase
import com.skymob.crosoftenteste.domain.usecases.book.DeleteBookUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.domain.usecases.book.SearchBookUseCase
import com.skymob.crosoftenteste.domain.usecases.file.UploadFileUseCase
import com.skymob.crosoftenteste.presentation.ui.auth.login.LoginViewModel
import com.skymob.crosoftenteste.presentation.ui.auth.register.RegisterViewModel
import com.skymob.crosoftenteste.presentation.ui.book.details.DetailsViewModel
import com.skymob.crosoftenteste.presentation.ui.book.new.NewBookViewModel
import com.skymob.crosoftenteste.presentation.ui.main.list.ListViewModel
import com.skymob.crosoftenteste.presentation.ui.main.profile.ProfileViewModel
import com.skymob.crosoftenteste.presentation.ui.main.search.SearchViewModel
import com.skymob.crosoftenteste.util.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    //Gson
    single { Gson() }

    single {
        AuthInterceptor(get())
    }

    // Retrofit e ApiService
    single { provideRetrofit(get()) }

    //OkHttpClient
    single { provideOkHttpClient(get()) }

    single { get<Retrofit>().create(ApiService::class.java) }

    //tokenManager
    single { SharedPreferencesManager(androidContext(), get()) }

    // Repositório
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<BookRepository> { BookRepositoryImpl(get()) }
    single<FileRepository> { FileRepositoryImpl(get(),get()) }

    //useCases
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetAllBooksUseCase(get()) }
    factory { GetBookByIdUseCase(get()) }
    factory { SearchBookUseCase(get()) }
    factory { UploadFileUseCase(get()) }
    factory { AddBookUseCase(get()) }
    factory { DeleteBookUseCase(get()) }

    //viewModels
    viewModel { RegisterViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { ListViewModel(get(), get(),get()) }
    viewModel { SearchViewModel(get(),get()) }
    viewModel { NewBookViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }

}