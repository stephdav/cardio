package com.sopra.agile.cardio.back.dao;

import static com.sopra.agile.cardio.back.dao.DatabaseUtils.count;
import static com.sopra.agile.cardio.back.dao.DatabaseUtils.initDbConnexion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.utils.converter.Converter;
import com.sopra.agile.cardio.common.model.Sprint;

public class SprintDaoTest {

    private static JdbcTemplate jdbc;

    private SprintDao dao;

    private static final String SPRINTS = "SPRINTS";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new SprintDaoImpl(jdbc);
        ReflectionTestUtils.setField(dao, "mapper", new Converter());
    }

    @Test
    public void testAll() {
        List<Sprint> sprints = dao.all();
        assertNotNull(sprints);
        assertEquals(count(jdbc, SPRINTS), sprints.size());
    }

    @Test
    public void testFindSprint() {
        // Sprint must be found
        Sprint sprint = dao.find("SPR-1");
        assertNotNull(sprint);
        assertEquals("1", sprint.getName());
        assertEquals("2016-01-01", sprint.getStartDate());
        assertEquals("2016-01-31", sprint.getEndDate());
        assertEquals("sprint1 goal", sprint.getGoal());
        assertEquals(13, sprint.getCommitment());
        assertEquals(11, sprint.getVelocity());

        // Sprint not found
        Sprint unk = dao.find("UNK");
        assertNull(unk);
    }

    @Test
    public void testAddSprint() {
        int count = count(jdbc, SPRINTS);
        dao.add(new Sprint(null, "2016-02", "2016-02-01", "2016-02-29"));
        assertEquals(count + 1, count(jdbc, SPRINTS));
    }

    @Test
    public void testUpdateSprint() {
        int count = count(jdbc, SPRINTS);
        Sprint sprint = new Sprint("SPR-0", "0", "2015-12-01", "2015-12-31");
        sprint.setGoal("mise à jour");
        sprint.setCommitment(34);
        sprint.setVelocity(20);
        Sprint updated = dao.update(sprint);

        assertEquals(count, count(jdbc, SPRINTS));
        assertNotNull(updated);
        assertEquals("mise à jour", updated.getGoal());
        assertEquals(20, updated.getVelocity());
    }

    @Test
    public void testRemoveSprint() {
        int count = count(jdbc, SPRINTS);
        dao.remove("TO_BE_DELETED");
        assertEquals(count - 1, count(jdbc, SPRINTS));
    }

    @Test
    public void testCurrentSprint() {
        // User must be found
        Sprint sprint = dao.current();
        assertNotNull(sprint);
        assertEquals("SPR-2", sprint.getId());
        assertEquals("current", sprint.getName());
        assertEquals("sprint2 goal", sprint.getGoal());
        assertEquals(44, sprint.getCommitment());
        assertEquals(23, sprint.getVelocity());
    }

    @Test
    public void testAllCompleted() {
        List<Sprint> sprints = dao.allCompleted();
        assertNotNull(sprints);
        assertEquals(count(jdbc, SPRINTS) - 1, sprints.size());
        // TODO : assert that sprints are sorted => impact on testAddSprint
    }

}
