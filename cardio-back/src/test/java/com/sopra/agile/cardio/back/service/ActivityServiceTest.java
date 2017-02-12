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

import com.sopra.agile.cardio.back.dao.ActivityDao;
import com.sopra.agile.cardio.common.exception.CardioFunctionalException;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.Activity;
import com.sopra.agile.cardio.common.model.ActivityStatus;
import com.sopra.agile.cardio.common.model.ActivityType;

public class ActivityServiceTest {

    private ActivityService svc;
    private ActivityDao activityDao;

    @Before
    public void init() throws CardioTechnicalException {
        svc = new ActivityServiceImpl();

        activityDao = mock(ActivityDao.class);
        ReflectionTestUtils.setField(svc, "activityDao", activityDao);

        Activity[] aActivitys = new Activity[3];

        aActivitys[0] = new Activity(null, ActivityType.US, "US-1", "TST 1", ActivityStatus.PENDING);
        aActivitys[1] = new Activity(null, ActivityType.TASK, "TASK-1", "TST 1", ActivityStatus.PENDING);
        aActivitys[2] = new Activity(null, ActivityType.TASK, "TASK-2", "TST 2", ActivityStatus.READY);

        List<Activity> sprints = Arrays.asList(aActivitys);
        when(activityDao.all()).thenReturn(sprints);
        when(activityDao.find("US-1")).thenReturn(aActivitys[0]);
        when(activityDao.find("TASK-1")).thenReturn(aActivitys[1]);
        when(activityDao.find("TASK-2")).thenReturn(aActivitys[2]);
        when(activityDao.find("UNK")).thenReturn(null);

        Activity newActivity = new Activity("TST", ActivityType.TASK, "TST", "TST", ActivityStatus.PENDING);
        when(activityDao.add(any(Activity.class))).thenReturn(newActivity);
    }

    @Test
    public void testAll() {
        List<Activity> activities = svc.all();
        assertNotNull(activities);
        assertEquals(3, activities.size());
    }

    @Test
    public void testFindActivity() {
        // Activity must be found
        Activity activity = svc.find("TASK-1");
        assertNotNull(activity);
        assertEquals("TASK-1", activity.getName());
        assertEquals("TST 1", activity.getDescription());

        // Activity not found
        Activity unk = svc.find("UNK");
        assertNull(unk);
    }

    @Test
    public void testAddActivity() throws CardioTechnicalException, CardioFunctionalException {
        Activity activity = svc.add(new Activity(null, ActivityType.US, "TST", "TST", ActivityStatus.DRAFT));
        assertNotNull(activity);
        assertEquals("TST", activity.getName());
        verify(activityDao).add(any(Activity.class));
    }

}
