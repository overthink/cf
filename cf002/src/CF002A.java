import java.util.*;

// http://codeforces.com/problemset/problem/2/A
public class CF002A {

    public static class Game {
        private final int MAX_ROUNDS = 1000;

        private int roundsComplete = 0;

        // cumulative score by player by round; null means player didn't act that round
        private final Map<String, Integer[]> history = new HashMap<>();
        // current score by player
        private final Map<String, Integer> scores = new HashMap<>();

        public void add(String name, int delta) {
            // update current score
            scores.putIfAbsent(name, 0);
            int newScore = scores.compute(name, (k, v) -> v + delta);

            // track history for breaking ties
            Integer[] scores = history.get(name);
            if (scores == null) {
                scores = new Integer[MAX_ROUNDS];
                history.put(name, scores);
            }

            scores[roundsComplete] = newScore;
            roundsComplete += 1;
        }

        public Set<String> highestScorers() {
            ArrayList<Map.Entry<String, Integer>> candidates = new ArrayList<>(scores.entrySet());
            // sort highest score first
            Collections.sort(candidates, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            // build a set of everyone with the highest score
            Set<String> result = new HashSet<>();
            if (!candidates.isEmpty()) {
                Integer highScore = candidates.get(0).getValue();
                for (Map.Entry<String, Integer> e: candidates) {
                   if (e.getValue().equals(highScore)) {
                       result.add(e.getKey());
                   } else {
                       break;
                   }
                }
            }
            return result;
        }

        public String winner() {
            // find all players P sharing top score
            // if only one, that's the winner
            // else winner is the first player in P who achieved >= top score
            List<String> candidates = new ArrayList<>(highestScorers());
            if (candidates.size() == 1) {
                return candidates.get(0);
            } else if (candidates.size() > 1) {
                Integer highScore = scores.get(candidates.get(0));
                for (int i = 0; i < MAX_ROUNDS; i++) {
                    for (String candidate: candidates) {
                        Integer scoreAtRoundI = history.get(candidate)[i];
                        if (scoreAtRoundI != null && scoreAtRoundI >= highScore) {
                            return candidate;
                        }
                    }
                }
            }
            throw new IllegalStateException("No highest scorers");
        }
    }

    public static void main(String[] args) {
        Game g = new Game();
        try(Scanner s = new Scanner(System.in, "UTF-8")) {
            int lines = s.nextInt();
            for (int i = 0; i < lines; i++) {
                g.add(s.next(), s.nextInt());
            }
        }
        System.out.println(g.winner());
    }

}

//Much simpler soln by jie414341055:
//- keep track of current scores
//- keep track of (player, cumulative score) in array
//- find max score at end
//- winner is first element in array with a cumulative score >= max score and a final score == max score
//
//n = int(raw_input())
//a = {}
//b = []
//for i in xrange(n):
//	x,p = raw_input().split()
//	p = int(p)
//	if x in a.keys():
//		a[x] += p
//	else:
//		a[x] = p
//	b.append((x,a[x]))
//mx = max(a.values())
//
//ra = {}
//for x,p in b:
//	if p >= mx and a[x] == mx:
//		print x
//		break
