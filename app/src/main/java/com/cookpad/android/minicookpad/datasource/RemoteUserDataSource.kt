package com.cookpad.android.minicookpad.datasource

import com.cookpad.android.minicookpad.datasource.entity.UserEntity

interface RemoteUserDataSource {
    fun fetch(name: String, onSuccess: (UserEntity?) -> Unit, onFailure: (Throwable) -> Unit)

    fun save(
        userEntity: UserEntity,
        onSuccess: (UserEntity) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}
