package de.dhbw.meetme.domain

import de.dhbw.meetme.rest.GPSService
import spock.lang.Specification

/**
 *
 */
class GPSTest extends Specification {

    def testGPSDistance() { // tests need to start with test....
        when:   // do something
        String test = GPSService.checkDistance(48.4660, 009.1043, 48.4639, 009.1043); //

        then: // then validate
        test == "test";
    }
}
