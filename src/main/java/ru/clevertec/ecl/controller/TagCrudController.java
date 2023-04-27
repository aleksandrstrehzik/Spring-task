package ru.clevertec.ecl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.service.TagCrudService;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagCrudController {

    private final TagCrudService tagCrudServiceImpl;

    @GetMapping("/getn/{name}")
    public ResponseEntity<TagDto> getTagByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(tagCrudServiceImpl.getByName(name), HttpStatus.OK);
    }

    @GetMapping("/geti/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tagCrudServiceImpl.getById(id), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity create(@RequestBody @Valid TagDto tagDto) {
        tagCrudServiceImpl.create(tagDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        tagCrudServiceImpl.delete(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
