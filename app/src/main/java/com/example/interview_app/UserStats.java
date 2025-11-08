package com.example.interview_app;

public class UserStats {
    private final int interviewsAttended;
    private final float accuracy;
    private final String performance;

    public UserStats(int interviewsAttended, float accuracy, String performance) {
        this.interviewsAttended = interviewsAttended;
        this.accuracy = accuracy;
        this.performance = performance;
    }

    public int getInterviewsAttended() { return interviewsAttended; }
    public float getAccuracy() { return accuracy; }
    public String getPerformance() { return performance; }
}
