package br.com.kartstore.service;

import br.com.kartstore.controller.sale.dto.ClientDTO;
import br.com.kartstore.service.database.entity.ClientEntity;
import br.com.kartstore.service.database.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FindClientService {

    @Autowired
    private ClientRepository repository;

    public List<ClientDTO> findClientIds(Iterable<Integer> iClients) {
        List<ClientEntity> entitys = repository.findAllById(iClients);
        return entitys
                .stream()
                .map(ClientEntity::mapClient)
                .collect(Collectors.toList());
    }

}
