package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Publisher;
import com.wp.bookhive.models.exceptions.*;
import com.wp.bookhive.repository.PublisherRepository;
import com.wp.bookhive.service.PublisherService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class PublishableServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublishableServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @Override
    public Publisher findById(Integer publisherId) {
        return this.publisherRepository.findById(publisherId).orElseThrow(() -> new PublisherNotFoundException(publisherId));
    }

    @Override
    @Transactional
    public Publisher edit(Integer publisherId, String name, String address, String email, String phoneNumber, String webSiteLink) {
        Publisher publisher = this.publisherRepository.findById(publisherId).orElseThrow(() -> new PublisherNotFoundException(publisherId));

        this.checkAddress(address, publisher);
        this.checkPhoneNumber(phoneNumber,publisher);

        if(name != null && !name.equals("")) {
            if (this.publisherRepository.findAll().stream()
                    .noneMatch(bs -> !Objects.equals(bs.getId(), publisherId) && bs.getName().equals(name))) {
                publisher.setName(name);
            } else {
                throw new NameAlreadyExistsException(name);
            }
        } else {
            throw new NameNotValidException(name);
        }

        if(email != null && email.contains("@") && email.length() > 1) {
            if (this.publisherRepository.findAll().stream()
                    .noneMatch(p -> !Objects.equals(p.getId(), publisherId) && p.getEmail().equals(email))) {
                publisher.setEmail(email);
            } else {
                throw new EmailAlreadyExistsException(email);
            }
        } else {
            throw new EmailNotValidException(email);
        }

        this.publisherRepository.save(publisher);
        return publisher;
    }

    @Override
    @Transactional
    public Publisher save(String name, String address, String email, String phoneNumber, String webSiteLink) {
        Publisher publisher = new Publisher();

        this.checkAddress(address, publisher);
        this.checkPhoneNumber(phoneNumber,publisher);

        if(name != null && !name.equals("")) {
            if (this.publisherRepository.findAll().stream()
                    .noneMatch(bs -> bs.getName().equals(name))) {
                publisher.setName(name);
            } else {
                throw new NameAlreadyExistsException(name);
            }
        } else {
            throw new NameNotValidException(name);
        }

        if(email != null && email.contains("@") && email.length() > 1) {
            if (this.publisherRepository.findAll().stream()
                    .noneMatch(p -> p.getEmail().equals(email))) {
                publisher.setEmail(email);
            } else {
                throw new EmailAlreadyExistsException(email);
            }
        } else {
            throw new EmailNotValidException(email);
        }

        this.publisherRepository.save(publisher);
        return publisher;
    }

    @Override
    public void deleteById(int publisherId) {
        this.publisherRepository.deleteById(publisherId);
    }

    private void checkAddress(String address, Publisher publisher) {
        if (address != null && !address.equals("")) {
            publisher.setAddress(address);
        } else {
            throw new AddressNotValidException(address);
        }
    }

    private void checkPhoneNumber(String phoneNumber, Publisher publisher) {
        if(phoneNumber!=null && !phoneNumber.equals("") && checkNumbers(phoneNumber) && startsWith(phoneNumber)) {
            publisher.setPhoneNumber(phoneNumber);
        } else {
            throw new PhoneNumberNotValidException(phoneNumber);
        }
    }

    private boolean checkNumbers(String phoneNumber) {
        for(String s : phoneNumber.trim().split("")) {
            if(!checkNumber(Integer.parseInt(s)))
                return false;
        }
        return true;
    }

    private boolean checkNumber(int number) {
        return (number >= 0 && number <=9);
    }

    private boolean startsWith(String phoneNumber) {
        return phoneNumber.startsWith("070") || phoneNumber.startsWith("071") || phoneNumber.startsWith("072")
                || phoneNumber.startsWith("074") || phoneNumber.startsWith("075") || phoneNumber.startsWith("076")
                || phoneNumber.startsWith("077") || phoneNumber.startsWith("078") || phoneNumber.startsWith("079");
    }
}
