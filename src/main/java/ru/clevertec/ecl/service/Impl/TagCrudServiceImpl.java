package ru.clevertec.ecl.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.mappers.TagMapper;
import ru.clevertec.ecl.exception.TagNotFoundException;
import ru.clevertec.ecl.repository.dao.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.TagCrudService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagCrudServiceImpl implements TagCrudService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto getByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        if (tag.isEmpty()) {
            throw new TagNotFoundException("name = " + name, 40401);
        }
        return tagMapper.toDto(tag.get());
    }

    @Override
    public TagDto getById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            throw new TagNotFoundException("id = " + id, 40401);
        }
        return tagMapper.toDto(tag.get());
    }

    @Override
    @Transactional
    public void create(TagDto tagDto) {
        tagRepository.save(tagMapper.toEntity(tagDto));
    }

    @Override
    @Transactional
    public void delete(String name) {
        Optional<Tag> tagByName = tagRepository.findByName(name);
        tagByName.ifPresent(tag -> tagRepository.delete(tagByName.get()));
    }
}
