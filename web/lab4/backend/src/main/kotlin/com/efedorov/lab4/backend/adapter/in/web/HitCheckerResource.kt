package com.efedorov.lab4.backend.adapter.`in`.web

import com.efedorov.lab4.backend.adapter.`in`.web.dto.HistoryEntryDTO
import com.efedorov.lab4.backend.application.port.`in`.hit.CheckHitUseCase
import com.efedorov.lab4.backend.application.port.`in`.hit.GetHitsForUserUseCase
import com.efedorov.lab4.backend.application.port.`in`.user.GetUserUseCase
import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.common.LoggerDelegate
import jakarta.ejb.EJB
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.SecurityContext

@Path("/v1/entries")
class HitCheckerResource {
    @EJB
    private lateinit var checkHitUseCase: CheckHitUseCase

    @EJB
    private lateinit var getUserUseCase: GetUserUseCase

    @EJB
    private lateinit var getHitsForUserUseCase: GetHitsForUserUseCase

    private val logger by LoggerDelegate()

    @POST
    fun checkHit(request: CheckRequest, @Context securityContext: SecurityContext) {
        val userName = securityContext.userPrincipal.name
        logger.info("Checking hit for user ${userName}")
        val user = getUserUseCase.getUserByEmail(userName)!!
        val (x, y, r) = request
        checkHitUseCase.checkHitForUser(user, x, y, r)
    }

    @GET
    fun getEntries(@Context securityContext: SecurityContext): List<HistoryEntryDTO> =
        getHitsForUserUseCase
            .getHitsForUserByUserName(securityContext.userPrincipal.name)
            .map { HistoryEntryDTO.fromEntity(it) }

    @DTO
    data class CheckRequest( val x: Double, val y: Double, val r: Double)

}