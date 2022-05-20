package com.javamaster.controllers;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.converters.CustomUserDetailsToUserConverter;
import com.javamaster.converters.TicketRequestDtoToTicketRawConverter;
import com.javamaster.converters.TicketToTicketResponseDtoConverter;
import com.javamaster.dto.*;
import com.javamaster.services.adapters.TicketOverviewServiceAdapter;
import com.javamaster.services.adapters.TicketServiceAdapter;
import lombok.AllArgsConstructor;
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
    public ResponseMessage saveTicket(@AuthenticationPrincipal final CustomUserDetails user,
                                      @Valid TicketRequestDto ticketRequestDto,
                                      @RequestParam String action) {
        TicketRaw raw = converterFromDto.convert(ticketRequestDto, user);
        Long id = ticketService.createTicket(raw, action);
        return new ResponseMessage("Ticket # " + id + " has been successfully created.");
    }

    @PatchMapping("/{id}")
    public ResponseMessage changeStatus(@AuthenticationPrincipal final CustomUserDetails user,
                                        @PathVariable Long id,
                                        @RequestParam String action) {
        ticketService.changeStatus(userConverter.convert(user), id, action);
        return new ResponseMessage("Ticket # " + id + " status has been changed.");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    public ResponseMessage editTicket(@AuthenticationPrincipal final CustomUserDetails user,
                                      @PathVariable Long id,
                                      @Valid TicketRequestDto ticketRequestDto,
                                      @RequestParam String action) {
        TicketRaw raw = converterFromDto.convert(ticketRequestDto, user);
        raw.setId(id);
        ticketService.editTicket(raw, action);
        return new ResponseMessage("Ticket # " + id + " has been successfully edited.");
    }

    @GetMapping()
    public List<TicketResponseDto> getAllTickets(@AuthenticationPrincipal final CustomUserDetails user) {
        var role = user.getUserRole();
        return ticketService.getByUserId(user.getId(), role)
                .stream()
                .map(ticket -> converterToDto.convert(ticket, userConverter.convert(user)))
                .collect(Collectors.toList());
    }

    @GetMapping("/my")
    public List<TicketResponseDto> getMyTickets(@AuthenticationPrincipal final CustomUserDetails user) {
        return ticketService.getByUserId(user.getId())
                .stream()
                .map(ticket -> converterToDto.convert(ticket, userConverter.convert(user)))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TicketOverview getOverviewByTicketId(@PathVariable Long id) {
        return ticketOverviewService.getTicketOverview(id);
    }
}
