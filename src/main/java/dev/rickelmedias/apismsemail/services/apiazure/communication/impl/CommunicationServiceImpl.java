package dev.rickelmedias.apismsemail.services.apiazure.communication.impl;

import com.azure.communication.email.models.*;
import com.azure.communication.email.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.communication.sms.models.*;
import com.azure.communication.sms.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl {

    @Value("az.connection.string")
    private String connectionString;

    @Value("az.from.email")
    private String fromPhoneNumber;

    @Value("az.from.phone")
    private String fromEmail;

    public void SendSMSOneToOne(String toPhoneNumber, String message) {
        SmsClient smsClient = new SmsClientBuilder()
                .connectionString(this.connectionString)
                .buildClient();

        SmsSendResult sendResult = smsClient.send(this.fromPhoneNumber, toPhoneNumber, message);

        System.out.println("Message Id: " + sendResult.getMessageId());
        System.out.println("Recipient Number: " + sendResult.getTo());
        System.out.println("Send Result Successful:" + sendResult.isSuccessful());
    }

    public void SendMail(String toEmail, String subject, String bodyPlainText) {
        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(this.connectionString)
                .buildClient();

        EmailMessage message = new EmailMessage()
                .setSenderAddress(this.fromEmail)
                .setToRecipients(toEmail)
                .setSubject(subject)
                .setBodyPlainText(bodyPlainText);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message, null);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        System.out.println("Operation Id: " + response.getValue().getId());
    }
}
