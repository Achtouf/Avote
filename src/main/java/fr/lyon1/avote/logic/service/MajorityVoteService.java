package fr.lyon1.avote.logic.service;

import fr.lyon1.avote.logic.model.MajorVoteInfo;
import fr.lyon1.avote.logic.model.entity.Choice;
import fr.lyon1.avote.logic.model.entity.Vote;
import fr.lyon1.avote.logic.model.major.MajorVoteComparator;
import fr.lyon1.avote.logic.model.major.VoteValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MajorityVoteService {
    public static Choice getMajorityChoice(List<Choice> choices) {
        int voteNumber = choices.get(0).getVotes().size();
        if (voteNumber == 0) {
            return choices.get(0);
        }
        int middle = voteNumber / 2;

        List<MajorVoteInfo> majorVoteInfoList = new ArrayList<>();
        final Comparator<MajorVoteInfo> majorVoteInfoComparator = new MajorVoteComparator();
        final Comparator<Vote> voteComparator = new VoteValueComparator();

        for (Choice choice : choices) {
            List<Vote> orderedVotes = new ArrayList<>(choice.getVotes());
            Collections.sort(orderedVotes, voteComparator);
            MajorVoteInfo majorVoteInfo = new MajorVoteInfo();

            majorVoteInfo.setMajorValue(orderedVotes.get(middle).getValue());
            majorVoteInfo.setMajorChoice(orderedVotes.get(middle).getChoice());

            for (int i = middle; i > 0; i--) {
                if (orderedVotes.get(i).getValue() < majorVoteInfo.getMajorValue()) {
                    majorVoteInfo.setInferiorVotes((i + 1) / voteNumber);
                    break;
                }
            }
            for (int i = middle; i < voteNumber; i++) {
                if (orderedVotes.get(i).getValue() > majorVoteInfo.getMajorValue()) {
                    majorVoteInfo.setSuperiorVotes((voteNumber - i) / voteNumber);
                    break;
                }
            }
            majorVoteInfoList.add(majorVoteInfo);
        }
        Collections.sort(majorVoteInfoList, majorVoteInfoComparator);
        return majorVoteInfoList.get(majorVoteInfoList.size() - 1).getMajorChoice();
    }
}
