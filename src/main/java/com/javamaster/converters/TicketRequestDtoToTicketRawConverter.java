package com.javamaster.converters;

import com.javamaster.config.jwt.CustomUserDetails;
import com.javamaster.dto.TicketRaw;
import com.javamaster.dto.TicketRequestDto;
import com.javamaster.repositories.CategoryRepository;
import com.javamaster.repositories.PriorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketRequestDtoToTicketRawConverter {
    private final CustomUserDetailsToUserConverter userConverter;
    private final CommentRequestDtoToCommentConverter commentConverter;
    private final AttachmentDtoToAttachmentConverter attachmentConverter;
    private final PriorityRepository priorityRepository;
    private final CategoryRepository categoryRepository;

    public TicketRaw convert(TicketRequestDto source, CustomUserDetails user) {
        return TicketRaw.builder()
                .owner(userConverter.convert(user))
                .name(source.getName())
                .description(source.getDescription())
                .desiredResolutionDate(source.getDesiredResolutionDate())
                .urgency(priorityRepository.findByUrgency(source.getUrgency()))
                .category(categoryRepository.findByCategory(source.getCategory()))
                .comment(commentConverter.convert(source.getComment(), user, null))
                .attachment(attachmentConverter.convert(source.getAttachment()))
                .build();
    }
}
