package com.sopra.agile.cardio.back.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sopra.agile.cardio.back.dao.SprintDao;
import com.sopra.agile.cardio.common.exception.CardioTechnicalException;
import com.sopra.agile.cardio.common.model.ProjectDataDetails;
import com.sopra.agile.cardio.common.model.Sprint;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ConfigService configSvc;

    @Autowired
    private SprintDao sprintDao;

    private int sample = 6;

    public ProjectServiceImpl() {
        // Empty constructor
    }

    @PostConstruct
    public void init() {
        sample = configSvc.getIntProperty("statistic.sprints.sample");
    }

    @Override
    public ProjectDataDetails projectData() {
        ProjectDataDetails details = new ProjectDataDetails();

        try {
            List<Sprint> sprints = sprintDao.allCompleted();
            computeBurnup(sprints, details);
            computeVelocity(sprints, details);
        } catch (CardioTechnicalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return details;
    }

    private void computeBurnup(List<Sprint> sprints, ProjectDataDetails details) {
        List<String> days = details.getSprints();
        List<Integer> burnup = details.getBurnup();

        int total = 0;
        int velocity = 0;
        for (Sprint s : sprints) {
            velocity = s.getVelocity();
            total += velocity;

            days.add(s.getName());
            burnup.add(total);
        }
    }

    private void computeVelocity(List<Sprint> tail, ProjectDataDetails details) {

        // Keep only a sample of last sprints
        List<Sprint> sprints = tail.subList(Math.max(tail.size() - sample, 0), tail.size());

        int nbSprints = sprints.size();
        if (nbSprints > 0) {
            List<String> days = details.getSprintsSample();
            List<Integer> done = details.getDone();
            List<Integer> values = new ArrayList<Integer>();

            int idx = 0;
            int average = 0;
            int overCommit = 0;

            int velocity = 0;

            for (Sprint s : sprints) {
                velocity = s.getVelocity();

                days.add(s.getName());
                done.add(velocity);

                average += velocity;
                values.add(velocity);
                if (velocity < s.getCommitment()) {
                    overCommit++;
                }

                idx++;
            }

            // sort values
            values.sort(Comparator.naturalOrder());

            int nb = values.size() / 2;

            List<Integer> worstList = values.subList(0, Math.min(values.size(), nb));
            int worst = 0;
            for (int x : worstList) {
                worst += x;
            }
            worst /= nb;

            List<Integer> bestList = values.subList(Math.max(values.size() - nb, 0), values.size());
            int best = 0;
            for (int x : bestList) {
                best += x;
            }
            best /= nb;

            details.setWorst(worst);
            details.setAverage(average / idx);
            details.setBest(best);
            details.setOverCommit(100 * overCommit / idx);
        }
    }
}
