package com.social.hilt

import com.social.presentation.dialogfragments.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton


@InstallIn(FragmentComponent::class, ActivityComponent::class)
@Module
class DialogFragmentModule {

    @Provides
    fun provideDialogFragmentRateSong() : DialogFragmentRateSong {
        return DialogFragmentRateSong.newInstance()
    }

    @Provides
    fun provideDialogFragmentMenu() : DialogFragmentMenu {
        return DialogFragmentMenu.newInstance()
    }


    @Provides
    fun provideDialogFragmentReport() : DialogFragmentReport {
        return DialogFragmentReport.newInstance()
    }

    @Provides
    fun provideDialogFragmentSuggestSong() : DialogFragmentSuggestSong{
        return DialogFragmentSuggestSong.newInstance()
    }

    @Provides
    fun provideDialogFragmentChooser() : DialogFragmentChooser {
        return DialogFragmentChooser.newInstance()
    }

    @Provides
    fun provideDialogFragmentComment() : DialogFragmentComment {
        return DialogFragmentComment.newInstance()
    }
}
