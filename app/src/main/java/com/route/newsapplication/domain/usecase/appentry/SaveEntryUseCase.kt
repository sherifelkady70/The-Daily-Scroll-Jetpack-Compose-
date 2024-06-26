package com.route.newsapplication.domain.usecase.appentry

import com.route.newsapplication.domain.manager.LocalUserManager

class SaveEntryUseCase (
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}