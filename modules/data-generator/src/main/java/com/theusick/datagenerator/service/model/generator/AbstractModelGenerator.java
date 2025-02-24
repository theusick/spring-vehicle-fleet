package com.theusick.datagenerator.service.model.generator;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Data
@SuperBuilder
public abstract class AbstractModelGenerator<T> implements ModelGenerator<T> {

    protected final Random random = ThreadLocalRandom.current();

}
