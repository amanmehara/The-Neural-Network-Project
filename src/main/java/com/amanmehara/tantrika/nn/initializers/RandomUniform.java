/*
 * Copyright 2019 Aman Mehara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amanmehara.tantrika.nn.initializers;

import com.amanmehara.tantrika.math.linalg.Matrix;
import com.amanmehara.tantrika.math.linalg.Vector;

import java.util.Random;
import java.util.function.DoubleSupplier;

public class RandomUniform implements Initializer {

    private final double minimum;
    private final double maximum;

    public RandomUniform(final double minimum, final double maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException();
        }

        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public Matrix initializeMatrix(final int outerSize, final int innerSize) {
        var random = new Random();
        DoubleSupplier randomSupplier = () -> minimum + random.nextDouble() * (maximum - minimum);
        return new Matrix(outerSize, innerSize, randomSupplier);
    }

    @Override
    public Vector initializeVector(final int size) {
        var random = new Random();
        DoubleSupplier randomSupplier = () -> minimum + random.nextDouble() * (maximum - minimum);
        return new Vector(size, randomSupplier);
    }

}
