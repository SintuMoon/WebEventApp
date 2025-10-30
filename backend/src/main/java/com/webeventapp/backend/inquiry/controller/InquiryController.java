package com.webeventapp.backend.inquiry.controller;

import com.webeventapp.backend.inquiry.dto.InquiryRequest;
import com.webeventapp.backend.inquiry.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void submitInquiry(@Valid @RequestBody InquiryRequest request) {
        inquiryService.submitInquiry(request);
    }
}
