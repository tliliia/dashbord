package com.tronina.dashbord.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApiController {
    @Operation(summary = "Method to fetch questions which fit the given search string.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ObjectDto.class))
                    })
    })
    @GetMapping
    Object doGet(@Parameter(description = "search string") @RequestParam("q") String query);

}
