package com.henry.springjpatx.repo

import com.henry.springjpatx.dto.FruitInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FruitInfoRepository : JpaRepository<FruitInfo, Long> {
    fun findAllByName(name: String): List<FruitInfo>
}