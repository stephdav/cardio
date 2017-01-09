package com.sopra.agile.cardio.back.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.Sprint;

public class ProjectServiceTest {

    private ProjectService svc;
    private SprintDao sprintDao;

    @Before
    public void init() throws CardioTechnicalException {
        svc = new ProjectServiceImpl();

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

        when(sprintDao.allCompleted()).thenReturn(Arrays.asList(aSprints[0], aSprints[1]));
    }

    @Test
    public void testBurnup() {
        ProjectDataDetails data = svc.projectData();
        assertNotNull(data);

        assertNotNull(data.getSprints());
        assertEquals(2, data.getSprints().size());

        assertNotNull(data.getBurnup());
        assertEquals(2, data.getBurnup().size());
        assertEquals(50, data.getBurnup().get(0).intValue());
        assertEquals(200, data.getBurnup().get(1).intValue());

        assertNotNull(data.getSprintsSample());
        assertEquals(2, data.getSprintsSample().size());

        assertNotNull(data.getDone());
        assertEquals(2, data.getDone().size());
        assertEquals(50, data.getDone().get(0).intValue());
        assertEquals(150, data.getDone().get(1).intValue());
    }

}
