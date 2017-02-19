package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.StoryDao;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Story;
import com.sopra.agile.cardio.common.model.StoryStatus;

public class StoryServiceTest {

    private StoryService svc;
    private StoryDao storyDao;

    @Before
    public void init() throws CardioTechnicalException {
        svc = new StoryServiceImpl();

        storyDao = mock(StoryDao.class);
        ReflectionTestUtils.setField(svc, "storyDao", storyDao);

        Story[] aStorys = new Story[4];

        aStorys[0] = new Story(1, "us 1", StoryStatus.DRAFT, 1, 1);
        aStorys[1] = new Story(2, "us 2", StoryStatus.READY, 2, 2);
        aStorys[2] = new Story(3, "us 3", StoryStatus.PENDING, 3, 3);
        aStorys[3] = new Story(4, "us 4", StoryStatus.DONE, 4, 4);

        List<Story> sprints = Arrays.asList(aStorys);
        when(storyDao.all()).thenReturn(sprints);
        when(storyDao.find(1)).thenReturn(aStorys[0]);
        when(storyDao.find(2)).thenReturn(aStorys[1]);
        when(storyDao.find(3)).thenReturn(aStorys[2]);
        when(storyDao.find(4)).thenReturn(aStorys[3]);
        when(storyDao.find(0)).thenReturn(null);

        Story newStory = new Story(10, "TST", StoryStatus.DRAFT, 1, 2);
        when(storyDao.add(any(Story.class))).thenReturn(newStory);
    }

    @Test
    public void testAll() throws CardioTechnicalException, CardioFunctionalException {
        List<Story> activities = svc.all();
        assertNotNull(activities);
        assertEquals(4, activities.size());
    }

    @Test
    public void testFindStory() throws CardioTechnicalException, CardioFunctionalException {
        // Story must be found
        Story story = svc.find("2");
        assertNotNull(story);
        assertEquals(2, story.getId());
        assertEquals("us 2", story.getDescription());

        // Story not found
        Story unk = svc.find("0");
        assertNull(unk);
    }

    @Test
    public void testAddStory() throws CardioTechnicalException, CardioFunctionalException {
        Story story = svc.add(new Story(10, "TST", StoryStatus.PENDING, 1, 2));
        assertNotNull(story);
        assertEquals("TST", story.getDescription());
        verify(storyDao).add(any(Story.class));
    }

}
