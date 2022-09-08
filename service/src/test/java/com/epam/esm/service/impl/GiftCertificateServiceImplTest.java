package com.epam.esm.service.impl;

import com.epam.esm.converters.impl.GiftCertificateConverterImpl;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.EntityCreationException;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.validators.impl.GiftCertificateValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "tag";
    private static final long CERTIFICATE_ID = 1;
    private static final String CERTIFICATE_NAME = "certificate";
    private static final String CERTIFICATE_DESCRIPTION = "description";
    private static final BigDecimal CERTIFICATE_PRICE = BigDecimal.valueOf(100);
    private static final int CERTIFICATE_DURATION = 90;
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();

    private TagDto tagDto;
    private Tag tag;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificate giftCertificate;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;
    @Mock
    private GiftCertificateValidatorImpl giftCertificateValidator;
    @Mock
    private GiftCertificateConverterImpl giftCertificateConverter;

    @BeforeEach
    void setUp() {
        tagDto = new TagDto(TAG_ID, TAG_NAME);
        tag = new Tag(TAG_ID, TAG_NAME);
        giftCertificateDto = GiftCertificateDto.builder()
                .id(CERTIFICATE_ID)
                .name(CERTIFICATE_NAME)
                .description(CERTIFICATE_DESCRIPTION)
                .price(CERTIFICATE_PRICE)
                .duration(CERTIFICATE_DURATION)
                .createDate(CREATION_DATE)
                .lastUpdateDate(LAST_UPDATE_DATE)
                .tags(Set.of(tagDto))
                .build();
        giftCertificate = GiftCertificate.builder()
                .id(CERTIFICATE_ID)
                .name(CERTIFICATE_NAME)
                .description(CERTIFICATE_DESCRIPTION)
                .price(CERTIFICATE_PRICE)
                .duration(CERTIFICATE_DURATION)
                .createDate(CREATION_DATE)
                .lastUpdateDate(LAST_UPDATE_DATE)
                .tagList(Set.of(tag))
                .build();
    }

    @Test
    void create() {
        checkFieldsValidation();
        doReturn(giftCertificate).when(giftCertificateDao).insert(any(GiftCertificate.class));
        doReturn(giftCertificate).when(giftCertificateConverter).dtoToEntity(any(GiftCertificateDto.class));
        doReturn(giftCertificateDto).when(giftCertificateConverter).entityToDto(any(GiftCertificate.class));
        GiftCertificateDto actual = giftCertificateService.create(giftCertificateDto);
        assertEquals(giftCertificateDto, actual);
    }

    @Test
    void createShouldThrowCreationException() {
        assertThrows(EntityCreationException.class, () -> giftCertificateService.create(null));
    }

    @Test
    void createShouldInvalidDataProvidedException() {
        doReturn(false).when(giftCertificateValidator).isValidCertificateName(anyString());
        assertThrows(InvalidDataProvidedException.class, () -> giftCertificateService.create(giftCertificateDto));
    }

    @Test
    void findById() {
        doReturn(Optional.of(giftCertificate)).when(giftCertificateDao).findById(anyLong());
        doReturn(giftCertificateDto).when(giftCertificateConverter).entityToDto(any(GiftCertificate.class));
        GiftCertificateDto actual = giftCertificateService.findById(CERTIFICATE_ID);
        assertEquals(actual, giftCertificateDto);
    }

    @Test
    void findByIdShouldReturnInvalidDataProvidedException() {
        assertThrows(InvalidDataProvidedException.class, () -> giftCertificateService.findById(null));
    }

    @Test
    void findByIdShouldReturnEntityNotFoundException() {
        doReturn(Optional.empty()).when(giftCertificateDao).findById(anyLong());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(CERTIFICATE_ID));
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void addTags() {
    }

    @Test
    void removeALlTags() {
    }

    @Test
    void findCertificatesByTag() {
    }

    @Test
    void update() {
    }

    private void checkFieldsValidation(){
        doReturn(true).when(giftCertificateValidator).isValidCertificateName(anyString());
        doReturn(true).when(giftCertificateValidator).isValidCertificateDescription(anyString());
        doReturn(true).when(giftCertificateValidator).isValidCertificateDuration(anyInt());
        doReturn(true).when(giftCertificateValidator).isValidCertificatePrice(any(BigDecimal.class));
        doReturn(true).when(giftCertificateValidator).isValidTags(anySet());
    }
}