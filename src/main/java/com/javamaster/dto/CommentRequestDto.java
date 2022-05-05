package com.javamaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequestDto {
    @Pattern(regexp = "^[A-Za-z\\d ~.(),:;<>@!#$%&'*+-/=?^_`{|}]{0,500}$",
            message = "It is allowed to enter upper and lowercase English alpha characters," +
                    " digits and special characters: ~.(),:;<>@!#$%&'*+-/=?^_`{|}." +
                    " The maximum number of characters is 500.")
    private String comment;
}
