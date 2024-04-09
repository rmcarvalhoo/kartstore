package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.ClientDTO;
import br.com.kartstore.service.database.entity.ClientEntity;
import br.com.kartstore.service.database.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindClientServiceTest {
    @InjectMocks
    private FindClientService findClientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindClientIds() {
        // Mock data
        List<Integer> clientIds = Arrays.asList(1, 2, 3);
        ClientEntity c1 = new ClientEntity();
        c1.setId(1);
        c1.setName("John");
        ClientEntity c2 = new ClientEntity();
        c2.setId(1);
        c2.setName("Alice");
        ClientEntity c3 = new ClientEntity();
        c3.setId(1);
        c3.setName("Bob");

        List<ClientEntity> clientEntities = Arrays.asList(c1,c2,c3);

        Mockito.when(clientRepository.findAllById(clientIds)).thenReturn(clientEntities);

        List<ClientDTO> clientDTOs = findClientService.findClientIds(clientIds);

        Mockito.verify(clientRepository).findAllById(clientIds);

        // Verify the result
        List<String> expectedNames = Arrays.asList("John", "Alice", "Bob");
        List<String> actualNames = clientDTOs.stream().map(ClientDTO::getName).collect(Collectors.toList());
        assertEquals(expectedNames, actualNames);
    }
}
