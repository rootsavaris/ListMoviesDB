package com.example.moviedb.di

import android.content.Context
import com.example.moviedb.R
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("APP_KEY")) { provideApiKey(get()) }
    single<ResourceProvider> { ResourceProviderImpl(get()) }
}

internal fun provideApiKey(
    context: Context
): String = context.getString(R.string.app_key)


interface ResourceProvider {
    fun getString(resourceId: Int): String
    fun getString(resourceId: Int, vararg args: Any): String
}

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    override fun getString(resourceId: Int): String = context.getString(resourceId)

    override fun getString(
        resourceId: Int,
        vararg args: Any
    ): String {
        return if (args.isNotEmpty()) {
            context.resources.getString(resourceId, *args)
        } else {
            context.resources.getString(resourceId)
        }
    }

}
