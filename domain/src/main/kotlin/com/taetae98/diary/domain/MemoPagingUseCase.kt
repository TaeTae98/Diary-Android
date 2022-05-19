package com.taetae98.diary.domain

import javax.inject.Inject

class MemoPagingUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(ids: Collection<Int>) = memoRepository.findByTagIds(ids)
}