package org.m2ci.msp.ema

import com.xlson.groovycsv.CsvParser
import org.ejml.simple.SimpleMatrix

class NdiWaveTsvFile extends TextFile {

    final String SEPARATOR = '\t'

    NdiWaveTsvFile(File file) {
        data = read(file)
    }

    static NdiWaveTsvFile loadFrom(File file) {
        new NdiWaveTsvFile(file)
    }

    void writeTo(File file) {
    }

    SimpleMatrix read(File file) {
        def tsvReader = file.newReader()
        def sensor
        def coils = [] as Set
        def header = tsvReader.readLine().split(SEPARATOR).collect {
            def field = it
            switch (field) {
                case ~/Sensor \d Id/:
                    sensor = it.tokenize()[1]
                    coils << sensor
                    break
                case ~/([XYZ]\[mm\]|Q[0xyz])/:
                    field = "Sensor $sensor $it"
                    break
                default:
                    break
            }
            field
        }
        def tsv = CsvParser.parseCsv([separator: SEPARATOR, columnNames: header, readFirstLine: true], tsvReader)
        def data = new SimpleMatrix(tsv.size(), header.size())
        tsv.eachWithIndex { row, r ->
            data.setRow(r, 0, row.collect { Double.parseDouble(it) })
        }
        data
    }

    enum Fields {
        X, Y, Z, Q0, Qx, Qy, Qz
    }
}
