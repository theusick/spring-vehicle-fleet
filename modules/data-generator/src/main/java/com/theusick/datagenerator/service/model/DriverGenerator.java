package com.theusick.datagenerator.service.model;

import com.theusick.fleet.service.model.DriverModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DriverGenerator extends AbstractModelGenerator<DriverModel> {

    private static final List<String> DRIVER_SURNAMES = List.of(
        "Смирнов", "Иванов", "Кузнецов", "Соколов", "Попов", "Лебедев", "Козлов", "Новиков",
        "Морозов", "Петров", "Волков", "Соловьёв", "Васильев", "Зайцев", "Павлов", "Семёнов",
        "Голубев", "Виноградов", "Богданов", "Воробьёв", "Фёдоров", "Михайлов", "Беляев", "Тарасов",
        "Белов", "Комаров", "Орлов", "Киселёв", "Макаров", "Андреев", "Ковалёв", "Ильин", "Гусев",
        "Титов", "Кузьмин", "Кудрявцев", "Баранов", "Куликов", "Алексеев", "Степанов", "Яковлев",
        "Сорокин", "Сергеев", "Романов", "Захаров", "Борисов", "Королёв", "Герасимов", "Пономарёв",
        "Григорьев"
    );

    private static final List<String> DRIVER_MALE_NAMES = List.of(
        "Сергей", "Владимир", "Кирилл", "Евгений", "Дмитрий", "Вадим", "Григорий", "Руслан"
    );

    private static final List<String> DRIVER_FEMALE_NAMES = List.of(
        "Марина", "Наталья", "Елена", "Дарья", "Екатерина", "Вячеслава", "Александра"
    );

    private final List<Long> availableVehicleIds;

    @Override
    public DriverModel generateFields() {
        Long chosenActiveVehicleId = generateActiveVehicleId();
        return DriverModel.builder()
            .name(generateName())
            .age(generateAge())
            .salary(generateSalary())
            .activeVehicleId(chosenActiveVehicleId)
            .vehicleIds(generateVehicleIds(chosenActiveVehicleId))
            .build();
    }

    private String generateName() {
        if (random.nextInt(10) != 0) {
            return generateMaleName();
        }
        return generateFemaleName();
    }

    private String generateFemaleName() {
        return DRIVER_SURNAMES.get(random.nextInt(DRIVER_SURNAMES.size())) +
            "a " +
            DRIVER_FEMALE_NAMES.get(random.nextInt(DRIVER_FEMALE_NAMES.size()));
    }

    private String generateMaleName() {
        return DRIVER_SURNAMES.get(random.nextInt(DRIVER_SURNAMES.size())) +
            ' ' +
            DRIVER_MALE_NAMES.get(random.nextInt(DRIVER_MALE_NAMES.size()));
    }

    private int generateAge() {
        return random.nextInt(18, 65);
    }

    private double generateSalary() {
        double salary = random.nextDouble(30_000, 150_000);
        return BigDecimal.valueOf(salary)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
    }

    private Long generateActiveVehicleId() {
        if (availableVehicleIds.isEmpty() || (random.nextInt(10) != 0)) {
            return null;
        }
        return availableVehicleIds.remove(random.nextInt(availableVehicleIds.size()));
    }

    private Set<Long> generateVehicleIds(Long activeVehicleId) {
        Set<Long> vehicleDrivers = new HashSet<>();

        if (activeVehicleId != null) {
            vehicleDrivers.add(activeVehicleId);
        }

        if (availableVehicleIds.isEmpty()) {
            return vehicleDrivers;
        }

        int randomVehicleCount = random.nextInt(0, Math.min(2, availableVehicleIds.size()));
        for (int i = 0; i < randomVehicleCount; i++) {
            vehicleDrivers.add(availableVehicleIds.get(i));
        }
        return vehicleDrivers;
    }

}
