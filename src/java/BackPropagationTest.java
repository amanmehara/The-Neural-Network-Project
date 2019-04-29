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

import nn.activations.Identity;
import nn.activations.Tanh;

public class BackPropagationTest {

    private double learningRate;

    private double momentum;

    private int numberOfLayers;

    public Layer layer[];

    private int numberOfSamples;

    private int sampleNumber;

    private double input[][];

    public double actualOutput[][];

    private double desiredOutput[][];

    private double weights[];

    //Constructor
    public BackPropagationTest(int numberOfNodes[],
                               double inputSamples[][],
                               double outputSamples[][],
                               double weights[],
                               double learningRate,
                               double momentum) {

        // Initiate variables
        this.numberOfSamples = inputSamples.length;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.numberOfLayers = numberOfNodes.length;

        // Create network layers
        this.layer = new Layer[numberOfLayers];

        // Input layer initialised
        layer[0] = new Layer(new Identity(), numberOfNodes[0], numberOfNodes[0]);

        // Layers other than input layer initialised
        for (int i = 1; i < numberOfLayers; i++) {
            layer[i] = new Layer(new Tanh(), numberOfNodes[i], numberOfNodes[i - 1]);
        }

        input = new double[numberOfSamples][layer[0].node.length];

        desiredOutput = new double[numberOfSamples][layer[numberOfLayers - 1].node.length];

        actualOutput = new double[numberOfSamples][layer[numberOfLayers - 1].node.length];

        // Assign Input Set
        for (int i = 0; i < numberOfSamples; i++) {
            for (int j = 0; j < layer[0].node.length; j++) {
                input[i][j] = inputSamples[i][j];
            }
        }

        // Assign Output Set
        for (int i = 0; i < numberOfSamples; i++) {
            for (int j = 0; j < layer[numberOfLayers - 1].node.length; j++) {
                desiredOutput[i][j] = outputSamples[i][j];
            }
        }

        // Assign Weights
        int weightsCount = 0;
        for (int i = 0; i < layer.length; i++) {
            for (int j = 0; j < layer[i].node.length; j++) {
                for (int k = 0; k < layer[i].node[j].weight.length; k++) {
                    layer[i].node[j].weight[k] = weights[weightsCount];
                    weightsCount++;
                }
            }
        }
    }

    //Getter
    public Layer[] getLayers() {
        return layer;
    }

    // Calculate the node activations
    public void feedForward() {

        int i, j;

        for (i = 0; i < layer[0].node.length; i++) {
            layer[0].node[i].output = layer[0].input[i];
        }

        layer[1].input = layer[0].input;
        for (i = 1; i < numberOfLayers; i++) {
            layer[i].computeOutput();
            if (i != numberOfLayers - 1)
                layer[i + 1].input = layer[i].outputVector();
        }

    }

    // Test the Neural Network
    public void testNetwork() {

        for (sampleNumber = 0; sampleNumber < numberOfSamples; sampleNumber++) {

            for (int i = 0; i < layer[0].node.length; i++) {
                layer[0].input[i] = input[sampleNumber][i];
            }
            this.feedForward();

            // Assign actualOutput
            for (int i = 0; i < layer[numberOfLayers - 1].node.length; i++) {
                actualOutput[sampleNumber][i] = layer[numberOfLayers - 1].node[i].output;
            }
        }
    }
}
