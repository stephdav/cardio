package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Sprint;
import com.sopra.agile.cardio.common.model.SprintData;
import com.sopra.agile.cardio.common.model.SprintDataDetails;
import com.sopra.agile.cardio.common.model.SprintDay;

public class SprintServiceTest {

    private SprintService svc;
    private SprintDao sprintDao;
    private SprintDayDao sprintDayDao;

    @Before
    public void init() throws CardioTechnicalException {
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
            aSprints[idx].setVelocity(100 * idx + 50);
        }
        LocalDate now = LocalDate.now();
        aSprints[1].setStartDate(now.plusDays(-7).toString());
        aSprints[1].setEndDate(now.plusDays(6).toString());

        List<Sprint> sprints = Arrays.asList(aSprints);
        when(sprintDao.all()).thenReturn(sprints);
        when(sprintDao.find("SPR-0")).thenReturn(aSprints[0]);
        when(sprintDao.find("SPR-1")).thenReturn(aSprints[1]);
        when(sprintDao.find("UNK")).thenReturn(null);
        when(sprintDao.allCompleted()).thenReturn(Arrays.asList(aSprints[0], aSprints[1]));

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
    public void testAddSprint() throws CardioTechnicalException, CardioFunctionalException {
        Sprint sprint = svc.add(new Sprint(null, "TST", "TST", "TST"));
        assertNotNull(sprint);
        assertEquals("TST", sprint.getName());
        // TODO : verfiy add is called with a sprint with a not null ID
        verify(sprintDao).add(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint() throws CardioTechnicalException {
        Sprint sprint = svc.update(new Sprint(null, "TST", "TST", "TST"));
        assertNotNull(sprint);
        assertEquals("TST2", sprint.getName());
        verify(sprintDao).update(any(Sprint.class));
    }

    @Test
    public void testFindData() {
        SprintData data = svc.findData("SPR-1");
        assertNotNull(data);

        SprintDataDetails details = data.getDetails();
        assertNotNull(details);

        assertNotNull(details.getDays());
        assertEquals(10, details.getDays().size());

        assertNotNull(details.getIdeal());
        assertEquals(10, details.getIdeal().size());
        assertEquals(100, (int) details.getIdeal().get(0));
        assertEquals(0, (int) details.getIdeal().get(9));

        assertNotNull(details.getDone());
        assertEquals(10, details.getDone().size());
        assertNotNull(details.getLeft());
        assertEquals(10, details.getLeft().size());
    }

    @Test
    public void testUpdateData_data_nullVelocity() throws CardioTechnicalException {
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
    public void testUpdateData_velocityNotNull() throws CardioTechnicalException {
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
