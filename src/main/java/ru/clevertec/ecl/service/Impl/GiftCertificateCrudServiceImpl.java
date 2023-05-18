package ru.clevertec.ecl.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.mappers.GiftCertificateMapper;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.repository.dao.GiftCertificateRepository;
import ru.clevertec.ecl.repository.dao.TagRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.service.GiftCertificateCrudService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GiftCertificateCrudServiceImpl implements GiftCertificateCrudService {

    private final GiftCertificateRepository giftRepository;
    private final GiftCertificateMapper giftMapper;
    private final TagRepository tagDao;

    @Override
    public GiftCertificateDto getByName(String name) {
        Optional<GiftCertificate> giftFromDB = giftRepository.findByName(name);
        if (giftFromDB.isPresent()) {
            return giftMapper.toDto(giftFromDB.get());
        } else throw new GiftCertificateNotFoundException("name = " + name, 40402);
    }

    @Override
    public GiftCertificateDto getById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftRepository.findById(id);
        if (giftCertificate.isPresent()) {
            return giftMapper.toDto(giftCertificate.get());
        } else throw new GiftCertificateNotFoundException("id = " + id, 40401);
    }

    @Override
    @Transactional
    public void delete(String name) {
        Optional<GiftCertificate> giftByName = giftRepository.findByName(name);
        if (giftByName.isPresent()) {
            giftRepository.delete(giftByName.get());
        } else throw new GiftCertificateNotFoundException("name = " + name, 40401);
    }

    @Override
    @Transactional
    public void create(GiftCertificateDto giftDto) {
        GiftCertificate gift = giftMapper.toEntity(giftDto);
        gift.setTags(setTagsToGiftCertificate(gift));
        giftRepository.save(gift);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftDto) {
        Optional<GiftCertificate> giftFromDBOpt = giftRepository.findByName(giftDto.getName());
        if (giftFromDBOpt.isEmpty()) {
            throw new GiftCertificateNotFoundException("name = " + giftDto.getName(), 40402);
        }
        GiftCertificate giftFromDB = giftFromDBOpt.get();
        if (giftDto.getDescription() != null) giftFromDB.setDescription(giftDto.getDescription());
        if (giftDto.getPrice() != null) giftFromDB.setPrice(giftDto.getPrice());
        if (giftDto.getDuration() != null) giftFromDB.setDuration(giftDto.getDuration());
        if (giftDto.getTags() != null) {
            List<Tag> tags = setTagsToGiftCertificate(giftMapper.toEntity(giftDto));
            giftFromDB.setTags(tags);
        }
        return giftMapper.toDto(giftFromDB);
    }

    public Page<GiftCertificateDto> getAllOrAllByPartOfName(String name, @PageableDefault(size = 3) Pageable pageable) {
        return giftRepository.findAllOrAllByPartName(name, pageable).map(giftMapper::toDto);
    }


    private List<Tag> setTagsToGiftCertificate(GiftCertificate gift) {
        if (!gift.getTags().isEmpty()) {
            List<Tag> tags = gift.getTags();
            tags.stream()
                    .filter(name -> !IsTagPresented(name.getName()))
                    .forEach(createTag -> tagDao.save(createTag));
            List<Tag> allTags = tags.stream()
                    .map(tagOpt -> tagDao.findByName(tagOpt.getName()))
                    .map(Optional::get)
                    .collect(toList());
            return allTags;
        } else return null;
    }

    private boolean IsTagPresented(String tag) {
        return tagDao.findByName(tag).isPresent();
    }
}
