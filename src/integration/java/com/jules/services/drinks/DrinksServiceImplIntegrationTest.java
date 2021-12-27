package com.jules.services.drinks;

import com.jules.IntegrationTestSetUp;
import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.models.Characteristic;
import com.jules.models.Component;
import com.jules.persistence.DrinksRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles("integration-test")
@Testcontainers
@DirtiesContext
public class DrinksServiceImplIntegrationTest {

    @Autowired
    private DrinksServiceImpl drinksService;
    @Autowired
    private DrinksRepository drinksRepository;
    private List<DrinkFullInfoDTO> drinks;


    @BeforeEach
    public void setUp() {
        drinks = new ArrayList<>();
        drinks.add(
                DrinkFullInfoDTO.builder()
                        .name("Coca-cola")
                        .price(20)
                        .amount(0.5f)
                        .totalAmount(50f)
                        .build()
        );
        drinks.add(DrinkFullInfoDTO.builder()
                .name("Jack Daniel's Old No7")
                .price(50)
                .amount(0.2f)
                .totalAmount(40f)
                .characteristics(
                        Set.of(
                                Characteristic.builder().name("Fortress").value(40).build()
                        )
                )
                .build());
        drinks.add(DrinkFullInfoDTO.builder()
                .name("Whiskey & Cola")
                .price(60)
                .amount(0.3f)
                .components(
                        Set.of(
                                Component.builder().amount(0.1f).name("Jack Daniel's Old No7").build(),
                                Component.builder().amount(0.2f).name("Coca-cola").build()
                        )
                )
                .build());
        drinks.add(DrinkFullInfoDTO.builder()
                .name("Faxe Royal")
                .price(80)
                .amount(0.5f)
                .totalAmount(100)
                .characteristics(
                        Set.of(
                                Characteristic.builder().name("Fortress").value(5.6f).build(),
                                Characteristic.builder().name("Beer variety").value("light").build()
                        )
                )
                .build());
        drinks.forEach(drink -> drinksService.save(drink));
    }

    @AfterEach
    protected void cleanupAllDataInDb() {
        drinks.forEach(drink -> drinksRepository.deleteByName(drink.getName()));
    }

    @Test
    void findAllSubDrinksOfDrinkWithNesting() {
        var actual = drinksService.findAllBasicDrinks("Whiskey & Cola");
        assertThat(actual).map(drink -> drink.getName()).asList().containsExactlyInAnyOrder("Jack Daniel's Old No7", "Coca-cola");

    }

    @Test
    void findAllSubDrinksOfDrinkWithNoLevelOfNesting() {
        var actual = drinksService.findAllBasicDrinks("Faxe Royal");
        assertThat(actual).asList().isEmpty();
    }
}
