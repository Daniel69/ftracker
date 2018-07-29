/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package us.sofka.reactive.model.generic;

import org.junit.Test;
import bid.dbo.ftracker.common.UniqueIDGenerator;

import static org.junit.Assert.*;

public class UniqueIDGeneratorTest implements UniqueIDGenerator {

    @Test
    public void testUuid() {
        final String uuid = uuid().block();
        assertNotNull(uuid);

    }

    @Test
    public void testUuids() {
    }

    @Test
    public void testNow() {
    }
}