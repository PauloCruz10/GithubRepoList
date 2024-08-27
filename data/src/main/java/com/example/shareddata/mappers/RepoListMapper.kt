package com.example.shareddata.mappers

import com.example.network.model.repositories.ItemsDto
import com.example.shareddata.db.entity.RepositoryEntity
import com.example.shareddata.model.repositories.Repository

fun ItemsDto.mapToEntity(): RepositoryEntity? {
    val repoId = id ?: return null
    return RepositoryEntity(
        repoId,
        name,
        fullName,
        owner?.login,
        private,
        owner?.avatarUrl,
        language,
        description,
        url,
        forks,
        stargazersCount
    )
}

fun List<ItemsDto>.mapToEntity() = this.mapNotNull { it.mapToEntity() }

fun RepositoryEntity.mapToLib(): Repository {
    return Repository(
        id,
        name.orEmpty(),
        fullName.orEmpty(),
        ownerName.orEmpty(),
        private,
        avatarUrl.orEmpty(),
        language.orEmpty(),
        description.orEmpty(),
        url.orEmpty(),
        forks ?: 0,
        stars ?: 0,
    )
}

fun List<RepositoryEntity>.mapToLib() = this.map { it.mapToLib() }
