package org.luksze;

import org.junit.Test;
import org.luksze.DefaultMethodImplementation.OverriddenDefaultMethodImplementation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.luksze.DefaultMethodImplementation.OverriddenDefaultMethodImplementation.OTHER_VALUE;
import static org.luksze.DefaultMethodInterface.DEFAULT_VALUE;

public class InterfaceDefaultMethodTest {

    @Test
    public void canInheritDefaultMethodFromInterface() throws Exception {
        DefaultMethodInterface implementation = new DefaultMethodImplementation();
        assertThat(implementation.defaultValue(), equalTo(DEFAULT_VALUE));
    }

    @Test
    public void canCreateAnonymousImplementaterWithDefaultMethod() throws Exception {
        DefaultMethodInterface implementation = new DefaultMethodInterface() {};
        assertThat(implementation.defaultValue(), equalTo(DEFAULT_VALUE));
    }

    @Test
    public void canOverrideDefaultMethodFromInterface() throws Exception {
        DefaultMethodInterface implementation = new OverriddenDefaultMethodImplementation();
        assertThat(implementation.defaultValue(), equalTo(OTHER_VALUE));
    }

}