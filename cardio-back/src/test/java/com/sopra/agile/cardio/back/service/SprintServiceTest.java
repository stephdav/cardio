package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.back.dao.SprintDayDao;
import com.sopra.agile.cardio.back.model.Parameter;
import com.sopra.agile.cardio.common.model.Chart;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintServiceTest {

    private SprintService svc;
    private SprintDao sprintDao;
    private SprintDayDao sprintDayDao;

    @Before
    public void init() throws SQLException {
        svc = new SprintServiceImpl();

        sprintDao = mock(SprintDao.class);
        ReflectionTestUtils.setField(svc, "sprintDao", sprintDao);

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
        when(sprintDao.all()).thenReturn(sprints);
        when(sprintDao.find("SPR-0")).thenReturn(aSprints[0]);
        when(sprintDao.find("SPR-1")).thenReturn(aSprints[1]);
        when(sprintDao.find("UNK")).thenReturn(null);
        when(sprintDao.current()).thenReturn(aSprints[1]);

        Sprint newSprint = new Sprint("TST", "TST", "TST", "TST");
        when(sprintDao.add(any(Sprint.class))).thenReturn(newSprint);

        Sprint updatedSprint = new Sprint("TST2", "TST2", "TST2", "TST2");
        when(sprintDao.update(any(Sprint.class))).thenReturn(updatedSprint);

        sprintDayDao = mock(SprintDayDao.class);
        ReflectionTestUtils.setField(svc, "sprintDayDao", sprintDayDao);

        when(sprintDayDao.findBetween(anyString(), Mockito.anyString())).thenReturn(new ArrayList<SprintDay>());
        when(sprintDayDao.insertOrUpdate(any(SprintDay.class))).thenReturn(new SprintDay());

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
        Sprint sprint = svc.add(new Sprint(null, "TST", "TST", "TST"));
        assertNotNull(sprint);
        assertEquals("TST", sprint.getName());
        // TODO : verfiy add is called with a sprint with a not null ID
        verify(sprintDao).add(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint() {
        Sprint sprint = svc.update(new Sprint(null, "TST", "TST", "TST"));
        assertNotNull(sprint);
        assertEquals("TST2", sprint.getName());
        verify(sprintDao).update(any(Sprint.class));
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
        assertEquals(3, burndown.getSeries().size());
        assertNotNull(burndown.getSeries().get(0).getData());
        assertEquals(10, burndown.getSeries().get(0).getData().length);
        assertEquals(100d, burndown.getSeries().get(0).getData()[0], 0.01d);
        assertEquals(0d, burndown.getSeries().get(0).getData()[9], 0.01d);
        assertNotNull(burndown.getSeries().get(1).getData());
        assertEquals(10, burndown.getSeries().get(1).getData().length);
        assertEquals(10, burndown.getSeries().get(2).getData().length);
    }

    @Test
    public void testData() {
        Chart data = svc.data("SPR-1");
        assertNotNull(data);
        assertNotNull(data.getDays());
        assertEquals(10, data.getDays().length);
        assertNotNull(data.getSeries());
        assertEquals(3, data.getSeries().size());
        assertNotNull(data.getSeries().get(0).getData());
        assertEquals(10, data.getSeries().get(0).getData().length);
        assertEquals(100d, data.getSeries().get(0).getData()[0], 0.01d);
        assertEquals(0d, data.getSeries().get(0).getData()[9], 0.01d);
        assertNotNull(data.getSeries().get(1).getData());
        assertEquals(10, data.getSeries().get(1).getData().length);
        assertEquals(10, data.getSeries().get(2).getData().length);
    }

    @Test
    public void testUpdateData_data_nullVelocity() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("2016-07-14", "7");
        data.put("2016-08-15", "");
        data.put("2016-12-25", null);
        SprintData sprintData = new SprintData();
        sprintData.setData(data);

        ArgumentCaptor<Sprint> savedCaptor = ArgumentCaptor.forClass(Sprint.class);

        svc.updateData("SPR-1", sprintData);
        verify(sprintDayDao, Mockito.times(1)).insertOrUpdate(any(SprintDay.class));
        verify(sprintDayDao, Mockito.times(2)).remove(anyString());
        verify(sprintDao, Mockito.times(1)).update(savedCaptor.capture());
        assertNotNull(savedCaptor.getValue());
        assertEquals(0, savedCaptor.getValue().getVelocity());
    }

    @Test
    public void testUpdateData_velocityNotNull() {
        SprintData sprintData = new SprintData();
        sprintData.setData(new HashMap<String, String>());

        SprintDay sd = new SprintDay();
        sd.setDone(7);
        when(sprintDayDao.findLastBetween(anyString(), Mockito.anyString())).thenReturn(sd);
        ArgumentCaptor<Sprint> savedCaptor = ArgumentCaptor.forClass(Sprint.class);

        svc.updateData("SPR-1", sprintData);
        verify(sprintDao, Mockito.times(1)).update(savedCaptor.capture());
        assertNotNull(savedCaptor.getValue());
        assertEquals(7, savedCaptor.getValue().getVelocity());
    }
}
