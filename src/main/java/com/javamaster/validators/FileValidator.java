package com.javamaster.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    @Override
    public void initialize(ValidFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null) {
            return true;
        }

        var size = multipartFile.getSize();
        String contentType = multipartFile.getContentType();

        assert contentType != null;
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "The selected file type is not allowed. " +
                                    "Please select a file of one of the following types: " +
                                    "pdf, png, doc, docx, jpg, jpeg.")
                    .addConstraintViolation();

            return false;
        }

        if (!isSupportedSize(size)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "The size of the attached file should not be greater than 5 Mb." +
                                    " Please select another file.")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    private boolean isSupportedSize(Long size) {
        return size <= 5000000;
    }
}