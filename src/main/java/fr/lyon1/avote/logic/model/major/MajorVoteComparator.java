package fr.lyon1.avote.logic.model.major;

import fr.lyon1.avote.logic.model.MajorVoteInfo;

import java.util.Comparator;

public class MajorVoteComparator implements Comparator<MajorVoteInfo> {
    @Override
    public int compare(MajorVoteInfo majorVoteInfo, MajorVoteInfo otherMajorVoteInfo) {
        if (majorVoteInfo.getMajorValue() != otherMajorVoteInfo.getMajorValue()) {
            return majorVoteInfo.getMajorValue() - otherMajorVoteInfo.getMajorValue();
        }
        double leftMax = Math.max(majorVoteInfo.getInferiorVotes(), majorVoteInfo.getSuperiorVotes());
        double rightMax = Math.max(otherMajorVoteInfo.getInferiorVotes(), otherMajorVoteInfo.getSuperiorVotes());
        return (int) Math.signum(leftMax - rightMax);
    }
}
