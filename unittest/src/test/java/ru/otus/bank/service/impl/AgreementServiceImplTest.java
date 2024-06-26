package ru.otus.bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.bank.dao.AgreementDao;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.impl.AgreementServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AgreementServiceImplTest {

    private final AgreementDao dao = mock(AgreementDao.class);

    AgreementServiceImpl agreementServiceImpl;

    @BeforeEach
    void init() {
        agreementServiceImpl = new AgreementServiceImpl(dao);
    }

    @Test
    void testFindByName() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        Mockito.when(dao.findByName(name)).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        assertThat(result).isPresent();
        assertThat(agreement.getId()).isEqualTo(agreement.getId());
    }

    @Test
    void testFindByNameWithCaptor() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.when(dao.findByName(captor.capture())).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        assertThat(name).isEqualTo(captor.getValue());
        assertThat(result).isPresent();
        assertThat(agreement.getId()).isEqualTo(agreement.getId());
    }

    @Test
    void addingAgreement() {
        // Test data
        String agreementName = "testName";

        // Invoke
        agreementServiceImpl.addAgreement(agreementName);

        // Captors
        ArgumentCaptor<Agreement> captor = ArgumentCaptor.forClass(Agreement.class);

        // Verifications
        verify(dao).save(captor.capture());

        assertThat(captor.getValue())
            .matches(it -> it.getName().equals(agreementName))
            .matches(it -> it.getId() == null);
    }

}
