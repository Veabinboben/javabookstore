package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.bookstoreserver.data.entities.Publisher;
import com.example.bookstoreserver.data.repositories.PublishersRepository;

@ExtendWith(MockitoExtension.class)
public class PublishersServiceTest {
    @Mock
    private PublishersRepository publishersRepository;

    @InjectMocks
    private PublishersService publishersService;

    @Test
    void testGetPublisherById() {
        Publisher Publisher = new Publisher();
        when(publishersRepository.findById(1l)).thenReturn(Optional.of(Publisher));

        Publisher result = publishersService.getPublisherById(1L);

        assertEquals(Publisher, result);

        verify(publishersRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPublishers() {

        List<Publisher> expectedPage = List.of();

        when(publishersRepository.findByNameContainingIgnoringCase(anyString()))
                .thenReturn(expectedPage);

        List<Publisher> result = publishersService.getPublishers("all");

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(publishersRepository).findByNameContainingIgnoringCase(eq("all"));
    }

    @Test
    void testSavePublisher() {
        Publisher Publisher = new Publisher();

        publishersService.savePublisher(Publisher);

        verify(publishersRepository, times(1)).save(Publisher);
    }

}
