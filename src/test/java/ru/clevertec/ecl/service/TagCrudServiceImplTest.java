package ru.clevertec.ecl.service;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.mappers.TagMapper;
import ru.clevertec.ecl.exception.TagNotFoundException;
import ru.clevertec.ecl.repository.dao.TagDao;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.data.TagTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagCrudServiceImplTest {

    @Spy
    private TagDao tagDao;
    @Spy
    private TagMapper tagMapper;
    @InjectMocks
    private TagCrudService tagService;

    @Test
    void checkGetByName() {
        try (Session session = HibernateUtil.getSession()) {
            Tag tag = TagTestBuilder.aTag().build();
            String name = tag.getName();

            Mockito.doReturn(Optional.of(tag))
                    .when(tagDao).getByName(name, session);

            assertThat(tagService.getByName(name)).isEqualTo(tagMapper.toDto(tag));
        }
    }

    @Test
    void checkGetNameThrow() {
        assertThrows(TagNotFoundException.class,
                () -> tagService.getByName("sd"));
    }

    @Test
    void checkGetById() {
        try (Session session = HibernateUtil.getSession()) {
            Tag tag = TagTestBuilder.aTag().build();
            Long id = tag.getId();

            Mockito.doReturn(Optional.of(tag))
                    .when(tagDao).findById(id, session);

            assertThat(tagService.getById(id)).isEqualTo(tagMapper.toDto(tag));
        }
    }

    @Test
    void checkGetByIdThrow() {
        assertThrows(TagNotFoundException.class,
                () -> tagService.getById(100L));
    }

    @Test
    void checkCreate() {
        Tag tag = TagTestBuilder.aTag().build();
        tagService.create(tagMapper.toDto(tag));
        TagDto tagFromDB = tagService.getByName(tag.getName());

        assertThat(tag.getName()).isEqualTo(tagFromDB.getName());
    }

    @Test
    void checkDelete() {
        try (Session session = HibernateUtil.getSession()) {
            Tag tag = TagTestBuilder.aTag().build();
            String name = tag.getName();

            Mockito.doReturn(Optional.of(tag))
                    .when(tagDao).getByName(name, session);

            Mockito.verify(tagDao).delete(tag, session);
        }
    }
}
