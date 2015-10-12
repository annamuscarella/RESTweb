package de.dhbw.meetme.domain

import spock.lang.Specification

/**
 *
 */
class UserTest extends Specification {

    def testUserId() { // tests need to start with test....
        when:   // do something
        User user = new User();

        then: // then validate
        user.id != null;
    }
}
