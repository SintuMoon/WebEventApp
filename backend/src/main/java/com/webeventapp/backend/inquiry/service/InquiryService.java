package com.webeventapp.backend.inquiry.service;

import com.webeventapp.backend.config.ContactProperties;
import com.webeventapp.backend.exception.ResourceNotFoundException;
import com.webeventapp.backend.inquiry.Inquiry;
import com.webeventapp.backend.inquiry.InquiryRepository;
import com.webeventapp.backend.inquiry.dto.InquiryRequest;
import com.webeventapp.backend.provider.model.Provider;
import com.webeventapp.backend.provider.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InquiryService {

    private static final Logger log = LoggerFactory.getLogger(InquiryService.class);

    private final InquiryRepository inquiryRepository;
    private final ProviderRepository providerRepository;
    private final JavaMailSender mailSender;
    private final ContactProperties contactProperties;

    public InquiryService(
            InquiryRepository inquiryRepository,
            ProviderRepository providerRepository,
            JavaMailSender mailSender,
            ContactProperties contactProperties
    ) {
        this.inquiryRepository = inquiryRepository;
        this.providerRepository = providerRepository;
        this.mailSender = mailSender;
        this.contactProperties = contactProperties;
    }

    public void submitInquiry(InquiryRequest request) {
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setProvider(provider);
        inquiry.setOrganizerName(request.organizerName());
        inquiry.setEmail(request.email());
        inquiry.setPhone(request.phone());
        inquiry.setMessage(request.message());
        inquiry.setEventDate(request.eventDate());
        inquiryRepository.save(inquiry);

        sendEmailNotification(provider, request);
    }

    private void sendEmailNotification(Provider provider, InquiryRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(provider.getEmail());
        message.setBcc(contactProperties.adminEmail());
        message.setSubject("Neue Anfrage über WebEventApp");
        message.setText(buildEmailBody(provider, request));
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            log.error("Failed to send inquiry email", ex);
        }
    }

    private String buildEmailBody(Provider provider, InquiryRequest request) {
        StringBuilder body = new StringBuilder();
        body.append("Hallo ").append(provider.getName()).append(",\n\n");
        body.append("eine neue Anfrage ist eingegangen.\n\n");
        body.append("Name: ").append(request.organizerName()).append('\n');
        body.append("E-Mail: ").append(request.email()).append('\n');
        if (request.phone() != null) {
            body.append("Telefon: ").append(request.phone()).append('\n');
        }
        if (request.eventDate() != null) {
            body.append("Event-Datum: ").append(request.eventDate()).append('\n');
        }
        if (request.message() != null && !request.message().isBlank()) {
            body.append("Nachricht: \n").append(request.message()).append('\n');
        }
        body.append("\nViele Grüße,\nDas WebEventApp Team");
        return body.toString();
    }
}
