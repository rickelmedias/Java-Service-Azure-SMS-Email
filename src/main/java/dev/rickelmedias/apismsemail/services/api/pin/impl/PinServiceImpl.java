package dev.rickelmedias.apismsemail.services.api.pin.impl;

import dev.rickelmedias.apismsemail.dtos.models.SendPinDTO;
import dev.rickelmedias.apismsemail.dtos.models.ValidatePinDTO;
import dev.rickelmedias.apismsemail.entities.User;
import dev.rickelmedias.apismsemail.entities.UserPin;
import dev.rickelmedias.apismsemail.repositories.UsersPinRepository;
import dev.rickelmedias.apismsemail.services.api.pin.PinService;

import dev.rickelmedias.apismsemail.services.api.user.UserService;
import dev.rickelmedias.apismsemail.services.apiazure.communication.impl.CommunicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PinServiceImpl implements PinService {

    @Autowired
    UsersPinRepository usersPinRepository;
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<?> sendPin(SendPinDTO body) {
        if (isPossibleToGeneratePin(body.document()).getStatusCode() != HttpStatus.OK) {
            return isPossibleToGeneratePin(body.document());
        }

        UserPin userPin = this.generateUserPin(body);

        switch (body.type()) {
            case EMAIL:
                return this.sendPinToEmail(userPin);
            case SMS:
                return this.sendPinToSMS(userPin);
            default:
                return new ResponseEntity("The type " + body.type() + " not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> validatePin(ValidatePinDTO body) {
        User user = userService.getUserByDocument(body.document());

        if (user == null) {
            return new ResponseEntity("User not exists for this document!", HttpStatus.BAD_REQUEST);
        }

        UserPin userPin = usersPinRepository.findUserPinByUserId(user.getId());

        if (userPin == null) {
            return new ResponseEntity("User Pin not found.", HttpStatus.BAD_REQUEST);
        }

        if (userPin.getUpdatedAt() == null) {
            Date now = new Date();
            long diff = now.getTime() - userPin.getCreatedAt().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= 10) {
                return new ResponseEntity("This PIN is invalid, expired time.", HttpStatus.FORBIDDEN);
            }
        } else {
            Date now = new Date();
            long diff = now.getTime() - userPin.getUpdatedAt().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= 10) {
                return new ResponseEntity("This PIN is invalid, expired time.", HttpStatus.FORBIDDEN);
            }
        }

        if (userPin.getType() == body.type() && userPin.getPin() == body.pin()) {
            usersPinRepository.delete(userPin);
            return new ResponseEntity<>("Validate!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid pin, type and pin not match.", HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<?> sendPinToSMS(UserPin userPin) {
        if (userPin.getUser().getPhone() == null) {
            return new ResponseEntity("This user have not phone!", HttpStatus.BAD_REQUEST);
        }

        CommunicationServiceImpl communicationServiceImpl = new CommunicationServiceImpl();
        communicationServiceImpl.SendSMSOneToOne(userPin.getUser().getPhone(), "Your PIN Code: " + userPin.getPin());

        usersPinRepository.save(userPin);
        return new ResponseEntity("SMS Sent!", HttpStatus.OK);
    }

    private ResponseEntity<?> sendPinToEmail(UserPin userPin) {
        if (userPin.getUser().getEmail() == null) {
            return new ResponseEntity("This user have not email!", HttpStatus.BAD_REQUEST);
        }

        CommunicationServiceImpl communicationServiceImpl = new CommunicationServiceImpl();
        String subject = "Valid your Bosch Fluid Monitoring account";
        String bodyPlainText = "Your PIN code is: " + userPin.getPin();
        communicationServiceImpl.SendMail(userPin.getUser().getEmail(), subject, bodyPlainText);

        usersPinRepository.save(userPin);
        return new ResponseEntity("Email Sent!", HttpStatus.OK);
    }

    private ResponseEntity<?> isPossibleToGeneratePin(String document) {
        User user = userService.getUserByDocument(document);

        if (user == null) {
            return new ResponseEntity("User not exists for this document!", HttpStatus.FORBIDDEN);
        }

        UserPin userPin = usersPinRepository.findUserPinByUserId(user.getId());

        if (userPin != null) {
            if (userPin.getGerneratedPins() >= 20) {
                return new ResponseEntity("You already generated pins 20 times!", HttpStatus.BAD_REQUEST);
            }
            if (userPin.getUpdatedAt() != null) {
                Date now = new Date();
                long diff = now.getTime() - userPin.getUpdatedAt().getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (minutes <= 3) {
                    return new ResponseEntity("You need wait for " + (3-minutes) + " minutes to generate again!", HttpStatus.BAD_REQUEST);
                }
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private UserPin generateUserPin(SendPinDTO body) {
        User user = userService.getUserByDocument(body.document());
        UserPin userPin = usersPinRepository.findUserPinByUserId(user.getId());

        if (userPin != null) {
            userPin.setGerneratedPins(userPin.getGerneratedPins()+1);
            userPin.setPin(this.randomPIN());
            return userPin;
        }

        UserPin newUserPin = new UserPin();
        newUserPin.setPin(this.randomPIN());
        newUserPin.setType(body.type());
        newUserPin.setGerneratedPins(1);
        newUserPin.setUser(user);

        return newUserPin;
    }


    private int randomPIN() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        int pin = sr.nextInt(90000000) + 10000000;
        return pin;
    }
}



