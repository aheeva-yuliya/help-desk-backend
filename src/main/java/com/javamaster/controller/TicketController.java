package com.javamaster.controller;


import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converter.CustomUserDetailsToUserConverter;
import com.javamaster.converter.TicketRequestDtoToTicketRawConverter;
import com.javamaster.converter.TicketToTicketResponseDtoConverter;
import com.javamaster.dto.TicketOverview;
import com.javamaster.dto.TicketRaw;
import com.javamaster.dto.TicketRequestDto;
import com.javamaster.dto.TicketResponseDto;
import com.javamaster.service.adapters.TicketOverviewServiceAdapter;
import com.javamaster.service.adapters.TicketServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketServiceAdapter ticketService;
    private final TicketToTicketResponseDtoConverter converterToDto;
    private final TicketRequestDtoToTicketRawConverter converterFromDto;
    private final CustomUserDetailsToUserConverter userConverter;
    private final TicketOverviewServiceAdapter ticketOverviewService;


    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    public ResponseEntity<String> saveDraft(@AuthenticationPrincipal final CustomUserDetails user,
                                            @Valid TicketRequestDto ticketRequestDto,
                                            @RequestParam String button) {
        TicketRaw raw = converterFromDto.convert(ticketRequestDto, user);
        ticketService.createTicket(raw, button);
        return ResponseEntity.ok("ticket saved");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatus(@AuthenticationPrincipal final CustomUserDetails user,
                                          @PathVariable Long id,
                                          @RequestParam String action) {
        ticketService.changeStatus(userConverter.convert(user), id, action);
        return ResponseEntity.ok("status changed");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    public ResponseEntity<?> edit(@AuthenticationPrincipal final CustomUserDetails user,
                                  @PathVariable Long id,
                                  @Valid TicketRequestDto ticketRequestDto,
                                  @RequestParam String button) {
        TicketRaw raw = converterFromDto.convert(ticketRequestDto, user);
        raw.setId(id);
        ticketService.editTicket(raw, button);
        return ResponseEntity.ok("edited");
    }

    @GetMapping()
    public List<TicketResponseDto> getAllTickets(@AuthenticationPrincipal final CustomUserDetails user) {
        var role = user.getUserRole();
        return ticketService.getByUserId(user.getId(), role)
                .stream()
                .map(ticket -> converterToDto.convert(ticket, role))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TicketOverview getOverviewByTicketId(@PathVariable Long id) {
        return ticketOverviewService.getTicketOverview(id);
    }
}
