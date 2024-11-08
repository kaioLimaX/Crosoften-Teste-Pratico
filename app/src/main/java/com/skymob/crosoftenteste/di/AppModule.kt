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
import com.skymob.crosoftenteste.util.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<BookRepository> { BookRepositoryImpl(get()) }
    single<FileRepository> { FileRepositoryImpl(get()) }

/*    //useCases
    factory { RegisterUserUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetBooksUseCase(get()) }
    factory { GetBookByIdUseCase(get()) }
    factory { UploadAndAddBookUseCase(get(),get()) }

    //viewModels
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ListBooksViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { NewBookViewModel(get()) }*/
}