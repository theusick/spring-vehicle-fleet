package com.theusick.datagenerator.service.model;

import com.theusick.fleet.service.model.DriverModel;
import com.theusick.fleet.service.model.VehicleDriverModel;
import com.theusick.fleet.service.model.VehicleModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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

    private final Long enterpriseId;

    private final List<VehicleModel> availableVehicles;

    @Override
    public DriverModel generateFields() {
        VehicleModel chosenActiveVehicle = generateActiveVehicle();
        return DriverModel.builder()
            .name(generateName())
            .age(generateAge())
            .salary(generateSalary())
            .enterpriseId(enterpriseId)
            .activeVehicle(chosenActiveVehicle)
            .vehicleDrivers(generateVehicleDrivers(chosenActiveVehicle))
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

    private VehicleModel generateActiveVehicle() {
        if (availableVehicles.isEmpty() || (random.nextInt(10) != 0)) {
            return null;
        }
        return availableVehicles.remove(random.nextInt(availableVehicles.size()));
    }

    private List<VehicleDriverModel> generateVehicleDrivers(VehicleModel activeVehicle) {
        List<VehicleDriverModel> vehicleDrivers = new ArrayList<>();

        if (activeVehicle != null) {
            vehicleDrivers.add(VehicleDriverModel.builder()
                .vehicleId(activeVehicle.getId())
                .build());
        }

        if (availableVehicles.isEmpty()) {
            return vehicleDrivers;
        }

        int randomVehicleCount = random.nextInt(0, Math.min(4, availableVehicles.size()));
        for (int i = 0; i < randomVehicleCount; i++) {
            vehicleDrivers.add(VehicleDriverModel.builder()
                .vehicleId(availableVehicles.get(i).getId())
                .build());
        }
        return vehicleDrivers;
    }

}
