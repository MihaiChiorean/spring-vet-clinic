package ro.fasttrackit.vetclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.fasttrackit.vetclinic.model.PetDto;
import ro.fasttrackit.vetclinic.model.Species;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository repository;

    @Test
    public void createNewPet_expectedRepositorySaveMethodCalled() {
        PetDto petRequest = new PetDto();
        this.service.createNewPet(petRequest);
        Mockito.when(this.repository.save(ArgumentMatchers.any(PetEntity.class))).thenReturn(new PetEntity());

        Mockito.verify(repository).save(ArgumentMatchers.any(PetEntity.class));
    }

    @Test
    public void createNewPet_expectedActualValuesFromRepository() {
        // Given (test setup)
        PetEntity expectedEntity = new PetEntity();
        expectedEntity.setId(2L);
        expectedEntity.setName("Azorel");
        expectedEntity.setSpecies(Species.DOG);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(expectedEntity);

        // When (action to test)
        PetDto actual = service.createNewPet(new PetDto());

        // Then (assertions)
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Azorel", actual.getName());
        Assertions.assertEquals(Species.DOG, actual.getSpecies());
    }

}