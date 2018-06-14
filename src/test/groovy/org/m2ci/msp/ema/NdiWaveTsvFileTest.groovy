package org.m2ci.msp.ema

import com.google.common.io.Resources
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NdiWaveTsvFileTest {

    static File file

    NdiWaveTsvFile ndiWaveFile

    @BeforeAll
    static void oneTimeSetup() {
        def resource = Resources.getResource('ndiwave.tsv').toURI()
        file = new File(resource)
    }

    @BeforeEach
    void setup() {
        ndiWaveFile = NdiWaveTsvFile.loadFrom(file)
    }

    @Test
    void canRead() {
        assert ndiWaveFile
    }
}
