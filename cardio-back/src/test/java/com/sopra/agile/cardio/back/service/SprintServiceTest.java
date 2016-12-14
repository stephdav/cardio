package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.common.model.Sprint;

public class SprintServiceTest {

    private SprintService svc;
    private SprintDao dao;

    @Before
    public void init() throws SQLException {
        svc = new SprintServiceImpl();

        dao = mock(SprintDao.class);
        ReflectionTestUtils.setField(svc, "sprintDao", dao);

        Sprint[] aSprints = new Sprint[3];
        int month;
        for (int idx = 0; idx < 3; idx++) {
            month = idx + 1;
            aSprints[idx] = new Sprint("SPR-" + idx, "NAME" + idx, "2016-" + month + "-01", "2016-" + month + "-15");
            aSprints[idx].setGoal("GOAL" + idx);
        }
        List<Sprint> sprints = Arrays.asList(aSprints);
        when(dao.all()).thenReturn(sprints);
        when(dao.find("SPR-0")).thenReturn(aSprints[0]);
        when(dao.find("UNK")).thenReturn(null);
        when(dao.current()).thenReturn(aSprints[1]);

        Sprint newSprint = new Sprint("TST", "TST", "TST", "TST");
        when(dao.add(any(Sprint.class))).thenReturn(newSprint);

    }

    @Test
    public void testAll() {
        List<Sprint> sprints = svc.all();
        assertNotNull(sprints);
        assertEquals(3, sprints.size());
    }

    @Test
    public void testFindSprint() {
        // Sprint must be found
        Sprint sprint = svc.find("SPR-0");
        assertNotNull(sprint);
        assertEquals("NAME0", sprint.getName());
        assertEquals("2016-1-01", sprint.getStartDate());
        assertEquals("2016-1-15", sprint.getEndDate());
        assertEquals("GOAL0", sprint.getGoal());

        // Sprint not found
        Sprint unk = svc.find("UNK");
        assertNull(unk);
    }

    @Test
    public void testAddSprint() {
        Sprint usr = svc.add(new Sprint(null, "TST", "TST", "TST"));
        assertNotNull(usr);
        assertEquals("TST", usr.getName());
    }

    @Test
    public void testCurrentSprint() {
        Sprint sprint = svc.currentSprint();
        assertNotNull(sprint);
        assertEquals("NAME1", sprint.getName());
    }

}