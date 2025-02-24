package com.theusick.datagenerator.service.model.generator;

import com.theusick.fleet.service.model.VehicleBrandModel;
import com.theusick.fleet.service.model.VehicleModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class VehicleGenerator extends AbstractModelGenerator<VehicleModel> {

    private static final List<String> COLORS =
        List.of("Red", "Blue", "Green", "White", "Black", "Gray");
    private static final List<String> LICENSE_PLATE_LETTERS =
        List.of("A", "B", "E", "K", "M", "H", "O", "P", "C", "T", "Y", "X");
    private static final List<String> REGIONS =
        List.of("01", "02", "05", "21", "47", "69", "77", "78", "97");

    public static VehicleBrandModel getRandomBrand(List<VehicleBrandModel> brands) {
        return brands.get(ThreadLocalRandom.current().nextInt(brands.size()));
    }

    @Override
    public VehicleModel generateFields() {
        return VehicleModel.builder()
            .year(generateYear())
            .mileage(generateMileage())
            .color(generateColor())
            .price(generatePrice())
            .licensePlate(generateLicensePlate())
            .purchaseDate(generatePurchasedAt())
            .build();
    }

    private int generateYear() {
        return random.nextInt(1989, 2025);
    }

    private int generateMileage() {
        return random.nextInt(0, 500_000);
    }

    private String generateColor() {
        return COLORS.get(random.nextInt(COLORS.size()));
    }

    private double generatePrice() {
        double price = random.nextDouble(300_000, 5_000_000);
        return BigDecimal.valueOf(price)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
    }

    private String generateLicensePlate() {
        String firstLetter = LICENSE_PLATE_LETTERS.get(random.nextInt(LICENSE_PLATE_LETTERS.size()));
        int digits = random.nextInt(100, 999);
        String secondLetter = LICENSE_PLATE_LETTERS.get(random.nextInt(LICENSE_PLATE_LETTERS.size()));
        String thirdLetter = LICENSE_PLATE_LETTERS.get(random.nextInt(LICENSE_PLATE_LETTERS.size()));
        String region = REGIONS.get(random.nextInt(REGIONS.size()));
        return String.format("%s%d%s%s%s", firstLetter, digits, secondLetter, thirdLetter, region);
    }

    private OffsetDateTime generatePurchasedAt() {
        LocalDate startDate = LocalDate.of(2001, Month.JANUARY, 1);

        long startSeconds = startDate.atStartOfDay()
            .toInstant(ZoneOffset.UTC)
            .getEpochSecond();
        long endSeconds = LocalDate.now()
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
            .getEpochSecond();

        Instant generatedInstant = Instant.ofEpochSecond(random.nextLong(startSeconds, endSeconds));

        return OffsetDateTime.ofInstant(generatedInstant, ZoneOffset.UTC);
    }

}
