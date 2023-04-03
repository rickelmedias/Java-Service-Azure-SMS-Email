package dev.rickelmedias.apismsemail.services.apiazure.communication;

public interface CommunicationService {
    void SendSMSOneToOne(String toPhoneNumber, String message);

    void SendMail(String toEmail, String subject, String bodyPlainText);
}
