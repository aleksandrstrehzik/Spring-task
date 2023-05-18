package ru.clevertec.ecl.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.mappers.TagMapper;
import ru.clevertec.ecl.exception.TagNotFoundException;
import ru.clevertec.ecl.repository.dao.TagRepository;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.data.TagTestBuilder;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class)
class TagCrudServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagCrudServiceImpl tagCrudService;

    @ParameterizedTest
    @MethodSource("getTagAndTagDtoWithName")
    void checkGetByName(Tag tag, TagDto tagDto, String name) {
        Mockito.doReturn(Optional.of(tag))
                .when(tagRepository).findByName(name);

        Mockito.doReturn(tagDto)
                .when(tagMapper).toDto(tag);

        assertThat(tagCrudService.getByName(name)).isEqualTo(tagMapper.toDto(tag));
    }

    @ParameterizedTest
    @MethodSource("getTagName")
    void checkGetNameThrow(String name) {
        Mockito.doReturn(Optional.empty())
                .when(tagRepository).findByName(name);

        assertThrows(TagNotFoundException.class,
                () -> tagCrudService.getByName(name));
    }

    @ParameterizedTest
    @MethodSource("getTagWithId")
    void checkGetById(Tag tag, TagDto tagDto, Long id) {
        Mockito.doReturn(Optional.of(tag))
                .when(tagRepository).findById(id);

        Mockito.doReturn(tagDto)
                .when(tagMapper).toDto(tag);

        assertThat(tagCrudService.getById(id)).isEqualTo(tagMapper.toDto(tag));
    }


    @ParameterizedTest
    @MethodSource("getTagId")
    void checkGetByIdThrow(Long id) {
        Mockito.doReturn(Optional.empty())
                .when(tagRepository).findById(id);

        assertThrows(TagNotFoundException.class,
                () -> tagCrudService.getById(id));
    }

    @Test
    void checkCreate() {
        Tag tag = TagTestBuilder.aTag().build();
        TagDto tagDto = TagTestBuilder.aTag().buildDto();

        Mockito.doReturn(tag)
                .when(tagMapper).toEntity(tagDto);

        tagCrudService.create(tagDto);

        Mockito.verify(tagRepository).save(tag);
    }

    @ParameterizedTest
    @MethodSource("getTagWithName")
    void checkDelete(Tag tag, String name) {
        Mockito.doReturn(Optional.of(tag))
                .when(tagRepository).findByName(name);

        tagCrudService.delete(name);

        Mockito.verify(tagRepository).delete(tag);
    }

    static Stream<Arguments> getTagId() {
        return Stream.of(
                arguments(1L),
                arguments(2L),
                arguments(3L));
    }

    static Stream<Arguments> getTagWithId() {
        Tag first = TagTestBuilder.aTag().withId(1L).build();
        Tag second = TagTestBuilder.aTag().withId(2L).build();
        Tag third = TagTestBuilder.aTag().withId(3L).build();
        TagDto firstDto = TagTestBuilder.aTag().buildDto();
        TagDto secondDto = TagTestBuilder.aTag().buildDto();
        TagDto thirdDto = TagTestBuilder.aTag().buildDto();
        return Stream.of(
                arguments(first, firstDto, 1L),
                arguments(second, secondDto, 2L),
                arguments(third, thirdDto, 3L));
    }

    static Stream<Arguments> getTagAndTagDtoWithName() {
        Tag first = TagTestBuilder.aTag().withName("first").build();
        Tag second = TagTestBuilder.aTag().withName("second").build();
        Tag third = TagTestBuilder.aTag().withName("third").build();
        TagDto firstDto = TagTestBuilder.aTag().withName("first").buildDto();
        TagDto secondDto = TagTestBuilder.aTag().withName("second").buildDto();
        TagDto thirdDto = TagTestBuilder.aTag().withName("third").buildDto();
        return Stream.of(
                arguments(first, firstDto, "first"),
                arguments(second, secondDto, "second"),
                arguments(third, thirdDto, "third"));
    }

    static Stream<Arguments> getTagWithName() {
        Tag first = TagTestBuilder.aTag().withName("first").build();
        Tag second = TagTestBuilder.aTag().withName("second").build();
        Tag third = TagTestBuilder.aTag().withName("third").build();
        return Stream.of(
                arguments(first, "first"),
                arguments(second, "second"),
                arguments(third, "third"));
    }

    static Stream<Arguments> getTagName() {
        return Stream.of(
                arguments("first"),
                arguments("second"),
                arguments("third"));
    }
}

