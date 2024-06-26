package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.exception.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExistException(UserAlreadyExistException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/auth/registration";
    }

    @ExceptionHandler(UnsupportedRegistrationServiceException.class)
    public String handleUnsupportedRegistrationServiceException(UnsupportedRegistrationServiceException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderCreateException.class)
    public String handleCreateFolderException(FolderCreateException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FileRemoveException.class)
    public String handleFileRemoveException(FileRemoveException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderRemoveException.class)
    public String handleFolderRemoveException(FolderRemoveException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FileUploadException.class)
    public String handleFileUploadException(FileUploadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderUploadException.class)
    public String handleFolderUploadException(FolderUploadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FileDownloadException.class)
    public String handleFileDownloadException(FileDownloadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderDownloadException.class)
    public String handleFolderDownloadException(FolderDownloadException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FolderRenameException.class)
    public String handleFolderRenameException(FolderRenameException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(DateTimeValueNotFoundException.class)
    public String handleDateTimeValueNotFoundException(DateTimeValueNotFoundException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(FileRenameException.class)
    public String handleFileRenameException(FileRenameException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(NoSuchFilesException.class)
    public String handleNoSuchFilesException(NoSuchFilesException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorMessage", "Please provide a non-empty folder(file) name");
        return "redirect:/";
    }
}
