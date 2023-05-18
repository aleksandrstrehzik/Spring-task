package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserOrderedDto;
import ru.clevertec.ecl.service.UserOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userOrder")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @PostMapping("/post")
    public ResponseEntity create(@RequestBody UserOrderedDto userOrderedDto) {
        userOrderService.createOrder(userOrderedDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/mostUsedTag")
    public ResponseEntity<TagDto> getMostFrequencyUsedByMostActiveUser() {
        TagDto tag = userOrderService.findMostFrequencyUsedByMostActiveUser();
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
}
