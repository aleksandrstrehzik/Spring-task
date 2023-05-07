package ru.clevertec.ecl.service.Impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.mappers.GiftCertificateMapper;
import ru.clevertec.ecl.repository.dao.GiftCertificateDao;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.GiftCertificateFindAllService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateFindAllServiceImpl implements GiftCertificateFindAllService {

    private final GiftCertificateDao giftDao;
    private final GiftCertificateMapper giftMapper;

    /**
     * Returns a list matching the parameters. Parameters can be zero, then their values will be entered by default
     * @param sort Which field will be sorted by(name or date). Default name
     * @param order ASC or DESC. Default ASC
     * @param page Display page number. Default 1
     * @param tag With what tags to return gift certificates. Default all
     * @return List<GiftCertificateDto> satisfactory
     */
    @Override
    public List<GiftCertificateDto> getAll(String sort, String order, String page, String tag) {
        try (Session session = HibernateUtil.getSession()) {
            int pageSize = 3;
            int productToSkip;
            String sortRequestToDB;
            if (sort != null) {
                String sortRequest = Arrays.stream(sort.split(","))
                        .filter(string -> "name".equals(string) || "date".equals(string))
                        .map(string -> "g." + string)
                        .collect(Collectors.joining(", "));
                sortRequestToDB = sortRequest.replace("date", "create_date");
            } else sortRequestToDB = "g.id";
            if (order == null) order = "ASC";
            if (page == null || Integer.parseInt(page) == 1) {
                productToSkip = 0;
            } else {
                productToSkip = (Integer.parseInt(page) - 1) * pageSize;
            }
            if (tag != null) {
                return giftDao.getAllWithTag(productToSkip, pageSize, sortRequestToDB, order, tag, session).stream()
                        .map(giftMapper::toDto)
                        .collect(Collectors.toList());
            }
            else return giftDao.getAll(productToSkip, pageSize, sortRequestToDB, order, session).stream()
                    .map(giftMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
}
