package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.GiftCertificateFindAllService;

import java.util.List;

@RestController
@RequestMapping("/findAll")
@RequiredArgsConstructor
public class GiftCertificateFindAllController {

    private final GiftCertificateFindAllService giftService;

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAll(@RequestParam(value = "sort", required = false) String sort,
                                                            @RequestParam(value = "order", required = false) String order,
                                                            @RequestParam(value = "page", required = false) String page,
                                                            @RequestParam(value = "tag", required = false) String tag) {
        List<GiftCertificateDto> gifts = giftService.getAll(sort, order, page, tag);
        return new ResponseEntity<>(gifts, HttpStatus.OK);
    }
}
