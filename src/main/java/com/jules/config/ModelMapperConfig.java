package com.jules.config;

import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.DrinkGetDTO;
import com.jules.models.Drink;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(drinkFullInfoDTOToDrinkConverter);
        modelMapper.addConverter(drinkToDrinkFullInfoDTOConverter);
        modelMapper.addConverter(drinkToDrinkGetDTOConverter);
        return modelMapper;
    }

    private Converter<DrinkFullInfoDTO, Drink> drinkFullInfoDTOToDrinkConverter = new AbstractConverter<>() {
        protected Drink convert(DrinkFullInfoDTO dto) {
            var drink = Drink.builder()
                    .amount(dto.getAmount())
                    .characteristics(dto.getCharacteristics())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .components(dto.getComponents())
                    .totalAmount(dto.getTotalAmount())
                    .nonDrinksComponent(dto.getNonDrinksComponent())
                    .build();
            return drink;
        }
    };

    private Converter<Drink, DrinkFullInfoDTO> drinkToDrinkFullInfoDTOConverter = new AbstractConverter<>() {
        protected DrinkFullInfoDTO convert(Drink drink) {
            var dto = DrinkFullInfoDTO.builder()
                    .amount(drink.getAmount())
                    .characteristics(drink.getCharacteristics())
                    .name(drink.getName())
                    .price(drink.getPrice())
                    .components(drink.getComponents())
                    .totalAmount(drink.getTotalAmount())
                    .nonDrinksComponent(drink.getNonDrinksComponent())
                    .build();
            return dto;
        }
    };

    private Converter<Drink, DrinkGetDTO> drinkToDrinkGetDTOConverter = new AbstractConverter<>() {
        protected DrinkGetDTO convert(Drink drink) {
            var dto = DrinkGetDTO.builder()
                    .amount(drink.getAmount())
                    .characteristics(drink.getCharacteristics())
                    .name(drink.getName())
                    .price(drink.getPrice())
                    .components(drink.getComponents())
                    .nonDrinksComponent(drink.getNonDrinksComponent())
                    .build();
            return dto;
        }
    };
}