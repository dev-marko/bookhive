package com.wp.bookhive.service.serviceImpl;

import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.models.exceptions.*;
import com.wp.bookhive.repository.BookshopRepository;
import com.wp.bookhive.service.BookshopService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class BookshopServiceImpl implements BookshopService {

    private final BookshopRepository bookshopRepository;

    public BookshopServiceImpl(BookshopRepository bookshopRepository) {
        this.bookshopRepository = bookshopRepository;
    }

    @Override
    public List<BookShop> getAllBookshops() {
        return this.bookshopRepository.findAll();
    }

    @Override
    public BookShop findById(Integer bookshopId) {
        return this.bookshopRepository.findById(bookshopId).orElseThrow(() -> new BookshopNotFoundException(bookshopId));
    }

    @Override
    @Transactional
    public BookShop edit(Integer bookshopId, String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude) {
        BookShop bookShop = this.bookshopRepository.findById(bookshopId).orElseThrow(() -> new BookshopNotFoundException(bookshopId));

        this.checkAddress(address, bookShop);
        this.checkCity(city, bookShop);
        this.checkLatAndLot(latitude, longitude, bookShop);
        this.checkPhoneNumber(phoneNumber,bookShop);

        if(name != null && !name.equals("")) {
            if (this.bookshopRepository.findAll().stream()
                    .noneMatch(bs -> !Objects.equals(bs.getId(), bookshopId) && bs.getName().equals(name))) {
                bookShop.setName(name);
            } else {
                throw new NameAlreadyExistsException(name);
            }
        } else {
            throw new NameNotValidException(name);
        }

        if(bookshopEmail != null && bookshopEmail.contains("@") && bookshopEmail.length() > 1) {
            if (this.bookshopRepository.findAll().stream()
                    .noneMatch(bs -> !Objects.equals(bs.getId(), bookshopId) && bs.getBookshopEmail().equals(bookshopEmail))) {
                bookShop.setBookshopEmail(bookshopEmail);
            } else {
                throw new EmailAlreadyExistsException(bookshopEmail);
            }
        } else {
            throw new EmailNotValidException(bookshopEmail);
        }

        this.bookshopRepository.save(bookShop);
        return bookShop;
    }

    @Override
    @Transactional
    public BookShop save(String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude) {
        BookShop bookShop = new BookShop();

        this.checkAddress(address, bookShop);
        this.checkCity(city, bookShop);
        this.checkLatAndLot(latitude, longitude, bookShop);
        this.checkPhoneNumber(phoneNumber,bookShop);

        if(name != null && !name.equals("")) {
            if (this.bookshopRepository.findAll().stream()
                    .noneMatch(bs -> bs.getName().equals(name))) {
                bookShop.setName(name);
            } else {
                throw new NameAlreadyExistsException(name);
            }
        } else {
            throw new NameNotValidException(name);
        }

        if(bookshopEmail != null && bookshopEmail.contains("@") && bookshopEmail.length() > 1) {
            if (this.bookshopRepository.findAll().stream()
                    .noneMatch(bs -> bs.getBookshopEmail().equals(bookshopEmail))) {
                bookShop.setBookshopEmail(bookshopEmail);
            } else {
                throw new EmailAlreadyExistsException(bookshopEmail);
            }
        } else {
            throw new EmailNotValidException(bookshopEmail);
        }

        this.bookshopRepository.save(bookShop);
        return bookShop;
    }

    @Override
    public void deleteById(int bookshopId) {
        this.bookshopRepository.deleteById(bookshopId);
    }

    private void checkLatAndLot(String latitude, String longitude, BookShop bookShop) {
        if (latitude != null && Double.parseDouble(latitude) > 0 && longitude != null && Double.parseDouble(longitude) > 0) {
            bookShop.setLatitude(Double.parseDouble(latitude));
            bookShop.setLongitude(Double.parseDouble(longitude));
        } else {
            throw new LatOrLotNotValidException(latitude, longitude);
        }
    }

    private void checkCity(String city, BookShop bookShop) {
        if (city != null && !city.equals("")) {
            bookShop.setCity(city);
        } else {
            throw new CityNotValidException(city);
        }
    }

    private void checkAddress(String address, BookShop bookShop) {
        if (address != null && !address.equals("")) {
            bookShop.setAddress(address);
        } else {
            throw new AddressNotValidException(address);
        }
    }

    private void checkPhoneNumber(String phoneNumber, BookShop bookShop) {
        if(phoneNumber!=null && !phoneNumber.equals("") && checkNumbers(phoneNumber) && startsWith(phoneNumber)) {
            bookShop.setPhoneNumber(phoneNumber);
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
