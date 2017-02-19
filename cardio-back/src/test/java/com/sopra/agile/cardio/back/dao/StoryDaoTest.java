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

import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;

public class StoryDaoTest {

    private static JdbcTemplate jdbc;

    private StoryDao dao;

    private static final String STORIES = "STORIES";

    @BeforeClass
    public static void classSetUp() throws Exception {
        jdbc = initDbConnexion("insert-dataTest.sql");
    }

    @Before
    public void setUp() throws Exception {
        dao = new StoryDaoImpl(jdbc);
    }

    @Test
    public void testConstructor() {
        assertNotNull(dao);
    }

    @Test
    public void testAll() throws CardioTechnicalException {
        List<Story> stories = dao.all();
        assertNotNull(stories);
        assertEquals(count(jdbc, STORIES), stories.size());
    }

    @Test
    public void testFindStory() throws CardioTechnicalException {

        // Story must be found
        Story story = dao.find(3);
        assertNotNull(story);
        assertEquals("user story 3", story.getDescription());
        assertEquals(StoryStatus.PENDING, story.getStatus());
        assertEquals(StoryStatus.PENDING, story.getStatus());
        assertEquals(300, story.getContribution());
        assertEquals(30, story.getEstimate());

        // Story not found
        Story unk = dao.find(0);
        assertNull(unk);
    }

    @Test
    public void testAddStory() throws CardioTechnicalException {
        int count = count(jdbc, STORIES);
        Story story = new Story(0, "TST", StoryStatus.DRAFT, 0, 0);
        dao.add(story);
        assertEquals(count + 1, count(jdbc, STORIES));
    }

    @Test
    public void testUpdateStory() throws CardioTechnicalException {
        int count = count(jdbc, STORIES);
        Story story = new Story(9, "to be updated", StoryStatus.DRAFT, 100, 100);
        story.setDescription("updated");
        story.setContribution(77);
        story.setEstimate(99);
        Story updated = dao.update(story);

        assertEquals(count, count(jdbc, STORIES));
        assertNotNull(updated);
        assertEquals("updated", updated.getDescription());
        assertEquals(77, updated.getContribution());
        assertEquals(99, updated.getEstimate());
    }

    @Test
    public void testRemoveStory() throws CardioTechnicalException {
        int count = count(jdbc, STORIES);
        dao.remove(666);
        assertEquals(count - 1, count(jdbc, STORIES));
    }
}
