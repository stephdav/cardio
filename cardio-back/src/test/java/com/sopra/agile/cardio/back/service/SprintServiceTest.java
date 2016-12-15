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

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.common.model.Chart;
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
            aSprints[idx].setCommitment(100 * idx);
        }
        LocalDate now = LocalDate.now();
        aSprints[1].setStartDate(now.plusDays(-7).toString());
        aSprints[1].setEndDate(now.plusDays(6).toString());

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

    @Test
    public void testLeftDays() {
        Parameter param = svc.leftDays();
        assertNotNull(param);
        assertEquals("left-days", param.getKey());
        assertEquals("5", param.getValue());
    }

    @Test
    public void testBurndown() {
        Chart burndown = svc.burndown();
        assertNotNull(burndown);
        assertNotNull(burndown.getDays());
        assertEquals(10, burndown.getDays().length);
        assertNotNull(burndown.getSeries());
        assertEquals(1, burndown.getSeries().size());
        assertNotNull(burndown.getSeries().get(0).getData());
        assertEquals(10, burndown.getSeries().get(0).getData().length);
        assertEquals(100d, burndown.getSeries().get(0).getData()[0], 0.01d);
        assertEquals(0d, burndown.getSeries().get(0).getData()[9], 0.01d);
    }
}
