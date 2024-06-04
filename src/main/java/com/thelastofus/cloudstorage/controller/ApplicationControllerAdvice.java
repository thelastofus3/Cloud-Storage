package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(UserAlreadyExistException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/auth/registration";
    }

    @ExceptionHandler(UnsupportedRegistrationServiceException.class)
    public String handleUnsupportedRegistrationServiceException(UnsupportedRegistrationServiceException e, RedirectAttributes attributes){
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(FolderCreateException.class)
    public String handleCreateFolderException(FolderCreateException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(FileRemoveException.class)
    public String handleFileRemoveException(FileRemoveException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(FolderRemoveException.class)
    public String handleFolderRemoveException(FolderRemoveException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(FileUploadException.class)
    public String handleFileUploadException(FileUploadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(FolderUploadException.class)
    public String handleFolderUploadException(FolderUploadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FileDownloadException.class)
    public String handleFileDownloadException(FileDownloadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderDownloadException.class)
    public String handleFolderDownloadException(FolderDownloadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(NoSuchFilesException.class)
    public String handleNoSuchFilesException(NoSuchFilesException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage",e.getMessage());
        return "redirect:/";
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", "Empty name of folder");
        return "redirect:/";
    }
}
