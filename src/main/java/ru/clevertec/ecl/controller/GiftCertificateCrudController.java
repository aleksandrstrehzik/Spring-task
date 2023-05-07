package ru.clevertec.ecl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.GiftCertificateCrudService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gift")
public class GiftCertificateCrudController {

    private final GiftCertificateCrudService giftCrudService;

    @GetMapping("/getn/{name}")
    public ResponseEntity<GiftCertificateDto> getByName(@PathVariable("name") String name) {
        GiftCertificateDto gift = giftCrudService.getByName(name);
        return new ResponseEntity<>(gift, HttpStatus.OK);
    }

    @GetMapping("/geti/{id}")
    public ResponseEntity<GiftCertificateDto> getById(@PathVariable("id") Long id) {
        GiftCertificateDto gift = giftCrudService.getById(id);
        return new ResponseEntity<>(gift, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("name") String name) {
        giftCrudService.delete(name);
    }

    @PostMapping("/post")
    public ResponseEntity create(@RequestBody @Valid GiftCertificateDto giftDto) {
        giftCrudService.create(giftDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/patch")
    public ResponseEntity<GiftCertificateDto> update(@RequestBody @Valid GiftCertificateDto giftDto) {
        GiftCertificateDto updateGift = giftCrudService.update(giftDto);
        return new ResponseEntity<>(updateGift, HttpStatus.OK);
    }

}
