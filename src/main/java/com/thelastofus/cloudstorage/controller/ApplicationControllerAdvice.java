package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", "Sorry, this username already taken");
        return "redirect:/auth/registration";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionBody handleUserNotFound(UserNotFoundException e){
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ExceptionBody handleIllegalState(IllegalStateException e){
        return new ExceptionBody(e.getMessage());
    }
    @ExceptionHandler(UnsupportedRegistrationServiceException.class)
    public ExceptionBody handleUnsupportedRegistrationServiceException(UnsupportedRegistrationServiceException e){
        return new ExceptionBody(e.getMessage());
    }
    @ExceptionHandler(CreateFolderException.class)
    public ExceptionBody handleCreateFolderException(CreateFolderException e) {
        return new ExceptionBody(e.getMessage());
    }
    @ExceptionHandler(FileUploadException.class)
    public ExceptionBody handleFileUploadException(FileUploadException e) {
        return new ExceptionBody(e.getMessage());
    }
    @ExceptionHandler(FolderUploadException.class)
    public ExceptionBody handleFolderUploadException(FolderUploadException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e){
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    public ExceptionBody handleException(){
        return new ExceptionBody("Access denied");
    }
}
