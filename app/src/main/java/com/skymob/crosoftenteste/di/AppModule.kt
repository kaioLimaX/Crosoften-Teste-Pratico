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
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.domain.usecases.file.UploadFileUseCase
import com.skymob.crosoftenteste.presentation.ui.auth.login.LoginViewModel
import com.skymob.crosoftenteste.presentation.ui.auth.register.RegisterViewModel
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
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<BookRepository> { BookRepositoryImpl(get()) }
    single<FileRepository> { FileRepositoryImpl(get()) }

   //useCases
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetAllBooksUseCase(get()) }
    factory { GetBookByIdUseCase(get()) }
    factory { UploadFileUseCase(get()) }

  //viewModels
    viewModel { RegisterViewModel(get()) }

    viewModel { LoginViewModel(get(),get()) }
  /*    viewModel { ProfileViewModel(get()) }
      viewModel { ListBooksViewModel(get()) }
      viewModel { DetailsViewModel(get()) }
      viewModel { NewBookViewModel(get()) }*/
}