package ru.clevertec.ecl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageResponse;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.UserCrudService;
import ru.clevertec.ecl.service.UserOrderService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserCrudController {

    private final UserCrudService userCrudService;
    private final UserOrderService userOrderService;

    @PostMapping("/post")
    public ResponseEntity create(@RequestBody @Valid UserDto userDto) {
        userCrudService.create(userDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageResponse<UserDto>> getAllUsers(@RequestParam(value = "name", required = false) String name, Pageable pageable) {
        Page<UserDto> allByPartOfName = userCrudService.getAllOrAllByPartOfName(name, pageable);
        return new ResponseEntity<>(PageResponse.of(allByPartOfName), HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDto>> getUserOrders(@RequestParam("name") String name, Pageable pageable) {
        List<OrderDto> content = userOrderService.getUserOrders(name, pageable).getContent();
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/getUser/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable("name") String name) {
        return new ResponseEntity<>(userCrudService.getUser(name), HttpStatus.OK);
    }
}
