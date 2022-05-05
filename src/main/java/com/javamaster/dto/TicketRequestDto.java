package com.javamaster.dto;

import com.javamaster.validators.ValidFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequestDto {
    private String category;
    @Pattern(regexp = "^[a-z\\d ~.(),:;<>@!#$%&'*+-/=?^_`{|}]{0,100}$",
            message = "It is allowed to enter lowercase English alpha characters, digits and special characters:" +
                    " ~.(),:;<>@!#$%&'*+-/=?^_`{|}. The maximum number of characters is 100.")
    private String name;
    @Pattern(regexp = "^[A-Za-z\\d ~.(),:;<>@!#$%&'*+-/=?^_`{|}]{0,500}$",
            message = "It is allowed to enter upper and lowercase English alpha characters," +
                    " digits and special characters: ~.(),:;<>@!#$%&'*+-/=?^_`{|}." +
                    " The maximum number of characters is 500.")
    private String description;
    private String urgency;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate desiredResolutionDate;
    private CommentRequestDto comment;
    @ValidFile
    private MultipartFile attachment;
}
